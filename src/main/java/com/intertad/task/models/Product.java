package com.intertad.task.models;

import com.intertad.task.enums.TaxCategory;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private Product(String identifier, String name, BigDecimal price, TaxCategory taxCategory) {
        this.identifier = identifier;
        this.name = name;
        this.price = price;
        this.taxCategory = taxCategory;
    }

    private String identifier;

    private String name;

    private BigDecimal price;

    private TaxCategory taxCategory;

    public String getIdentifier() {
        return identifier;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TaxCategory getTaxCategory() {
        return taxCategory;
    }

    @Override
    public String toString() {
        return "Product :" +
                " name='" + name + '\'' +
                ", identifier='" + identifier + '\'' +
                ", price=" + price +
                ", taxCategory=" + taxCategory +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(identifier, product.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    public static class ModelBuilder {
        private String identifier;

        private String name;

        private BigDecimal price;

        private TaxCategory taxCategory;

        public ModelBuilder() {
        }

        public ModelBuilder identifier(String identifier) {
            this.identifier = identifier;
            return this;
        }

        public ModelBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ModelBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }

        public ModelBuilder tax(TaxCategory taxCategory) {
            this.taxCategory = taxCategory;
            return this;
        }

        public Product build() {
            return new Product(this.identifier, this.name, this.price, this.taxCategory);
        }
    }
}
