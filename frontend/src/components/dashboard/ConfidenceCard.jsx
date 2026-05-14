import React from "react";
import { cardStyle } from "./cardStyle";

export default function ConfidenceCard({ confidence }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>Confidence</h2>
      <p style={{ fontSize: "28px", fontWeight: "700", marginTop: "8px" }}>
        {confidence ?? "N/A"}
      </p>
    </div>
  );
}