package com.intertad.task.service;

import com.intertad.task.models.Product;
import com.intertad.task.models.Sum;

import java.math.BigDecimal;
import java.util.List;

public interface CountService {

    Sum count(List<Product> products);

    BigDecimal getChangeDue(Sum sum, BigDecimal clientSum);
}
