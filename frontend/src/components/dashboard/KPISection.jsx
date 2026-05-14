import React from "react";
import { cardStyle } from "./cardStyle";

export default function KPISection({ kpis }) {
  return (
    <div style={cardStyle}>
      <h2 style={{ fontSize: "18px", fontWeight: "600" }}>KPI Mapping</h2>

      <div style={{ marginTop: "10px" }}>
        {kpis?.length ? (
          kpis.map((kpi, i) => (
            <div key={i}>{JSON.stringify(kpi)}</div>
          ))
        ) : (
          <p>No KPIs available</p>
        )}
      </div>
    </div>
  );
}