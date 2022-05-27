package output.bean;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 出参
 */
public class ManagementFeeBean implements Serializable {
    private static final long serialVersionUID = 4239827372927808532L;
    private String storeCode;
    private String storeName;
    private String businessDate;
    private BigDecimal totalAmount = BigDecimal.ZERO;
    private BigDecimal incomeAmount = BigDecimal.ZERO;
    private BigDecimal eatInDisAmount = BigDecimal.ZERO;
    private BigDecimal deliveryDisAmount = BigDecimal.ZERO;
    private List<ManagementClassBean> classList;

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

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public BigDecimal getEatInDisAmount() {
        return eatInDisAmount;
    }

    public void setEatInDisAmount(BigDecimal eatInDisAmount) {
        this.eatInDisAmount = eatInDisAmount;
    }

    public BigDecimal getDeliveryDisAmount() {
        return deliveryDisAmount;
    }

    public void setDeliveryDisAmount(BigDecimal deliveryDisAmount) {
        this.deliveryDisAmount = deliveryDisAmount;
    }

    public List<ManagementClassBean> getClassList() {
        return classList;
    }

    public void setClassList(List<ManagementClassBean> classList) {
        this.classList = classList;
    }
}
