import { useState, useEffect } from "react";
import api from "../api/axios";

import { normalizeAnalysis } from "../utils/normalizeAnalysis";
import AnalysisDashboard from "../components/dashboard/AnalysisDashboard";

function Dashboard() {
  const [selectedFile, setSelectedFile] = useState(null);
  const [loading, setLoading] = useState(false);
  const [message, setMessage] = useState("");
  const [result, setResult] = useState(null);
  const [history, setHistory] = useState([]);

  useEffect(() => {
    const saved = localStorage.getItem("analysis_history");
    if (saved) setHistory(JSON.parse(saved));
  }, []);

  const handleFileChange = (e) => {
    setSelectedFile(e.target.files[0] || null);
    setMessage("");
    setResult(null);
  };

  const handleAnalyze = async () => {
    if (!selectedFile) {
      setMessage("Please select a file first.");
      return;
    }

    setLoading(true);
    setMessage("");

    try {
      const formData = new FormData();
      formData.append("file", selectedFile);

      const response = await api.post("/feedback/upload", formData);

      const data = response.data;

      setResult(data);

      const updated = [
        {
          fileName: selectedFile.name,
          timestamp: new Date().toISOString(),
          data
        },
        ...history,
      ].slice(0, 10);

      setHistory(updated);
      localStorage.setItem("analysis_history", JSON.stringify(updated));

      setMessage("Analysis completed successfully ✅");
    } catch (err) {
      console.error("Analyze error:", err);
      setMessage(
        err?.response?.data?.message || "Upload failed ❌"
      );
    } finally {
      setLoading(false);
    }
  };

  // ✅ SAFE NORMALIZATION LAYER (NO CRASHES)
  const analysis = normalizeAnalysis(result);

  return (
    <div style={styles.container}>
      <h1 style={styles.title}>Trinetra AI Feedback Analyzer</h1>

      {/* UPLOAD CARD */}
      <div style={styles.card}>
        <h2 style={{ color: "#000" }}>Upload Feedback File</h2>

        <label style={styles.label}>Choose file:</label>

        <input
          type="file"
          onChange={handleFileChange}
          style={styles.fileInput}
        />

        {selectedFile && (
          <p style={styles.text}>
            Selected: {selectedFile.name}
          </p>
        )}

        <button
          style={styles.button}
          onClick={handleAnalyze}
          disabled={loading}
        >
          {loading ? "Analyzing..." : "Analyze"}
        </button>

        {message && <p style={styles.text}>{message}</p>}
      </div>

      {/* 🔥 ANALYSIS DASHBOARD */}
      <div style={{ marginTop: "30px" }}>
        <AnalysisDashboard data={analysis} loading={loading} />
      </div>
    </div>
  );
}

const styles = {
  container: {
    padding: "40px",
    fontFamily: "Arial",
    backgroundColor: "#f6f7fb",
    minHeight: "100vh",
  },

  title: {
    fontSize: "28px",
    marginBottom: "20px",
    color: "#000",
  },

  card: {
    backgroundColor: "#fff",
    padding: "20px",
    borderRadius: "10px",
    boxShadow: "0 2px 10px rgba(0,0,0,0.1)",
    width: "420px",
  },

  label: {
    display: "block",
    marginBottom: "8px",
    color: "#000",
    fontSize: "14px",
  },

  fileInput: {
    color: "#000",
    backgroundColor: "#fff",
    padding: "6px",
    borderRadius: "6px",
    border: "1px solid #ccc",
    width: "100%",
  },

  button: {
    marginTop: "10px",
    padding: "10px",
    backgroundColor: "#2563eb",
    color: "#fff",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
  },

  text: {
    marginTop: "10px",
    color: "#000",
    fontSize: "14px",
  },
};

export default Dashboard;