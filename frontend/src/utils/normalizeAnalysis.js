export function normalizeAnalysis(data) {
  if (!data) return null;

  // backend returns LISTS → we flatten safely
  const scoreObj = data.scores?.[0] || {};

  return {
    // core metrics
    score: scoreObj.value ?? 0,
    confidence: scoreObj.confidence ?? 0,
    dependencyRisk: scoreObj.dependencyRisk ?? "unknown",

    // explanation
    whyNotHigherScore: data.summary ?? "No explanation provided",

    // structured lists (backend-native)
    kpiMapping: data.kpiMappings ?? [],
    gaps: data.gaps ?? [],
    evidence: data.evidence ?? [],
    supervisorBiases: scoreObj.biases ?? [],

    followUpQuestions: data.followUpQuestions ?? [],

    // IMPORTANT FIX
    transcript:
      data.transcript ??
      data.request?.transcript ??
      ""
  };
}