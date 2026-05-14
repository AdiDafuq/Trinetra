import React from "react";
import { cardStyle } from "./cardStyle";

export default function BiasSection({ biases }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>
        Supervisor Biases
      </h2>

      <div style={{ marginTop: "10px" }}>
        {biases?.length ? (
          biases.map((b, i) => <div key={i}>{JSON.stringify(b)}</div>)
        ) : (
          <p>No biases detected</p>
        )}
      </div>
    </div>
  );
}