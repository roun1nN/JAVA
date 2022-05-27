/*
package utils;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

import java.util.ArrayList;
import java.util.List;

public class ManagementFeeExcelStyle extends AbstractCellStyleStrategy {


    */
/**
     *   WorkBoot
     * *//*

    private Workbook workbook;


    @Override
    protected void initCellStyle(Workbook workbook) {
        // 初始化信息时，保存Workbook对象，转换时需要使用
        this.workbook=workbook;
    }

    */
/**
     * 表头样式设置
     * @param cell
     * @param head
     * @param relativeRowIndex
     *//*

    @Override
    protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        // 设置表头样式
        int cellColNum = cell.getColumnIndex();
//        System.out.println("列号为:" + cellColNum + "\t表头名为:" + cell.getStringCellValue());
        CellStyle headCellStyle = newCommonHeadStyle(workbook);
        if (cellColNum > 3) {
            // 数据列表头前景设为青柠色
            headCellStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
        } else {
            // 表头前景设置白色
            headCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        }
        cell.setCellStyle(headCellStyle);
    }




    @Override
    protected void setContentCellStyle(Cell cell, Head head, Integer integer) {

    }


    */
/**
     *  设置表头  和内容样式
     * @return
     *//*

    */
/*public static HorizontalCellStyleStrategy managementFeeCellStyleStrategy(){
        //1 表头样式策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        //设置表头居中对齐
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        //表头前景设置白色
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(true);
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short)10);
        headWriteCellStyle.setWriteFont(headWriteFont);

        //内容样式  多个样式则隔行换色
        List<WriteCellStyle> listCntWritCellSty =  new ArrayList<>();

        //2 内容样式策略  样式一
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        //内容字体大小
        contentWriteFont.setFontName("宋体");
        contentWriteFont.setFontHeightInPoints((short)10);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        //背景设置白色
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        //设置自动换行
        contentWriteCellStyle.setWrapped(true);
        //设置垂直居中
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 头默认了 FillPatternType所以可以不指定
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        //设置背景黄色
//        contentWriteCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        //设置水平靠左
        //contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        //设置边框样式
        setBorderStyle(contentWriteCellStyle);
        //内容风格可以定义多个。
        listCntWritCellSty.add(contentWriteCellStyle);*//*
*/
/*

        //2 内容样式策略  样式二
        WriteCellStyle contentWriteCellStyle2 = new WriteCellStyle();
        // 这里需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色。
        // 头默认了 FillPatternType所以可以不指定。
        contentWriteCellStyle2.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        //背景设置白色
        contentWriteCellStyle2.setFillForegroundColor(IndexedColors.WHITE1.getIndex());
        // 背景绿色
//        contentWriteCellStyle2.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        //设置垂直居中
        contentWriteCellStyle2.setVerticalAlignment(VerticalAlignment.CENTER);
        //设置边框样式
        setBorderStyle(contentWriteCellStyle2);
        listCntWritCellSty.add(contentWriteCellStyle2);*//*
*/
/*
        // 水平单元格风格综合策略(表头 + 内容)
        // return  new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        return  new HorizontalCellStyleStrategy(headWriteCellStyle, listCntWritCellSty);
    }*//*


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
