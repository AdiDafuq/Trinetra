import React from "react";

export default function ScoreCard({ score }) {
  return (
    <div
      style={{
        padding: "16px",
        backgroundColor: "#ffffff",
        borderRadius: "8px",
        boxShadow: "0 1px 6px rgba(0,0,0,0.1)",
        color: "#000000"
      }}
    >
      <h2 style={{ fontSize: "18px", fontWeight: "600", color: "#000000" }}>
        Overall Score
      </h2>

      <p style={{ fontSize: "32px", fontWeight: "700", marginTop: "8px", color: "#000000" }}>
        {score ?? "N/A"}
      </p>
    </div>
  );
}