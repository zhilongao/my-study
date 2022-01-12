package com.util.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.util.json.JsonUtils;

import java.util.List;

public class SelfImportHandler extends AnalysisEventListener<List<String>> {

    @Override
    public void invoke(List<String> rows, AnalysisContext context) {
        // rowNum从0开始计算
        Integer rowNum = context.getCurrentRowNum();
        String rowsStr = JsonUtils.toJson(rows);
        System.err.println(rowNum + "--" + rowsStr);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        System.err.println("分析完成->" + context.getCurrentSheet().getSheetNo());
    }
}
