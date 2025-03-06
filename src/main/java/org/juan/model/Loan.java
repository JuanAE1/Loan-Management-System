package org.juan.model;

public class Loan {
    private Integer id;
    private Double amount;
    private Integer term;
    private String createdTime;
    private String updatedTime;
    private Integer typeId;
    private Integer statusId;
    private Integer userId;

    public Loan(){}

    public Loan(Integer id, Double amount, Integer term, String createdTime, String updatedTime, Integer typeId, Integer statusId, Integer userId) {
        this.id = id;
        this.amount = amount;
        this.term = term;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        this.typeId = typeId;
        this.statusId = statusId;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(String updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
