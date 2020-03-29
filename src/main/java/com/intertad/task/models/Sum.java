package com.intertad.task.models;

import com.intertad.task.enums.Tax;

import java.math.BigDecimal;
import java.util.Map;

public class Sum {

    private BigDecimal subtotal;

    private BigDecimal totalTax;

    private BigDecimal totalAmountDue;

    private Map<Tax, BigDecimal> taxByCategory;

    private Sum(BigDecimal subtotal, Map<Tax, BigDecimal> taxByCategory) {
        this.subtotal = subtotal;
        this.taxByCategory = taxByCategory;
        this.totalTax = countTotalTax();
        this.totalAmountDue = subtotal.add(totalTax);
    }

    private BigDecimal countTotalTax() {
        BigDecimal totalTax = BigDecimal.ZERO;
        for (var s : taxByCategory.values()) {
            totalTax = totalTax.add(s);
        }
        return totalTax;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public BigDecimal getTotalAmountDue() {
        return totalAmountDue;
    }

    public Map<Tax, BigDecimal> getTaxByCategory() {
        return taxByCategory;
    }

    public String printSum() {
        return "Subtotal=" + getSubtotal() +
                ", totalTax=" + getTotalTax() +
                ", totalAmountDue=" + getTotalAmountDue() +
                '}';
    }

    public String printSumWithDetailTax() {
        return "Subtotal = " + getSubtotal() +
                ", totalAmountDue=" + getTotalAmountDue() +
                ", tax=" + getTaxByCategory() +
                '}';
    }

    public static class SumBuilder {

        private BigDecimal subtotal;

        private Map<Tax, BigDecimal> taxByCategory;

        public SumBuilder subtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
            return this;
        }

        public SumBuilder taxByCategory(Map<Tax, BigDecimal> taxByCategory) {
            this.taxByCategory = taxByCategory;
            return this;
        }

        public Sum build() {
            return new Sum(subtotal, taxByCategory);
        }
    }
}
