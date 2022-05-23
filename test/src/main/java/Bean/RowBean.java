package Bean;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.format.NumberFormat;
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
    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double businessAmount;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("实收合计")
    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double actualInAmount;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("堂食虚收合计")
    // 会计专用格式
    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double eatInRevenue;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("外卖虚收合计")
    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double deliveryRevenue;
    @ExcelProperty("收款")
    private List<TypeBean> receipts;
    @ExcelProperty("堂食虚收")
    private List<TypeBean> eatInList;
    @ExcelProperty("外卖虚收")
    private List<TypeBean> deliveryList;

    public RowBean() {
    }

    public RowBean(String sequence, String storeCode, String storeName, String businessDate, Double businessAmount,
                   Double actualInAmount, Double eatInRevenue, Double deliveryRevenue, List<TypeBean> receipts,
                   List<TypeBean> eatInList, List<TypeBean> deliveryList) {
        this.sequence = sequence;
        this.storeCode = storeCode;
        this.storeName = storeName;
        this.businessDate = businessDate;
        this.businessAmount = businessAmount;
        this.actualInAmount = actualInAmount;
        this.eatInRevenue = eatInRevenue;
        this.deliveryRevenue = deliveryRevenue;
        this.receipts = receipts;
        this.eatInList = eatInList;
        this.deliveryList = deliveryList;
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

    public Double getBusinessAmount() {
        return businessAmount;
    }

    public void setBusinessAmount(Double businessAmount) {
        this.businessAmount = businessAmount;
    }

    public Double getActualInAmount() {
        return actualInAmount;
    }

    public void setActualInAmount(Double actualInAmount) {
        this.actualInAmount = actualInAmount;
    }

    public Double getEatInRevenue() {
        return eatInRevenue;
    }

    public void setEatInRevenue(Double eatInRevenue) {
        this.eatInRevenue = eatInRevenue;
    }

    public Double getDeliveryRevenue() {
        return deliveryRevenue;
    }

    public void setDeliveryRevenue(Double deliveryRevenue) {
        this.deliveryRevenue = deliveryRevenue;
    }

    public List<TypeBean> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<TypeBean> receipts) {
        this.receipts = receipts;
    }

    public List<TypeBean> getEatInList() {
        return eatInList;
    }

    public void setEatInList(List<TypeBean> eatInList) {
        this.eatInList = eatInList;
    }

    public List<TypeBean> getDeliveryList() {
        return deliveryList;
    }

    public void setDeliveryList(List<TypeBean> deliveryList) {
        this.deliveryList = deliveryList;
    }
}
