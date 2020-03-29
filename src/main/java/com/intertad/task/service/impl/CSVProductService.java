package com.intertad.task.service.impl;

import com.intertad.task.enums.TaxCategory;
import com.intertad.task.models.Product;
import com.intertad.task.service.ProductService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class CSVProductService implements ProductService {

    private Map<String, Product> productsMap = new TreeMap<>();
    private SortedSet<String> productIds = new TreeSet<>();


    public CSVProductService(String filename) {
        initData(filename);
    }

    private void initData(String filename) {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Product> products = records.stream().map(record ->
                new Product.ModelBuilder()
                        .identifier(record.get(0))
                        .name(record.get(1))
                        .price(BigDecimal.valueOf(Double.parseDouble(record.get(2))))
                        .tax(TaxCategory.ofSymbol(record.get(3)))
                        .build()
        ).collect(Collectors.toList());

        products.forEach(product -> {
            this.productsMap.put(product.getIdentifier(), product);
            this.productIds.add(product.getIdentifier());
        });

    }

    @Override
    public List<Product> getAllByIds(List<String> identifiers) {
        return identifiers.stream().map(key -> productsMap.get(key)).collect(Collectors.toList());
    }

    @Override
    public List<String> getAllByIdentifier(String identifier) {
        List<String> containId = new ArrayList<>();
        for (var code : productIds.tailSet(identifier)) {
            if (code.startsWith(identifier)) {
                containId.add(code);
            } else {
                break;
            }
        }
        return containId;
    }

    @Override
    public Product getByIdentifier(String identifier) {
        return productsMap.get(identifier);
    }
}
