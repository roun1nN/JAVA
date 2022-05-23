package Bean;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.NumberFormat;

public class TypeBean {

    @ExcelIgnore
    private String paymentName;
    // 用于子类型内部排序
    @ExcelIgnore
    private int paymentSort;
    @ExcelProperty("${this.paymentName}")
    @NumberFormat("_ ¥* #,##0.00_ ;_ ¥* -#,##0.00_ ;_ ¥* \"-\"??_ ;_ @_ ")
    private Double amount;

    public TypeBean() {
    }

    public TypeBean(String paymentName, int paymentSort, Double amount) {
        this.paymentName = paymentName;
        this.paymentSort = paymentSort;
        this.amount = amount;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
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
