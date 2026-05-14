import React from "react";
import { cardStyle } from "./cardStyle";

export default function FollowUpSection({ questions }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>
        Follow-up Questions
      </h2>

      <div style={{ marginTop: "10px" }}>
        {questions?.length ? (
          questions.map((q, i) => <div key={i}>{q}</div>)
        ) : (
          <p>No follow-up questions</p>
        )}
      </div>
    </div>
  );
}