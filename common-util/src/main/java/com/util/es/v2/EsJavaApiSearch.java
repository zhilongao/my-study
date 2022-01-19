package com.util.es.v2;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * es高级查询(版本7.8.0)
 */
public class EsJavaApiSearch {

    /**
     * 查询索引中的全部数据
     * @param index index
     */
    public void searchAll(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        req.source(new SearchSourceBuilder().query(QueryBuilders.matchAllQuery()));
        RestHighLevelClient client = EsClientUtil.getEsClient();
        SearchResponse response = client.search(req, RequestOptions.DEFAULT);
        printInfo(response);
        client.close();
    }

    /**
     * 条件查询
     * @param index index
     */
    public void searchByCondition(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        req.source(new SearchSourceBuilder().query(QueryBuilders.termQuery("age", 12)));
        RestHighLevelClient client = EsClientUtil.getEsClient();
        SearchResponse response = client.search(req, RequestOptions.DEFAULT);
        printInfo(response);
        client.close();
    }

    /**
     * 分页搜索
     * @param index index
     */
    public void searchByPage(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        // (当前页码-1)*每页显示数据条数
        builder.from(2);
        builder.size(2);
        req.source(builder);
        RestHighLevelClient client = EsClientUtil.getEsClient();
        SearchResponse response = client.search(req, RequestOptions.DEFAULT);
        printInfo(response);
        client.close();
    }

    /**
     * 查询排序
     */
    public void searcBySort(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        builder.sort("age", SortOrder.DESC);
        req.source(builder);
        RestHighLevelClient client = EsClientUtil.getEsClient();
        SearchResponse response = client.search(req, RequestOptions.DEFAULT);
        printInfo(response);
        client.close();
    }

    /**
     * 过滤字段
     * @param index index
     * @throws IOException
     */
    public void searcByFileter(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        String[] excludes = {"age"};
        String[] includes = {};
        builder.fetchSource(includes, excludes);
        req.source(builder);
        RestHighLevelClient client = EsClientUtil.getEsClient();
        SearchResponse response = client.search(req, RequestOptions.DEFAULT);
        printInfo(response);
        client.close();

    }

    /**
     * 组合查询
     * @param index index
     * @throws IOException
     */
    public void searcByCombine(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("age", 14));
        //boolQueryBuilder.must(QueryBuilders.matchQuery("sex", "男"));
        //boolQueryBuilder.mustNot(QueryBuilders.matchQuery("sex", "男"));
        builder.query(boolQueryBuilder);
        req.source(builder);
        RestHighLevelClient client = EsClientUtil.getEsClient();
        SearchResponse response = client.search(req, RequestOptions.DEFAULT);
        client.close();
        printInfo(response);
    }

    /**
     * 范围查询
     * @param index index
     * @throws IOException
     */
    public void searcByRange(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("age");
        rangeQuery.gte(15);
        rangeQuery.lt(17);
        builder.query(rangeQuery);
        req.source(builder);
        executeAndPrint(req);
    }

    /**
     * 模糊查询
     * @param index index
     * @throws IOException
     */
    public void searcByLike(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        builder.query(QueryBuilders.fuzzyQuery("name", "5").fuzziness(Fuzziness.TWO));
        req.source(builder);
        executeAndPrint(req);
    }

    /**
     * 高亮查询
     * @param index index
     * @throws IOException
     */
    public void searcByHighlight(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        TermsQueryBuilder termsQueryBuilder = QueryBuilders.termsQuery("name", "zhangsan");
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.preTags("<font color='red'>");
        highlightBuilder.postTags("</font>");
        highlightBuilder.field("name");
        builder.highlighter(highlightBuilder);
        builder.query(termsQueryBuilder);
        req.source(builder);
        RestHighLevelClient client = EsClientUtil.getEsClient();
        SearchResponse response = client.search(req, RequestOptions.DEFAULT);
        printInfo(response);
        client.close();
    }

    /**
     * 聚合查询
     * @param index index
     * @throws IOException
     */
    public void searcAggregate(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.max("maxAge").field("age");
        builder.aggregation(aggregationBuilder);
        req.source(builder);
        executeAndPrint(req);
    }

    /**
     * 分组查询
     * @param index index
     * @throws IOException
     */
    public void searcByGroup(String index) throws IOException {
        SearchRequest req = new SearchRequest();
        req.indices(index);
        SearchSourceBuilder builder = new SearchSourceBuilder();
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("ageGroup").field("age");
        builder.aggregation(aggregationBuilder);
        req.source(builder);
        executeAndPrint(req);
    }

    private void executeAndPrint(SearchRequest req) throws IOException {
        RestHighLevelClient client = EsClientUtil.getEsClient();
        SearchResponse response = client.search(req, RequestOptions.DEFAULT);
        client.close();
        printInfo(response);
    }



    private void printInfo(SearchResponse response) {
        SearchHits hits = response.getHits();
        System.out.println(hits.getTotalHits());
        System.out.println(response.getTook());
        for (SearchHit hit : hits ) {
            System.out.println(hit.getSourceAsString());
        }
    }
}
