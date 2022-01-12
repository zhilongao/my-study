package com.util.excel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.alibaba.excel.support.ExcelTypeEnum;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class ExcelUtils {

    public static void parseExcel(InputStream is, AnalysisEventListener listener) {
        // 获取所有sheet,挨个开始解析
        ExcelReader reader = EasyExcelFactory.getReader(is, listener);
        List<ReadSheet> sheets = reader.excelExecutor().sheetList();
        for (int i = 0; i < sheets.size(); i++) {
            // Integer sheetNo = sheets.get(i).getSheetNo();
            // String sheetName = sheets.get(i).getSheetName();
            reader.read(new Sheet(i + 1, 0));
        }
        reader.finish();
    }

    public static void export(OutputStream os, Class cls, List<BaseRowModel> datas) {
        // 实例化ExcelWriter
        ExcelWriter writer = new ExcelWriter(os, ExcelTypeEnum.XLS, true);
        // 实例化表单
        Sheet sheet = new Sheet(1, 0, cls);
        sheet.setSheetName("用户信息1");
        writer.write(datas, sheet);

        Sheet sheet1 = new Sheet(2, 0, cls);
        sheet1.setSheetName("用户信息2");
        writer.write(datas, sheet1);

        writer.finish();
    }

}
