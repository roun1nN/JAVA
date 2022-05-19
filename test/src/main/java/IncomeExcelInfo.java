import Bean.DeliveryVirtualBean;
import Bean.HallVirtualBean;
import Bean.PaymentBean;
import Bean.RowBean;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ContentLoopMerge;

import java.util.List;

public class IncomeExcelInfo {
    // 起始日期
    private String businessDateSt;
    // 结束日期
    private String businessDateEd;
    // 门店编码
    private String storeCode;
    // 门店名称
    private String storeName;
    // 收款
    private List<PaymentBean> receipts;
    // 堂食虚收
    private List<PaymentBean> hallVirtuals;
    // 外卖虚收
    private List<PaymentBean> deliveryVirtuals;
    // 行数据
    private List<RowBean> rows;

    public String getBusinessDateSt() {
        return businessDateSt;
    }

    public void setBusinessDateSt(String businessDateSt) {
        this.businessDateSt = businessDateSt;
    }

    public String getBusinessDateEd() {
        return businessDateEd;
    }

    public void setBusinessDateEd(String businessDateEd) {
        this.businessDateEd = businessDateEd;
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

    public List<PaymentBean> getReceipts() {
        return receipts;
    }

    public void setReceipts(List<PaymentBean> receipts) {
        this.receipts = receipts;
    }

    public List<PaymentBean> getHallVirtuals() {
        return hallVirtuals;
    }

    public void setHallVirtuals(List<PaymentBean> hallVirtuals) {
        this.hallVirtuals = hallVirtuals;
    }

    public List<PaymentBean> getDeliveryVirtuals() {
        return deliveryVirtuals;
    }

    public void setDeliveryVirtuals(List<PaymentBean> deliveryVirtuals) {
        this.deliveryVirtuals = deliveryVirtuals;
    }

    public List<RowBean> getRows() {
        return rows;
    }

    public void setRows(List<RowBean> rows) {
        this.rows = rows;
    }
}
