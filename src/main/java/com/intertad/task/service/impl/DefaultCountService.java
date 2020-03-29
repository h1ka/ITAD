package com.intertad.task.service.impl;

import com.intertad.task.enums.Tax;
import com.intertad.task.enums.TaxCategory;
import com.intertad.task.models.Product;
import com.intertad.task.models.Sum;
import com.intertad.task.service.CountService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DefaultCountService implements CountService {

    @Override
    public Sum count(List<Product> products) {
        return countSumProducts(products);
    }

    @Override
    public BigDecimal getChangeDue(Sum sum, BigDecimal clientSum) {
        if (clientSum.compareTo(sum.getTotalAmountDue()) < 0) {
            throw new RuntimeException("Not enough money to pay");
        } else return clientSum.subtract(sum.getTotalAmountDue());
    }

    private Sum countSumProducts(List<Product> products) {
        BigDecimal subtotal = BigDecimal.ZERO;
        Map<Tax, BigDecimal> taxMap = new HashMap<>();

        Map<TaxCategory, List<Product>> productsGroupByTaxCategory =
                products.stream().collect(Collectors.groupingBy(Product::getTaxCategory));

        for (var category : productsGroupByTaxCategory.entrySet()) {
            BigDecimal subTotalCategory = countSubTotal(category.getValue());
            Map<Tax, BigDecimal> taxByCategory = countTax(subTotalCategory, category.getKey());

            for (var tax : taxByCategory.entrySet()) {
                taxMap.computeIfPresent(tax.getKey(), (k, v) -> v.add(tax.getValue()));
                taxMap.putIfAbsent(tax.getKey(), tax.getValue());
            }
            subtotal = subtotal.add(subTotalCategory);
        }

        return new Sum.SumBuilder().subtotal(subtotal).taxByCategory(taxMap).build();
    }

    private BigDecimal countSubTotal(List<Product> products) {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (var product : products) {
            subTotal = subTotal.add(product.getPrice());
        }
        return subTotal;
    }


    private Map<Tax, BigDecimal> countTax(BigDecimal sum, TaxCategory taxCategory) {
        Map<Tax, BigDecimal> taxes = new HashMap<>();
        for (var tax : Tax.values()) {
            if (!tax.getNotApply().contains(taxCategory)) {
                BigDecimal multiply = sum.multiply(BigDecimal.valueOf(tax.getProc()));
                taxes.put(tax, multiply);
            }
        }
        return taxes;
    }
}
