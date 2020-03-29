package com.intertad.task.enums;

import java.util.Arrays;
import java.util.List;

public enum Tax {

    STATE(0.063),
    COUNTRY(0.007, TaxCategory.GROCERIES),
    CITY(0.02);

    private double proc;

    private List<TaxCategory> notApply;

    Tax(double proc, TaxCategory... notApply) {
        this.proc = proc;
        this.notApply = Arrays.asList(notApply);
    }

    public double getProc() {
        return proc;
    }

    public List<TaxCategory> getNotApply() {
        return notApply;
    }
}
