/*
package handler;

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import utils.StringUtils;

import java.util.List;

@Slf4j
public class CustomCellWriteHandler extends AbstractCellWriteHandler {

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        try {
            int colNum = cell.getColumnIndex();
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            String cellValue = cell.getStringCellValue();
            if (isHead) {
                */
/*if (colNum <= 3) {
                    // 表头常规信息格式
                    CellStyle headStyle = newCommonHeadStyle(workbook);
                    cell.setCellStyle(headStyle);
                } else {
                    // 表头数据信息格式
                    CellStyle headStyle = newCommonHeadStyle(workbook);
                    headStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
                    cell.setCellStyle(headStyle);
                }*//*

            } else if (colNum > 3) {
                if (!StringUtils.isEmpty(cellValue)) {
                    // 合计行,防止有的店名叫SUM×××,匹配到括号
                    if (cellValue.startsWith("SUM(")) {
                        CellStyle summaryStyle = newNumDataStyle(workbook, true);
                        summaryStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
                        cell.setCellStyle(summaryStyle);
                        cell.setCellFormula(cellValue);
                    } else {
                        // 数据类型改为数值
                        double cellNum = Double.parseDouble(cellValue);
                        cell.setCellValue(cellNum);
                        CellStyle contentCellStyle = newNumDataStyle(workbook, false);
                        // 设置单元格数据格式为"会计专用"
                        DataFormat dataFormat = workbook.createDataFormat();
                        contentCellStyle.setDataFormat(dataFormat.getFormat("_ * #,##0.00_ ;_ * -#,##0.00_ ;_ * \"-\"??_ ;_ @_ "));
                        cell.setCellStyle(contentCellStyle);
                    }
                }
            } else {
                if (StringUtils.isEmpty(cellValue)) {
                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setBorderLeft(BorderStyle.NONE);
                    cellStyle.setBorderBottom(BorderStyle.NONE);
                    cell.setCellStyle(cellStyle);
                } else if (cellValue.equals("合计")) {
                   CellStyle cellStyle = newFormerDataStyle(workbook, true);
                   cell.setCellStyle(cellStyle);
                } else {
                    CellStyle formerDataCellStyle = newFormerDataStyle(workbook, false);
                    cell.setCellStyle(formerDataCellStyle);
                }
            }
        } catch (Exception e) {
            log.error("单元格设置失败", e);
        }
    }

    */
/**
     * 设置边框样式
     * @param contentCellStyle
     *//*

    private static void setBorderStyle(CellStyle contentCellStyle){
        //设置边框样式
        contentCellStyle.setBorderLeft(BorderStyle.THIN);
        // contentWriteCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex()); //颜色
        contentCellStyle.setBorderTop(BorderStyle.THIN);
        contentCellStyle.setBorderRight(BorderStyle.THIN);
        contentCellStyle.setBorderBottom(BorderStyle.THIN);
    }

    */
/**
     * 数值格式
     * @param workbook
     * @return
     *//*

    private static CellStyle newNumDataStyle(Workbook workbook, boolean isSummary) {
        CellStyle contentCellStyle = workbook.createCellStyle();
        //2 内容样式策略  样式一
        Font contentFont = workbook.createFont();
        //内容字体大小
        if (isSummary) {
            contentFont.setFontName("等线");
            //背景设置青柠色
            contentCellStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
        } else {
            //背景设置白色
            contentCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
            contentFont.setFontName("宋体");
        }
        contentFont.setFontHeightInPoints((short)10);
        // 合计行加粗
        contentFont.setBold(isSummary);
        contentCellStyle.setFont(contentFont);
        // 设置数据格式为会计专用
        DataFormat dataFormat = workbook.createDataFormat();
        contentCellStyle.setDataFormat(dataFormat.getFormat("_ * #,##0.00_ ;_ * -#,##0.00_ ;_ * \"-\"??_ ;_ @_ "));
        //设置自动换行
        contentCellStyle.setWrapText(true);
        //设置垂直居中
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 头默认了 FillPatternType所以可以不指定
        contentCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置背景黄色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        //设置水平靠左
        //contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        //设置边框样式
        setBorderStyle(contentCellStyle);
        return contentCellStyle;
    }

    */
/**
     * 常规信息列格式
     * @param workbook
     * @return
     *//*

    private static CellStyle newFormerDataStyle(Workbook workbook, boolean isSummary) {
        CellStyle contentCellStyle = workbook.createCellStyle();
        //2 内容样式策略  样式一
        Font contentFont = workbook.createFont();
        //内容字体大小
        if (isSummary) {
            contentFont.setFontName("等线");
            //背景设置青柠色
            contentCellStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
        } else {
            contentFont.setFontName("宋体");
            //背景设置白色
            contentCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        }
        contentFont.setFontHeightInPoints((short)10);
        // 合计行加粗
        contentFont.setBold(isSummary);
        contentCellStyle.setFont(contentFont);
        // 设置数据格式为会计专用
        DataFormat dataFormat = workbook.createDataFormat();
        contentCellStyle.setDataFormat(dataFormat.getFormat("_ * #,##0.00_ ;_ * -#,##0.00_ ;_ * \"-\"??_ ;_ @_ "));
        //设置自动换行
        contentCellStyle.setWrapText(true);
        //设置居中
        contentCellStyle.setAlignment(HorizontalAlignment.CENTER);
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 头默认了 FillPatternType所以可以不指定
        contentCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置背景黄色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        //设置边框样式
        setBorderStyle(contentCellStyle);
        return contentCellStyle;
    }

    */
/**
     * 表头格式
     * @param workbook
     * @return
     *//*

    private static CellStyle newCommonHeadStyle(Workbook workbook) {
        //1 表头样式策略
        CellStyle headCellStyle = workbook.createCellStyle();
        //设置表头居中对齐
        headCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        //表头前景设置白色
        headCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        headCellStyle.setWrapText(true);
        Font headFont = workbook.createFont();
        headFont.setBold(true);
        headFont.setFontName("宋体");
        headFont.setFontHeightInPoints((short)10);
        headCellStyle.setFont(headFont);
        setBorderStyle(headCellStyle);
        return headCellStyle;
    }
}
*/
