import Bean.TypeBean;
import Bean.RowBean;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.*;

public class ManagementFeeExcelExport {

    Logger logger = LoggerFactory.getLogger(ManagementFeeExcelExport.class);
    @Test
    public void incomeExcelExport() {
        List<RowBean> rowData = new ArrayList<>();
        TypeBean bean1 = new TypeBean("现金", 1, 22.34);
        TypeBean bean2 = new TypeBean("支付宝", 2, 11.23);
        TypeBean bean3 = new TypeBean("微信", 3, 12.56);
        List<TypeBean> receiptList = Arrays.asList(bean1, bean2, bean3);
        List<TypeBean> eatInList = Arrays.asList(bean1, bean2, bean3);
        List<TypeBean> deliveryList = Arrays.asList(bean1, bean2, bean3);
        RowBean firstDataRow = new RowBean("1", "SHA186", "肯德基", "501", 12.34, 12.45, 12.56, 12.67, receiptList, eatInList, deliveryList);
        rowData.add(firstDataRow);
        for (int i = 1; i <= 30; i++) {
            RowBean bean = new RowBean();
            BeanUtils.copyProperties(rowData.get(0), bean);
            bean.setSequence(Integer.toString(i + 1));
            bean.setBusinessDate(Integer.toString(Integer.parseInt(firstDataRow.getBusinessDate()) + i));
        }
        ObjectMapper om = new ObjectMapper();
        IncomeInfo info = new IncomeInfo();
        info.setBusinessDateEd("531");
        info.setBusinessDateSt("501");
        info.setStoreCode("PSH160");
        info.setStoreName("撒旦发生");
        info.setRows(rowData);
//        String infoJson = om.writeValueAsString(info);
        ExcelWriter writer = null;
        try {
//            info = om.readValue(infoJson, IncomeInfo.class);
            // 最后一行
            RowBean bottom = new RowBean();
            bottom.setBusinessDate("合计");
            String storeCode = info.getStoreCode();
            String storeName = info.getStoreName();

            String fileName = "E:\\done\\管理费\\" + storeName + "-管理费(" + info.getBusinessDateSt() + "至" + info.getBusinessDateEd() + ").xlsx";
            String head = "收入(" + info.getBusinessDateSt() + "至" + info.getBusinessDateEd() + ")";
            // 收款各类型金额
//            Map<String, Integer> receiptTypeIntMap = new HashMap<>();
            // 获取TypeBean的amount属性，然后获取注解实例及其value
            /*Field field = TypeBean.class.getDeclaredField("amount");
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            InvocationHandler h = Proxy.getInvocationHandler(property);
            Field hField = h.getClass().getDeclaredField("memberValues");
            String[] value = property.value();
            List<TypeBean> receiptList = new ArrayList<>();*/
            // 堂食虚收各类型金额
//            Map<String, Integer> eatInTypeIntMap = new HashMap<>();
            // 外卖虚收各类型金额
//            Map<String, Integer> deliveryTypeIntMap = new HashMap<>();
            /*TypeBean bean1 = new TypeBean("现金", 1, 22.34);
            TypeBean bean2 = new TypeBean("支付宝", 2, 11.23);
            TypeBean bean3 = new TypeBean("微信", 3, 12.56);
            List<TypeBean> receiptList = Arrays.asList(bean1, bean2, bean3);
            List<TypeBean> eatInList = Arrays.asList(bean1, bean2, bean3);
            List<TypeBean> deliveryList = Arrays.asList(bean1, bean2, bean3);
            List<RowBean> rows = info.getRows();
            RowBean firstDataRow = new RowBean("1", "SHA186", "肯德基", "501", 12.34, 12.45, 12.56, 12.67, receiptList, eatInList, deliveryList);
            rows.add(firstDataRow);
            for (int i = 1; i <= 30; i++) {
                RowBean bean = new RowBean();
                BeanUtils.copyProperties(rows.get(0), bean);
                bean.setSequence(Integer.toString(i + 1));
                bean.setBusinessDate(Integer.toString(Integer.parseInt(firstDataRow.getBusinessDate()) + i));
            }*/
            // 各大类Map所包含的小类计算
//            calTypesMap(receiptTypeIntegerMap, hallTypeIntegerMap, deliveryTypeIntegerMap, rows);

            writer = EasyExcel.write(fileName, RowBean.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet(head).build();
            writer.write(rowData, writeSheet);
        } catch (Exception e) {
            try {
                logger.info("Excel构造出错:{}", om.writeValueAsString(e));
            } catch (JsonProcessingException jsonProcessingException) {
                logger.info("jackson出错");
            }
            e.printStackTrace();
        }/* finally {
            if (writer != null) {
                writer.close();
            }
        }*/
    }

    // 计算各大类的子类型
    /*private void calTypesMap(Map<String, Integer> receiptTypeIntegerMap, Map<String, Integer> hallTypeIntegerMap,
                            Map<String, Integer> deliveryTypeIntegerMap, List<RowBean> rows) {
        if (!CollectionUtils.isEmpty(rows)) {
            int sequence = 1;
            for (RowBean bean : rows) {
                bean.setSequence(Integer.toString(sequence++));
                List<TypeBean> receiptList = bean.getReceipts();
                if (!CollectionUtils.isEmpty(receiptList)) {
                    for (TypeBean receipt : receiptList) {
                        int columnNum = 0;
                        if (!receiptTypeIntegerMap.containsKey(receipt.getPaymentName())) {
                            receiptTypeIntegerMap.put(receipt.getPaymentName(), ++columnNum);
                        }
                    }
                }
                List<TypeBean> hallVirtualList = bean.getEatInList();
                if (!CollectionUtils.isEmpty(receiptList)) {
                    for (TypeBean hall : hallVirtualList) {
                        int columnNum = 0;
                        if (!hallTypeIntegerMap.containsKey(hall.getPaymentName())) {
                            hallTypeIntegerMap.put(hall.getPaymentName(), ++columnNum);
                        }
                    }
                }
                List<TypeBean> deliveryVirtualList = bean.getDeliveryList();
                if (!CollectionUtils.isEmpty(receiptList)) {
                    for (TypeBean delivery : deliveryVirtualList) {
                        int columnNum = 0;
                        if (!deliveryTypeIntegerMap.containsKey(delivery.getPaymentName())) {
                            deliveryTypeIntegerMap.put(delivery.getPaymentName(), ++columnNum);
                        }
                    }
                }
            }
        }
    }*/
}
