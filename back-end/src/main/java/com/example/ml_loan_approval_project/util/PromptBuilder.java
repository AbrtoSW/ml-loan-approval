package com.example.ml_loan_approval_project.util;
import com.example.ml_loan_approval_project.model.Applicant;

public class PromptBuilder {
    public static String buildPrompt(Applicant applicant){
        return String.format(
                "You are a loan approval officer for a bank.\n" +
                "Given the following applicant data:\n" +
                "Name %s\n" +
                "Age %d\n" +
                "Annual Income: $%.2f \n" +
                "Requested Loan Amount: $%.2f\n" +
                "Credit Score: %d\n\n" +
                "Decide whether to approve or deny the loan, given the information provided.\n" +
                "Reply in JSON format with keys \"decision\" (approved or denied) and \"reason\" (brief explanation).",
                applicant.getName(),
                applicant.getAge(),
                applicant.getAnnualIncome(),
                applicant.getRequestedLoanAmount(),
                applicant.getCreditScore()
        );
    }

}
