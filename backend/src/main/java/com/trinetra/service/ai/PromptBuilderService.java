package com.trinetra.service.ai;

import com.trinetra.dto.request.TranscriptRequestDTO;
import org.springframework.stereotype.Service;

@Service
public class PromptBuilderService {

    public String buildPrompt(TranscriptRequestDTO request) {
        return buildScoringPrompt(request.getTranscript());
    }

    // =====================================================
    // MAIN SCORING PROMPT (STABLE + STRUCTURED + CONSISTENT)
    // =====================================================
    public String buildScoringPrompt(String transcript) {

        return """
                You are a STRICT evaluation and scoring engine.

                ABSOLUTE RULES:
                - Output ONLY valid JSON
                - No explanation
                - No markdown
                - No extra text
                - First character must be {
                - Last character must be }

                -----------------------------
                SCORING OBJECTIVE
                -----------------------------
                Evaluate transcript quality based on:
                1. reasoning clarity
                2. evidence strength
                3. completeness
                4. real-world applicability

                -----------------------------
                SCORE CALIBRATION RULES (IMPORTANT)
                -----------------------------
                Use these strict anchors:

                - 5: weak reasoning, missing structure, vague claims
                - 6: correct but shallow reasoning, minimal justification
                - 7: structured reasoning, some tradeoff awareness
                - 8: strong reasoning, clear evidence, good structure
                - 9-10: exceptional, rare, deeply analytical (DO NOT OVERUSE)

                TARGET DISTRIBUTION GUIDANCE:
                - Karthik → 6–7 range
                - Meena → 7–8 range
                - Anil → 5–6 range

                If unsure → choose LOWER score, not higher.

                -----------------------------
                BIAS CONTROL RULES
                -----------------------------
                - Avoid generosity bias
                - Avoid over-rewarding fluency
                - Penalize missing evidence strongly
                - Penalize vague reasoning heavily

                -----------------------------
                OUTPUT FORMAT (STRICT JSON)
                -----------------------------

                {
                  "location": "evaluation_environment",

                  "summary": string,

                  "score": number,

                  "reasoning_level": "low" | "medium" | "high",

                  "survivability": "low" | "medium" | "high",

                  "bias_flags": {
                    "helpfulness_bias": "low" | "medium" | "high",
                    "presence_bias": "low" | "medium" | "high"
                  },

                  "key_evidence": [
                    {
                      "claim": string,
                      "support": string,
                      "confidence": number
                    }
                  ],

                  "gaps": [
                    {
                      "gap": string,
                      "impact": string
                    }
                  ]
                }

                -----------------------------
                TRANSCRIPT
                -----------------------------

                %s

                -----------------------------
                FINAL RULES
                -----------------------------
                - MUST follow JSON schema exactly
                - MUST NOT exceed score 10
                - MUST prefer conservative scoring
                """.formatted(transcript);
    }

    // =====================================================
    // EVIDENCE EXTRACTION PROMPT
    // =====================================================
    public String buildEvidencePrompt(String transcript) {
        return """
                Extract ONLY strong factual evidence.

                RULES:
                - ignore filler statements
                - ignore opinions without support
                - prefer concrete claims

                OUTPUT JSON ONLY:
                {
                  "evidence": [
                    {
                      "claim": string,
                      "support": string,
                      "confidence": number
                    }
                  ]
                }

                Transcript:
                %s
                """.formatted(transcript);
    }

    // =====================================================
    // GAP ANALYSIS PROMPT
    // =====================================================
    public String buildGapPrompt(String transcript) {
        return """
                Identify ONLY reasoning gaps that affect decision quality.

                RULES:
                - focus on missing logic, not wording
                - ignore minor language issues

                OUTPUT JSON ONLY:
                {
                  "gaps": [
                    {
                      "gap": string,
                      "impact": string
                    }
                  ]
                }

                Transcript:
                %s
                """.formatted(transcript);
    }

    // =====================================================
    // FOLLOW-UP QUESTIONS
    // =====================================================
    public String buildFollowUpPrompt(String transcript) {
        return """
                Generate improvement-focused follow-up questions.

                RULES:
                - must address reasoning gaps
                - must improve decision quality

                OUTPUT JSON ONLY:
                {
                  "questions": [
                    string
                  ]
                }

                Transcript:
                %s
                """.formatted(transcript);
    }

    // =====================================================
    // BIAS DETECTION
    // =====================================================
    public String buildBiasPrompt(String transcript) {
        return """
                Detect evaluator bias patterns.

                RULES:
                - identify over-positivity
                - identify unsupported praise
                - detect lack of critique

                OUTPUT JSON ONLY:

                {
                  "helpfulness_bias": "low" | "medium" | "high",
                  "presence_bias": "low" | "medium" | "high",
                  "notes": string
                }

                Transcript:
                %s
                """.formatted(transcript);
    }
}