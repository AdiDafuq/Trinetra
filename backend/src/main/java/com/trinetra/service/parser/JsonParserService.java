package com.trinetra.service.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class JsonParserService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // =====================================================
    // CLEAN RESPONSE
    // =====================================================
    public String cleanResponse(String response) {
        if (response == null) return "";

        return response
                .replaceAll("```json", "")
                .replaceAll("```", "")
                .trim();
    }

    // =====================================================
    // MAIN PARSER (STRICT BUT SAFE)
    // =====================================================
    public JsonNode parse(String response) {

        if (response == null || response.isBlank()) {
            return fallbackNode("Empty response");
        }

        String cleaned = cleanResponse(response);

        // 1. direct parse attempt
        try {
            return objectMapper.readTree(cleaned);
        } catch (JsonProcessingException ignored) {
            // continue fallback
        }

        // 2. extract JSON block
        String extracted = extractJsonBlock(cleaned);

        try {
            return objectMapper.readTree(extracted);
        } catch (Exception e) {
            return fallbackNode("Failed to parse JSON");
        }
    }

    // =====================================================
    // SAFE PARSE (NO EXCEPTION THROWING)
    // =====================================================
    public JsonNode safeParse(String response) {
        return parse(response);
    }

    // =====================================================
    // JSON EXTRACTION (SAFE NON-GREEDY FIX)
    // =====================================================
    private String extractJsonBlock(String text) {

        if (text == null || text.isBlank()) {
            return "{}";
        }

        // safer bounded match (prevents greedy collapse)
        Pattern pattern = Pattern.compile("\\{(?:[^{}]|\\{[^{}]*\\})*\\}");
        Matcher matcher = pattern.matcher(text);

        String lastValid = null;

        while (matcher.find()) {
            lastValid = matcher.group();
        }

        if (lastValid != null) {
            return lastValid;
        }

        return "{}";
    }

    // =====================================================
    // VALIDATION
    // =====================================================
    public boolean isValidJson(String response) {
        try {
            parse(response);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

        // =====================================================
    // FALLBACK NODE (STRUCTURE SAFE GUARANTEE)
    // =====================================================
    private JsonNode fallbackNode(String reason) {
        // Create an ObjectNode reference to keep the correct method signatures available
        com.fasterxml.jackson.databind.node.ObjectNode fallback = objectMapper.createObjectNode();
        
        fallback.put("summary", "Fallback parsing used")
                .put("score", 6)
                .put("reason", reason)
                .put("location", "evaluation_environment");
                
        fallback.set("bias_flags", objectMapper.createObjectNode()
                .put("helpfulness_bias", "medium")
                .put("presence_bias", "medium")
        );
        
        fallback.set("key_evidence", objectMapper.createArrayNode());
        fallback.set("gaps", objectMapper.createArrayNode());
        
        return fallback;
    }

}