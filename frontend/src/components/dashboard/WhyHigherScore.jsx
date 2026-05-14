import React from "react";
import { cardStyle } from "./cardStyle";

export default function WhyHigherScore({ explanation }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>
        Why not a higher score?
      </h2>

      <p style={{ marginTop: "10px" }}>
        {explanation ?? "No explanation provided."}
      </p>
    </div>
  );
}