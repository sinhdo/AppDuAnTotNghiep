package com.example.appduantotnghiep.model;

public class Discount  {
    private String id;
    private String code;
    private double amount;
    private boolean checked = false;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Discount(){

    }

    public Discount(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Discount(String id, String code, double amount, boolean checked) {
        this.id = id;
        this.code = code;
        this.amount = amount;
        this.checked = checked;
    }
}
