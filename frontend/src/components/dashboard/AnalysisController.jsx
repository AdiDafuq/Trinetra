import React, { useState } from "react";
import useAnalysis from "../../hooks/useAnalysis";
import AnalysisDashboard from "./AnalysisDashboard";
import SampleTranscriptPicker from "./SampleTranscriptPicker";

export default function AnalysisController() {
  const { loading, error, data, runAnalysis, reset } = useAnalysis();
  const [transcript, setTranscript] = useState("");

  const handleAnalyze = async () => {
    if (!transcript.trim()) return;
    await runAnalysis(transcript);
  };

  return (
  <div className="grid grid-cols-1 lg:grid-cols-2 gap-4 p-4 bg-gray-50 text-black">
      <SampleTranscriptPicker onSelect={setTranscript} />

      <textarea
        value={transcript}
        onChange={(e) => setTranscript(e.target.value)}
        placeholder="Paste transcript here..."
        className="w-full p-3 border rounded h-32 text-black bg-white"
      />

      <div className="flex gap-2">
        <button
          onClick={handleAnalyze}
          disabled={loading}
          className="px-4 py-2 bg-blue-600 text-white rounded"
        >
          {loading ? "Analyzing..." : "Analyze"}
        </button>

        <button
          onClick={reset}
          className="px-4 py-2 bg-gray-300 text-black rounded"
        >
          Reset
        </button>
      </div>

      {error && (
        <div className="text-red-600 font-medium">
          {error}
        </div>
      )}

      <AnalysisDashboard data={data} loading={loading} />
    </div>
  );
}