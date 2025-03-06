package org.juan.dto;

public class CreateLoansDto {
    private Double amount;
    private Integer term;
    private Integer typeId;

    public CreateLoansDto() {
    }

    public CreateLoansDto(Double amount, Integer term, Integer typeId) {
        this.amount = amount;
        this.term = term;
        this.typeId = typeId;
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

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }
}