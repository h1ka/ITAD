package com.intertad.task.service;

import com.intertad.task.models.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllByIds(List<String> ids);

    List<String> getAllByIdentifier(String identifier);

    Product getByIdentifier(String identifier);
}
