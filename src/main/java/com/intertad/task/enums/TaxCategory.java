package com.intertad.task.enums;

/**
 * tax category, one of the following
 * a.	g (groceries)
 * b.	pf (prepared food)
 * c.	pd (prescription drug)
 * d.	nd (non-prescription drug)
 * e.	c (clothing)
 * f.	o (other items)
 */
public enum TaxCategory {

    GROCERIES("g"),
    PREPARED_FOOD("pf"),
    PRESCRIPTION_DRUG("pd"),
    NON_PRESCRIPTION_DRUG("nd"),
    CLOTHING("c"),
    OTHER_ITEMS("o");

    private String symbol;

    TaxCategory(String symbol) {
        this.symbol = symbol;
    }

    public static TaxCategory ofSymbol(String symbol) {
        for (var value : values()) {
            if (value.getSymbol().equals(symbol)) {
                return value;
            }
        }
        throw new RuntimeException("Symbol not found " + symbol);
    }

    public String getSymbol() {
        return symbol;
    }
}

