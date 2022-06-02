package output.bean;

import java.math.BigDecimal;

public class ManagementBean {

//    @ExcelIgnore
    private String name;
    // 用于子类型内部排序
//    @ExcelIgnore
    private int paymentSort;
//    @ExcelProperty("${this.paymentName}")
//    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double amount;

    public ManagementBean() {
    }

    public ManagementBean(String paymentName, int paymentSort, Double amount) {
        this.name = paymentName;
        this.paymentSort = paymentSort;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPaymentSort() {
        return paymentSort;
    }

    public void setPaymentSort(int paymentSort) {
        this.paymentSort = paymentSort;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
