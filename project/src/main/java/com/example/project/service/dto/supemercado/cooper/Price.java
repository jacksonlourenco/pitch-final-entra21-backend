package com.example.project.service.dto.supemercado.cooper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Price {
    private String criteriaReferenceCode;
    private double price;
    private String shoppingStoreReferenceCode;

    public void setCriteriaReferenceCode(String criteriaReferenceCode) {
        this.criteriaReferenceCode = criteriaReferenceCode;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setShoppingStoreReferenceCode(String shoppingStoreReferenceCode) {
        this.shoppingStoreReferenceCode = shoppingStoreReferenceCode;
    }

    public String getCriteriaReferenceCode() {
        return criteriaReferenceCode;
    }

    public double getPrice() {
        return price;
    }

    public String getShoppingStoreReferenceCode() {
        return shoppingStoreReferenceCode;
    }

    @Override
    public String toString() {
        return "Price{" +
                "criteriaReferenceCode='" + criteriaReferenceCode + '\'' +
                ", price=" + price +
                ", shoppingStoreReferenceCode='" + shoppingStoreReferenceCode + '\'' +
                "} \n";
    }
}
