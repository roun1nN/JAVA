package utils;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数组属性转换器
 */

@Component
public class ListConverter implements Converter<List> {
    @Override
    public Class supportJavaTypeKey() {
        return List.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public List convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        String stringValue = cellData.getStringValue();
        String[] split = stringValue.split(",");
        List<String> enterpriseList = new ArrayList<>();
        for(int i = 0; i < split.length; i++){
            enterpriseList.add(split[i]);
        }
        return enterpriseList;
    }

    @Override
    public CellData convertToExcelData(List list, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        list.forEach(o -> {
            String s = o.toString();
            stringBuilder.append(s+",");
        });
        return new CellData(stringBuilder.toString());
    }
}
