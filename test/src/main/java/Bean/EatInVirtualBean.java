package Bean;

import com.alibaba.excel.annotation.ExcelProperty;

import java.math.BigDecimal;

public class EatInVirtualBean {

    @ExcelProperty("${paymentName}")
    private String paymentName;

    private BigDecimal amount;

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
