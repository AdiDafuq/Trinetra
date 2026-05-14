import React from "react";
import { cardStyle } from "./cardStyle";

export default function TranscriptHighlighter({ transcript }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>Transcript</h2>

      <div style={{ marginTop: "10px", whiteSpace: "pre-wrap" }}>
        {transcript || "No transcript available"}
      </div>
    </div>
  );
}