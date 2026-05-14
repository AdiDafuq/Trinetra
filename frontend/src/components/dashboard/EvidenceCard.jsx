import React from "react";
import { cardStyle } from "./cardStyle";

export default function EvidenceCard({ evidence }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>Evidence</h2>

      <div style={{ marginTop: "10px" }}>
        {evidence?.length ? (
          evidence.map((e, i) => (
            <div key={i}>
              {typeof e === "string" ? e : JSON.stringify(e)}
            </div>
          ))
        ) : (
          <p>No evidence found</p>
        )}
      </div>
    </div>
  );
}