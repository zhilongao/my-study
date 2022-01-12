package com.study.search.aop.search.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IAnalysisEventListener extends AnalysisEventListener<Map<Integer, String>> {

    List<Object> datas = new ArrayList<>();

    Map<Integer, String> headMap = null;

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.err.println("开始读取表头");
        System.err.println(JSONObject.toJSON(headMap));
        this.headMap = headMap;
        System.err.println("结束读取表头");
    }

    @Override
    public void invoke(Map<Integer, String> o, AnalysisContext analysisContext) {
        Integer sheetNo = analysisContext.readSheetHolder().getSheetNo();
        String sheetName = analysisContext.readSheetHolder().getSheetName();
        System.err.println(sheetNo + "---" + sheetName);
        System.err.println("解析到了一条数据 " + JSONObject.toJSONString(o));
        datas.add(o);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.err.println("execute any");
    }

    public List<Object> getDatas() {
        return datas;
    }

    public void setDatas(List<Object> datas) {
        this.datas = datas;
    }
}
