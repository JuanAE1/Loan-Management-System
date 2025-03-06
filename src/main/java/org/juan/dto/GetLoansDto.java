package org.juan.dto;

public class GetLoansDto {
    private int id;
    private Double amount;
    private Integer term;
    private String type;
    private String status;
    private String owner;

    public GetLoansDto() {
    }

    public GetLoansDto(int id, Double amount, Integer term, String type, String status, String owner) {
        this.id = id;
        this.amount = amount;
        this.term = term;
        this.type = type;
        this.status = status;
        this.owner = owner;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }
}
