package com.trinetra.service.ai;

public class PromptTemplates {

    // =========================
    // GLOBAL RULES
    // =========================
    public static final String STRICT_JSON =
            """
            You must return ONLY valid JSON.
            No markdown. No explanation. No extra text.
            """;

    // =========================
    // EVIDENCE EXTRACTION
    // =========================
    public static final String EVIDENCE_PROMPT =
            """
            Extract key evidence from the transcript.

            Return JSON:
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
            """;

    // =========================
    // SCORING
    // =========================
    public static final String SCORING_PROMPT =
            """
            Evaluate the transcript.

            Return JSON:
            {
              "score": number (0-10),
              "execution_vs_systems": "execution" | "systems",
              "survivability": "low" | "medium" | "high",
              "helpfulness_bias": "low" | "medium" | "high",
              "presence_bias": "low" | "medium" | "high",
              "reason": string
            }

            Transcript:
            %s
            """;

    // =========================
    // GAP ANALYSIS
    // =========================
    public static final String GAP_PROMPT =
            """
            Identify reasoning gaps.

            Return JSON:
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
            """;

    // =========================
    // FOLLOW-UP QUESTIONS
    // =========================
    public static final String FOLLOWUP_PROMPT =
            """
            Generate follow-up questions.

            Return JSON:
            {
              "questions": [
                string
              ]
            }

            Transcript:
            %s
            """;

    // =========================
    // BIAS DETECTION
    // =========================
    public static final String BIAS_PROMPT =
            """
            Detect biases.

            Return JSON:
            {
              "helpfulness_bias": "low" | "medium" | "high",
              "presence_bias": "low" | "medium" | "high",
              "notes": string
            }

            Transcript:
            %s
            """;
}