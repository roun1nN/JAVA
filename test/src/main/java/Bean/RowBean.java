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
    @NumberFormat("#.##%")
    private Double businessAmount;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("实收合计")
    @NumberFormat("#.##%")
    private Double actualInAmount;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("堂食虚收合计")
    @NumberFormat("#.##%")
    private Double hallVirtualRevenue;
    @ContentLoopMerge(eachRow = 2)
    @ExcelProperty("外卖虚收合计")
    @NumberFormat("#.##%")
    private Double deliveryVirtualRevenue;
    @ExcelProperty("收款")
    private List<PaymentBean> receipts;
    @ExcelProperty("堂食虚收")
    private List<PaymentBean> hallVirtualList;
    @ExcelProperty("外卖虚收")
    private List<PaymentBean> deliveryVirtualList;

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

    public Double getHallVirtualRevenue() {
        return hallVirtualRevenue;
    }

    public void setHallVirtualRevenue(Double hallVirtualRevenue) {
        this.hallVirtualRevenue = hallVirtualRevenue;
    }

    public Double getDeliveryVirtualRevenue() {
        return deliveryVirtualRevenue;
    }

    public void setDeliveryVirtualRevenue(Double deliveryVirtualRevenue) {
        this.deliveryVirtualRevenue = deliveryVirtualRevenue;
    }

    public List<PaymentBean> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<PaymentBean> receipts) {
        this.receipts = receipts;
    }

    public List<PaymentBean> getHallVirtualList() {
        return hallVirtualList;
    }

    public void setHallVirtualList(List<PaymentBean> hallVirtualList) {
        this.hallVirtualList = hallVirtualList;
    }

    public List<PaymentBean> getDeliveryVirtualList() {
        return deliveryVirtualList;
    }

    public void setDeliveryVirtualList(List<PaymentBean> deliveryVirtualList) {
        this.deliveryVirtualList = deliveryVirtualList;
    }
}
