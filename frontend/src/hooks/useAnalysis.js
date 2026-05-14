import { useState } from "react";
import { analyzeTranscript } from "../api/analyzeApi";

export default function useAnalysis() {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [data, setData] = useState(null);

  const runAnalysis = async (transcript) => {
    setLoading(true);
    setError(null);

    try {
      const result = await analyzeTranscript(transcript);
      setData(result);
      return result;
    } catch (err) {
      setError(err?.response?.data?.message || "Analysis failed");
    } finally {
      setLoading(false);
    }
  };

  const reset = () => {
    setData(null);
    setError(null);
    setLoading(false);
  };

  return {
    loading,
    error,
    data,
    runAnalysis,
    reset
  };
}