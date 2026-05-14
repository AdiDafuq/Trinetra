import React from "react";
import { cardStyle } from "./cardStyle";

export default function DependencyRiskCard({ risk }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>Dependency Risk</h2>
      <p style={{ fontSize: "20px", marginTop: "8px" }}>
        {risk ?? "unknown"}
      </p>
    </div>
  );
}