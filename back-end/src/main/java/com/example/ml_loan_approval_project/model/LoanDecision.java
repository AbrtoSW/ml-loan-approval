package com.example.ml_loan_approval_project.model;

public class LoanDecision {
    private String decision;
    private String reason;

    public LoanDecision(){}

    public LoanDecision(String decision, String reason) {
        this.decision = decision;
        this.reason = reason;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
