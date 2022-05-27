package output.bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;
import java.util.List;

public class RowBean {

    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("序号")
    private String sequence;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("门店编码")
    private String storeCode;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("门店名称")
    private String storeName;
    @ContentLoopMerge(eachRow = 2)
    @DateTimeFormat("yyyy-MM-dd")
    @ExcelProperty("日期")
    private String businessDate;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("营业额")
//    @NumberFormat("_ * #,##0.00_ ;_ * -#,##0.00_ ;_ * \"-\"??_ ;_ @_ ")
    private Double totalAmount;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("实收合计")
//    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double incomeAmount;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("堂食虚收合计")
    // 会计专用格式
//    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double eatInDisAmount;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("外卖虚收合计")
//    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double deliveryDisAmount;
    @ExcelProperty("收款")
    private List<ManagementBean> incomeList;
    @ExcelProperty("堂食虚收")
    private List<ManagementBean> eatInDisList;
    @ExcelProperty("外卖虚收")
    private List<ManagementBean> deliveryDisList;

    public RowBean() {
    }

    public RowBean(String sequence, String storeCode, String storeName, String businessDate, Double businessAmount,
                   Double actualInAmount, Double eatInRevenue, Double deliveryRevenue, List<ManagementBean> receipts,
                   List<ManagementBean> eatInList, List<ManagementBean> deliveryList) {
        this.sequence = sequence;
        this.storeCode = storeCode;
        this.storeName = storeName;
        this.businessDate = businessDate;
        this.totalAmount = businessAmount;
        this.incomeAmount = actualInAmount;
        this.eatInDisAmount = eatInRevenue;
        this.deliveryDisAmount = deliveryRevenue;
        this.incomeList = receipts;
        this.eatInDisList = eatInList;
        this.deliveryDisList = deliveryList;
    }

    public String getSequence() {
        return sequence;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(String businessDate) {
        this.businessDate = businessDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(Double incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public Double getEatInDisAmount() {
        return eatInDisAmount;
    }

    public void setEatInDisAmount(Double eatInDisAmount) {
        this.eatInDisAmount = eatInDisAmount;
    }

    public Double getDeliveryDisAmount() {
        return deliveryDisAmount;
    }

    public void setDeliveryDisAmount(Double deliveryDisAmount) {
        this.deliveryDisAmount = deliveryDisAmount;
    }

    public List<ManagementBean> getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(List<ManagementBean> incomeList) {
        this.incomeList = incomeList;
    }

    public List<ManagementBean> getEatInDisList() {
        return eatInDisList;
    }

    public void setEatInDisList(List<ManagementBean> eatInDisList) {
        this.eatInDisList = eatInDisList;
    }

    public List<ManagementBean> getDeliveryDisList() {
        return deliveryDisList;
    }

    public void setDeliveryDisList(List<ManagementBean> deliveryDisList) {
        this.deliveryDisList = deliveryDisList;
    }
}
