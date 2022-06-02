import lombok.extern.slf4j.Slf4j;
import output.ManagementFeeOutput;
import output.bean.ManagementBean;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.junit.Test;
import output.bean.ManagementFeeBean;
import utils.CollectionUtils;
import utils.DateUtils;
import utils.EasyExcelUtil;


import java.text.ParseException;
import java.util.*;

@Slf4j
public class ManagementFeeExcelExport {

    @Test
    public void managementFeeExcelExport() {
        // 获取数据
        /*String jsonStrOfInput = JSONUtil.toJsonStr(input);
        String dataResult = HttpRequest.post(env.getProperty("managementFee.data.url"))
                .header("Authorization", env.getProperty("managementFee.header.auth"))
                .body(jsonStrOfInput).execute().body();*/
        try {
//            String dataResult = HttpUtils.doPost(env.getProperty("managementFee.data.url"), "");
            String dataResult = "{\"code\":200,\"message\":\"处理成功\",\"data\":{},\"success\":true}";
            JSONObject jsonData = JSONUtil.parseObj(dataResult);
            String dataString = jsonData.getStr("data");
            String outs = JSONUtil.parseObj(dataString).getStr("managementFeeList");
            JSONArray outJArr = JSONUtil.parseArray(outs);
//            List<ManagementFeeOutput> rowList = outJArr.toList(ManagementFeeOutput.class);
            List<ManagementFeeOutput> rowList = createRowList();
            String storeName = rowList.get(0).getStoreName();
            String dateSt = DateUtils.stringDateFormat(rowList.get(0).getBusinessDate(), DateUtils.DATE_TO_STRING_SHORT_PATTERN);
            String dateEd = DateUtils.stringDateFormat(rowList.get(rowList.size() - 1).getBusinessDate(), DateUtils.DATE_TO_STRING_SHORT_PATTERN);

            String fileName = "E:\\done\\收入\\" + storeName + "-收入(" + dateSt + "至" + dateEd + ").xlsx";
            String sheetName = "收入(" + dateSt + "至" + dateEd + ")";
            List<List<String>> head = head(rowList);
            List<List<String>> data = data(rowList);
            EasyExcelUtil easyExcelUtil = new EasyExcelUtil();
            easyExcelUtil.downloadFileNext(data, fileName, sheetName, head);
        } catch (Exception e) {
            log.error("导出Excel失败", e);
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建表头
     * @return
     */
    private List<List<String>> head(List<ManagementFeeOutput> rowData) {
        List<List<String>> list = new ArrayList<>();
        //表头数据
        String a="序号",b="门店编码",c="门店名称",d="日期",e="营业额",f="实收合计",g="堂食虚收合计",h="外卖虚收合计",i="收款",j="堂食虚收",k="外卖虚收";
        // Arrays.asList()中参数数量为head行数，表头数据相邻列若名字相同则会合并，全为basicInfo则该行合并为一整列
        //第一列，1/2/3行
        /*list.add(new ArrayList<>(Arrays.asList(basicInfo,a,a)));
        //第二列，1/2/3行
        list.add(new ArrayList<>(Arrays.asList(basicInfo,b,b)));
        //第三列
        list.add(new ArrayList<>(Arrays.asList(basicInfo,c,c)));
        //第四列
        list.add(new ArrayList<>(Arrays.asList(basicInfo,d,d)));
        //第五列
        list.add(new ArrayList<>(Arrays.asList(basicInfo,e,e)));
        //第六列
        list.add(new ArrayList<>(Arrays.asList(basicInfo,f,f)));
        //第七列
        list.add(new ArrayList<>(Arrays.asList(basicInfo,g,g)));
        //第八列
        list.add(new ArrayList<>(Arrays.asList(basicInfo,h,h)));*/
        //第一列，1/2行
        list.add(new ArrayList<>(Arrays.asList(a,a)));
        //第二列，1/2行
        list.add(new ArrayList<>(Arrays.asList(b,b)));
        //第三列
        list.add(new ArrayList<>(Arrays.asList(c,c)));
        //第四列
        list.add(new ArrayList<>(Arrays.asList(d,d)));
        //第五列
        list.add(new ArrayList<>(Arrays.asList(e,e)));
        //第六列
        list.add(new ArrayList<>(Arrays.asList(f,f)));
        //第七列
        list.add(new ArrayList<>(Arrays.asList(g,g)));
        //第八列
        list.add(new ArrayList<>(Arrays.asList(h,h)));
        List<ManagementBean> firstIncomeList = rowData.get(0).getIncomeList();
        List<ManagementBean> firstEatInDisList = rowData.get(0).getEatInDisList();
        List<ManagementBean> firstDeliveryDisList = rowData.get(0).getDeliveryDisList();
        // 二级表头,此处可遍历添加到一个list中，变为通用导出方法，支持新增大类
        if (!CollectionUtils.isEmpty(firstIncomeList)) {
            //动态根据类型生成
            /*for (ManagementBean bean : firstIncomeList) {
                list.add(new ArrayList<>(Arrays.asList(i,bean.getName())));
            }*/
            for (int n = 0; n < firstIncomeList.size(); n++) {
                if (n > 0 && firstIncomeList.get(n).getName().equals(firstIncomeList.get(n-1).getName())) {
                    firstIncomeList.get(n).setName(firstIncomeList.get(n-1).getName() + " ");
                }
                list.add(new ArrayList<>(Arrays.asList(i,firstIncomeList.get(n).getName())));
            }
        }
        if (!CollectionUtils.isEmpty(firstEatInDisList)) {
            //动态根据类型生成
            /*for (ManagementBean bean : firstEatInDisList) {
                list.add(new ArrayList<>(Arrays.asList(j,bean.getName())));
            }*/
            for (int n = 0; n < firstEatInDisList.size(); n++) {
                if (n > 0 && firstEatInDisList.get(n).getName().equals(firstEatInDisList.get(n-1).getName())) {
                    firstEatInDisList.get(n).setName(firstEatInDisList.get(n-1).getName() + " ");
                }
                list.add(new ArrayList<>(Arrays.asList(j,firstEatInDisList.get(n).getName())));
            }
        }
        if (!CollectionUtils.isEmpty(firstDeliveryDisList)) {
            //动态根据类型生成
            /*for (ManagementBean bean : firstDeliveryDisList) {
                list.add(new ArrayList<>(Arrays.asList(k,bean.getName())));
            }*/
            for (int n = 0; n < firstDeliveryDisList.size(); n++) {
                if (n > 0 && firstDeliveryDisList.get(n).getName().equals(firstDeliveryDisList.get(n-1).getName())) {
                    firstDeliveryDisList.get(n).setName(firstDeliveryDisList.get(n-1).getName() + " ");
                }
                list.add(new ArrayList<>(Arrays.asList(k,firstDeliveryDisList.get(n).getName())));
            }
        }
        return list;
    }

    /**
     * 组装数据
     * @param rowList
     * @return
     */
    private List<List<String>> data(List<ManagementFeeOutput> rowList) throws ParseException {
        List<List<String>> dataList = new ArrayList<>();
        int seq = 1;
        for (ManagementFeeOutput out : rowList) {
            List<String> singleRowData = new ArrayList<>();
            singleRowData.add(Integer.toString(seq++));
            singleRowData.add(out.getStoreCode());
            singleRowData.add(out.getStoreName());
            // 日期格式转换
            String businessDate = DateUtils.stringDateFormat(out.getBusinessDate(), DateUtils.DATE_TO_STRING_SHORT_PATTERN);
            singleRowData.add(businessDate);
//            singleRowData.add(out.getBusinessDate());
            singleRowData.add(out.getTotalAmount().toString());
            singleRowData.add(out.getIncomeAmount().toString());
            singleRowData.add(out.getEatInDisAmount().toString());
            singleRowData.add(out.getDeliveryDisAmount().toString());
            List<ManagementBean> incomeList = out.getIncomeList();
            if (!CollectionUtils.isEmpty(incomeList)) {
                for (ManagementBean bean : incomeList) {
                    singleRowData.add(bean.getAmount().toString());
                }
            }
            List<ManagementBean> eatInDisList = out.getEatInDisList();
            if (!CollectionUtils.isEmpty(incomeList)) {
                for (ManagementBean bean : eatInDisList) {
                    singleRowData.add(bean.getAmount().toString());
                }
            }
            List<ManagementBean> deliveryDisList = out.getDeliveryDisList();
            if (!CollectionUtils.isEmpty(incomeList)) {
                for (ManagementBean bean : deliveryDisList) {
                    singleRowData.add(bean.getAmount().toString());
                }
            }
            dataList.add(singleRowData);
        }
        List<String> summaryRow = new ArrayList<>();
        summaryRow.add("");
        summaryRow.add("");
        summaryRow.add("");
        summaryRow.add("合计");
        char column;
        for (int i = 0; i < dataList.get(0).size() - 4; i++) {
             column = (char) ('E' + i);
             summaryRow.add("SUM(" + column + "3:" + column + (dataList.size() + 2) + ")");
        }
        dataList.add(summaryRow);
        return dataList;
    }

    private List<ManagementFeeOutput> createRowList() {
        List<ManagementFeeOutput> data = new ArrayList<>(1);
        ManagementFeeOutput singleRow = new ManagementFeeOutput();
        data.add(singleRow);
        singleRow.setSequence("1");
        singleRow.setStoreCode("800208");
        singleRow.setStoreName("黄记煌上海普陀长寿路巴黎春天店");
        singleRow.setBusinessDate("2022-05-06");
        singleRow.setTotalAmount(3452.80);
        singleRow.setIncomeAmount(148.00);
        singleRow.setEatInDisAmount(0d);
        singleRow.setDeliveryDisAmount(148.00);
        List<ManagementBean> innerList = new ArrayList<>(1);
        ManagementBean bean = new ManagementBean("现金", 1, 148.00);
        innerList.add(bean);
        singleRow.setIncomeList(innerList);
        innerList = new ArrayList<>(1);
        bean = new ManagementBean("现金", 1, 148.00);
        innerList.add(bean);
        singleRow.setEatInDisList(innerList);
        innerList = new ArrayList<>(1);
        bean = new ManagementBean("现金", 1, 148.00);
        innerList.add(bean);
        singleRow.setDeliveryDisList(innerList);
        data.add(singleRow);
        return data;
    }
}


    /*@Test
    public void incomeExcelExport() {
    // 自造测试数据
        List<ManagementFeeOutput> rowData = new ArrayList<>();
        ManagementBean bean1 = new ManagementBean("现金", 1, new BigDecimal("22.34"));
        ManagementBean bean2 = new ManagementBean("支付宝", 2, new BigDecimal("11.23"));
        ManagementBean bean3 = new ManagementBean("微信", 3, new BigDecimal("12.56"));
        List<ManagementBean> receiptList = Arrays.asList(bean1, bean2, bean3);
        List<ManagementBean> eatInList = Arrays.asList(bean1, bean2, bean3);
        List<ManagementBean> deliveryList = Arrays.asList(bean1, bean2, bean3);
        ManagementFeeOutput firstDataRow = new ManagementFeeOutput("1", "PSH160", "PIZ", "501", 12.34, 12.45, 12.56, 12.67, receiptList, eatInList, deliveryList);
        rowData.add(firstDataRow);
        for (int i = 1; i <= 30; i++) {
            ManagementFeeOutput bean = new ManagementFeeOutput();
            BeanUtils.copyProperties(rowData.get(0), bean);
            bean.setSequence(Integer.toString(i + 1));
            bean.setBusinessDate(Integer.toString(Integer.parseInt(firstDataRow.getBusinessDate()) + i));
        }
        ManagementFeeOutput info = new ManagementFeeOutput();
//        String infoJson = om.writeValueAsString(info);
        ExcelWriter writer = null;
        try {
//            info = om.readValue(infoJson, IncomeInfo.class);
            // 最后一行
            ManagementFeeOutput bottom = new ManagementFeeOutput();
            bottom.setBusinessDate("合计");
//            String storeCode = rowData.get(0).getStoreCode();
            String storeName = rowData.get(0).getStoreName();

            String fileName = "E:\\done\\管理费\\" + storeName + "-管理费(" + rowData.get(0).getBusinessDate() + "至" + rowData.get(rowData.size() - 1) + ").xlsx";
            // 收款各类型金额
//            Map<String, Integer> receiptTypeIntMap = new HashMap<>();
            // 获取TypeBean的amount属性，然后获取注解实例及其value
            *//*Field field = TypeBean.class.getDeclaredField("amount");
            ExcelProperty property = field.getAnnotation(ExcelProperty.class);
            InvocationHandler h = Proxy.getInvocationHandler(property);
            Field hField = h.getClass().getDeclaredField("memberValues");
            String[] value = property.value();
            List<TypeBean> receiptList = new ArrayList<>();*//*
            // 堂食虚收各类型金额
//            Map<String, Integer> eatInTypeIntMap = new HashMap<>();
            // 外卖虚收各类型金额
//            Map<String, Integer> deliveryTypeIntMap = new HashMap<>();
            *//*TypeBean bean1 = new TypeBean("现金", 1, 22.34);
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
                rowList.add(bean);
            }*//*
            // 各大类Map所包含的小类计算
//            calTypesMap(receiptTypeIntegerMap, hallTypeIntegerMap, deliveryTypeIntegerMap, rows);

            writer = EasyExcel.write(fileName, ManagementFeeOutput.class).build();
            WriteSheet writeSheet = EasyExcel.writerSheet().build();
            writer.write(rowData, writeSheet);
        } catch (Exception e) {
            logger.error("Excel构造出错", e);
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.finish();
            }
        }
    }*/

// 计算各大类的子类型
    /*private void calTypesMap(Map<String, Integer> incomeTypeIntegerMap, Map<String, Integer> eatInDisTypeIntegerMap,
                            Map<String, Integer> deliveryDisTypeIntegerMap, List<ManagementFeeOutput> rows) {
        if (!CollectionUtils.isEmpty(rows)) {
            int sequence = 1;
            for (ManagementFeeOutput bean : rows) {
                bean.setSequence(Integer.toString(sequence++));
                List<ManagementBean> receiptList = bean.getIncomeList();
                if (!CollectionUtils.isEmpty(receiptList)) {
                    for (ManagementBean receipt : receiptList) {
                        int columnNum = 0;
                        if (!incomeTypeIntegerMap.containsKey(receipt.getName())) {
                            incomeTypeIntegerMap.put(receipt.getName(), ++columnNum);
                        }
                    }
                }
                List<ManagementBean> eatInDisList = bean.getEatInDisList();
                if (!CollectionUtils.isEmpty(receiptList)) {
                    for (ManagementBean hall : eatInDisList) {
                        int columnNum = 0;
                        if (!eatInDisTypeIntegerMap.containsKey(hall.getName())) {
                            eatInDisTypeIntegerMap.put(hall.getName(), ++columnNum);
                        }
                    }
                }
                List<ManagementBean> deliveryVirtualList = bean.getDeliveryDisList();
                if (!CollectionUtils.isEmpty(receiptList)) {
                    for (ManagementBean delivery : deliveryVirtualList) {
                        int columnNum = 0;
                        if (!deliveryDisTypeIntegerMap.containsKey(delivery.getName())) {
                            deliveryDisTypeIntegerMap.put(delivery.getName(), ++columnNum);
                        }
                    }
                }
            }
        }
    }*/
