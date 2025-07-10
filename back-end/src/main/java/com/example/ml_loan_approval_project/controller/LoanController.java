package com.example.ml_loan_approval_project.controller;

import com.example.ml_loan_approval_project.model.LoanDecision;
import com.example.ml_loan_approval_project.model.Applicant;
import com.example.ml_loan_approval_project.service.LoanApprovalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/loan")

public class LoanController {

    private final LoanApprovalService loanApprovalService;

    @Autowired
    public LoanController(LoanApprovalService loanApprovalService) {
        this.loanApprovalService = loanApprovalService;
    }

    @PostMapping("/apply")
    public LoanDecision applyForLoan(@RequestBody Applicant applicant) {
        return loanApprovalService.evaluateApplicant(applicant);
    }
}

