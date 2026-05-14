import React from "react";
import { cardStyle } from "./cardStyle";

export default function GapsSection({ gaps }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>Gaps</h2>

      <div style={{ marginTop: "10px" }}>
        {gaps?.length ? (
          gaps.map((g, i) => <div key={i}>{JSON.stringify(g)}</div>)
        ) : (
          <p>No gaps detected</p>
        )}
      </div>
    </div>
  );
}