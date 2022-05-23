import Bean.RowBean;

import java.util.List;

public class IncomeInfo {
    // 起始日期
    private String businessDateSt;
    // 结束日期
    private String businessDateEd;
    // 门店编码
    private String storeCode;
    // 门店名称
    private String storeName;
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

    public List<RowBean> getRows() {
        return rows;
    }

    public void setRows(List<RowBean> rows) {
        this.rows = rows;
    }
}
