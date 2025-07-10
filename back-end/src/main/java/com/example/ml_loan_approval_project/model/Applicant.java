package com.example.ml_loan_approval_project.model;


public class Applicant {
    private String name;
    private int age;
    private double annualIncome;
    private double requestedLoanAmount;
    private int creditScore;

    // no-arg constructor for spring
    public Applicant() {}

    //quick constructor for testing
    public Applicant(String name, int age, double annualIncome, double requestedLoanAmount,int creditScore){
        this.name = name;
        this.age = age;
        this.annualIncome = annualIncome;
        this.requestedLoanAmount = requestedLoanAmount;
        this.creditScore = creditScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(double annualIncome) {
        this.annualIncome = annualIncome;
    }

    public double getRequestedLoanAmount() {
        return requestedLoanAmount;
    }

    public void setRequestedLoanAmount(double requestedLoanAmount) {
        this.requestedLoanAmount = requestedLoanAmount;
    }

    public int getCreditScore() {
        return creditScore;
    }

    public void setCreditScore(int creditScore) {
        this.creditScore = creditScore;
    }


}
