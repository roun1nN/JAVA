package utils;

import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractCellWriteHandler;
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.row.SimpleRowHeightStyleStrategy;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EasyExcelUtil {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     *
     * 表头直接放入
     * @Param 复杂表头文件导出
     * @return
     */
    public void downloadFileNext(/*HttpServletResponse response,*/ List<?> list, String fileNames, String sheetName, List<List<String>> titleList) throws Exception {
        if (CollectionUtils.isEmpty(list)) {
            throw new Exception("无数据");
        }
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        try {
            // URLEncoder.encode防止中文乱码
//            String fileName = URLEncoder.encode(fileNames, "UTF-8").replaceAll("\\+", "%20");
            if (StringUtils.isEmpty(sheetName)) {
                sheetName = fileNames;
            }
            /*response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName);*/
            // 这里需要设置不关闭流
//            EasyExcel.write(response.getOutputStream(), list.get(0).getClass()).autoCloseStream(Boolean.FALSE).sheet(sheetName)
//                    .doWrite(list);
            EasyExcel./*write(response.getOutputStream())*/write(fileNames)
                    .head(titleList)
                    .registerWriteHandler(new ManagementFeeExcelStyle()/*.managementFeeCellStyleStrategy()*/)
                    .registerWriteHandler(new CustomCellWriteHandler())
                    .registerWriteHandler(new FreezeSheetWriteHandler())
                    .registerWriteHandler(new ColumnWidthStrategyHandler())
                    .registerWriteHandler(new SimpleRowHeightStyleStrategy((short)25,(short)24)) // 简单的行高策略：头行高25，内容行高15
                    .sheet(sheetName).doWrite(list);
        } catch (Exception e) {
            logger.error("导出失败------------"+e.getMessage());
            e.printStackTrace();
            // 重置response
            /*response.reset();
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            Map<String, String> map = new HashMap<String, String>();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSONUtil.toJsonStr()(map));*/
        }
    }

}

class ColumnWidthStrategyHandler extends AbstractColumnWidthStyleStrategy {

    private static final int MAX_COLUMN_WIDTH = 255;
    //因为在自动列宽的过程中，有些设置地方让列宽显得紧凑，所以做出了个判断
    private static final int COLUMN_WIDTH = 20;
    private  Map<Integer, Map<Integer, Integer>> CACHE = new HashMap(8);

    public ColumnWidthStrategyHandler() {
    }

    @Override
    protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        boolean needSetWidth = isHead || !CollectionUtils.isEmpty(cellDataList);
        if (needSetWidth) {
            Map<Integer, Integer> maxColumnWidthMap = (Map)CACHE.get(writeSheetHolder.getSheetNo());
            if (maxColumnWidthMap == null) {
                maxColumnWidthMap = new HashMap(16);
                CACHE.put(writeSheetHolder.getSheetNo(), maxColumnWidthMap);
            }

            Integer columnWidth = this.dataLength(cellDataList, cell, isHead);
            if (columnWidth >= 0) {
                if (columnWidth > MAX_COLUMN_WIDTH) {
                    columnWidth = MAX_COLUMN_WIDTH;
                }else {
                    if(columnWidth<COLUMN_WIDTH){
                        columnWidth =columnWidth*2;
                    }
                }

                Integer maxColumnWidth = (Integer)((Map)maxColumnWidthMap).get(cell.getColumnIndex());
                if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                    ((Map)maxColumnWidthMap).put(cell.getColumnIndex(), columnWidth);
                    writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(),  columnWidth * 128);
                }
            }
        }
    }

    private  Integer dataLength(List<CellData> cellDataList, Cell cell, Boolean isHead) {
        if (isHead) {
            return cell.getStringCellValue().getBytes().length;
        } else {
            CellData cellData = (CellData)cellDataList.get(0);
            CellDataTypeEnum type = cellData.getType();
            if (type == null) {
                return -1;
            } else {
                switch(type) {
                    case STRING:
                        return cellData.getStringValue().getBytes().length;
                    case BOOLEAN:
                        return cellData.getBooleanValue().toString().getBytes().length;
                    case NUMBER:
                        return cellData.getNumberValue().toString().getBytes().length;
                    default:
                        return -1;
                }
            }
        }
    }
}

class CustomCellWriteHandler extends AbstractCellWriteHandler {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void afterCellDispose(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, List<CellData> cellDataList, Cell cell, Head head, Integer relativeRowIndex, Boolean isHead) {
        try {
            int colNum = cell.getColumnIndex();
            Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
            String cellValue = cell.getStringCellValue();
            if (isHead) {
                /*if (colNum <= 3) {
                    // 表头常规信息格式
                    CellStyle headStyle = newCommonHeadStyle(workbook);
                    cell.setCellStyle(headStyle);
                } else {
                    // 表头数据信息格式
                    CellStyle headStyle = newCommonHeadStyle(workbook);
                    headStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
                    cell.setCellStyle(headStyle);
                }*/
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
            logger.error("单元格设置失败", e);
        }
    }

    /**
     * 设置边框样式
     * @param contentCellStyle
     */
    private static void setBorderStyle(CellStyle contentCellStyle){
        //设置边框样式
        contentCellStyle.setBorderLeft(BorderStyle.THIN);
        // contentWriteCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex()); //颜色
        contentCellStyle.setBorderTop(BorderStyle.THIN);
        contentCellStyle.setBorderRight(BorderStyle.THIN);
        contentCellStyle.setBorderBottom(BorderStyle.THIN);
    }

    /**
     * 数值格式
     * @param workbook
     * @return
     */
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

    /**
     * 常规信息列格式
     * @param workbook
     * @return
     */
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

    /**
     * 表头格式
     * @param workbook
     * @return
     */
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

class FreezeSheetWriteHandler implements SheetWriteHandler {

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {

    }

    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        Sheet sheet = writeSheetHolder.getSheet();
        sheet.createFreezePane(4, 2);
    }
}

class ManagementFeeExcelStyle extends AbstractCellStyleStrategy {


    /**
     *   WorkBoot
     * */
    private Workbook workbook;


    @Override
    protected void initCellStyle(Workbook workbook) {
        // 初始化信息时，保存Workbook对象，转换时需要使用
        this.workbook=workbook;
    }

    /**
     * 表头样式设置
     * @param cell
     * @param head
     * @param relativeRowIndex
     */
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


    /**
     * 设置边框样式
     * @param contentCellStyle
     */
    private static void setBorderStyle(CellStyle contentCellStyle){
        //设置边框样式
        contentCellStyle.setBorderLeft(BorderStyle.THIN);
        // contentWriteCellStyle.setBottomBorderColor(IndexedColors.BLUE.getIndex()); //颜色
        contentCellStyle.setBorderTop(BorderStyle.THIN);
        contentCellStyle.setBorderRight(BorderStyle.THIN);
        contentCellStyle.setBorderBottom(BorderStyle.THIN);
    }


    /**
     * 表头格式
     * @param workbook
     * @return
     */
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
