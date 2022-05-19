import Bean.DeliveryVirtualBean;
import Bean.HallVirtualBean;
import Bean.PaymentBean;
import Bean.RowBean;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import utils.CollectionUtils;
import utils.DateUtils;

import java.util.*;

public class EasyExcelExport {

    @Test
    public void incomeExcelExport() {
        String infoJson = "";
        ObjectMapper om = new ObjectMapper();
        IncomeExcelInfo info = null;
        try {
            info = om.readValue(infoJson, IncomeExcelInfo.class);
            List<RowBean> data = info.getRows();
            RowBean bottom = new RowBean();
            bottom.setBusinessDate("合计");
            String storeCode = info.getStoreCode();
            String storeName = info.getStoreName();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.stringToDate(info.getBusinessDateSt(), DateUtils.DATE_TO_STRING_BUSINESS_PATTERN));
            int monthDayNum = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            String fileName = storeName + "-收入(" + info.getBusinessDateSt() + "至" + info.getBusinessDateEd() + ").xlsx";
            String head = "收入(" + info.getBusinessDateSt() + "至" + info.getBusinessDateEd() + ")";
            // 收款各类型金额
            Map<String, Integer> receiptTypeIntegerMap = new HashMap<>();
            // 堂食虚收各类型金额
            Map<String, Integer> hallTypeIntegerMap = new HashMap<>();
            // 外卖虚收各类型金额
            Map<String, Integer> deliveryTypeIntegerMap = new HashMap<>();
            List<RowBean> rows = info.getRows();
            if (!CollectionUtils.isEmpty(rows)) {
                int sequence = 1;
                for (RowBean bean : rows) {
                    bean.setSequence(Integer.toString(sequence++));
                    List<PaymentBean> receiptList = bean.getReceipts();
                    if (!CollectionUtils.isEmpty(receiptList)) {
                        for (PaymentBean receipt : receiptList) {
                            int columnNum = 0;
                            if (!receiptTypeIntegerMap.containsKey(receipt.getPaymentName())) {
                                receiptTypeIntegerMap.put(receipt.getPaymentName(), ++columnNum);
                            }
                        }
                    }
                    List<PaymentBean> hallVirtualList = bean.getHallVirtualList();
                    if (!CollectionUtils.isEmpty(receiptList)) {
                        for (PaymentBean hall : hallVirtualList) {
                            int columnNum = 0;
                            if (!hallTypeIntegerMap.containsKey(hall.getPaymentName())) {
                                hallTypeIntegerMap.put(hall.getPaymentName(), ++columnNum);
                            }
                        }
                    }
                    List<PaymentBean> deliveryVirtualList = bean.getDeliveryVirtualList();
                    if (!CollectionUtils.isEmpty(receiptList)) {
                        for (PaymentBean delivery : deliveryVirtualList) {
                            int columnNum = 0;
                            if (!deliveryTypeIntegerMap.containsKey(delivery.getPaymentName())) {
                                deliveryTypeIntegerMap.put(delivery.getPaymentName(), ++columnNum);
                            }
                        }
                    }
                }
            }
            ExcelWriterBuilder writerBuilder = EasyExcel.write(fileName);
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            ExcelWriter writer = writerBuilder.build();
            writer.write()
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
