package com.example.ml_loan_approval_project.service;

import com.example.ml_loan_approval_project.model.Applicant;
import com.example.ml_loan_approval_project.model.LoanDecision;
import com.example.ml_loan_approval_project.util.PromptBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import io.github.cdimascio.dotenv.Dotenv;

// THIS IS A QUICK PROTOCOL TO SEE HOW A MACHINE LEARNER CAN EVALUATE PEOPLE, IDEALLY YOU WOULD
// MAKE YOUR OWN ML USING YOUR OWN DATA


@Service
public class LoanApprovalService {

    private static final String OPENAI_API_KEY;
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    static{
        Dotenv dotenv = Dotenv.load();
        OPENAI_API_KEY = dotenv.get("OPENAI_API_KEY");
        if(OPENAI_API_KEY == null){
            throw new IllegalStateException("OPENAI API KEY is not set in the env file");
        }
    }


    public LoanDecision evaluateApplicant(Applicant applicant){
        try{
            String prompt = PromptBuilder.buildPrompt(applicant);

            String requestBody = """
                {
                  "model": "gpt-4",
                  "messages": [
                    {"role": "system", "content": "You are a helpful loan approval assistant."},
                    {"role": "user", "content": "%s"}
                  ],
                  "max_tokens": 150,
                  "temperature": 0
                }
                """.formatted(prompt.replace("\"", "\\\"").replace("\n", "\\n"));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPENAI_API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization","Bearer " + OPENAI_API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("OpenAI API STATUS: " + response.statusCode());
            System.out.println("OPENAI API RESPONSE: " + response.body());

            JsonNode root = objectMapper.readTree(response.body());
            String content = root.at("/choices/0/message/content").asText();

            System.out.println("Extracted Content: " + content);
            return objectMapper.readValue(content, LoanDecision.class);
        } catch (Exception e){
            e.printStackTrace();
            return new LoanDecision("denied", "Error processing loan approval: " + e.getMessage());
        }
    }
}
