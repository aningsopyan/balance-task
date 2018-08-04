package com.example.aningsopyan.myapplicationbalance.model;

public class ModelExpanse {
    private String description;
    private Double amount;

    public ModelExpanse(String description, Double amount){
        this.description= description;
        this.amount= amount;
    }

    public String getDescription(){
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setDescription(String label){
        this.description= label;
    }

    public void setAmount(Double become){
        this.amount= become;
    }

}
