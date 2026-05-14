import { sampleTranscripts } from "../../utils/sampleTranscripts";

export default function SampleTranscriptPicker({ onSelect }) {
  return (
    <div style={{ marginBottom: "12px" }}>
      <h4>Sample Transcripts</h4>

      <div style={{ display: "flex", gap: "8px", flexWrap: "wrap" }}>
        {sampleTranscripts.map((item, idx) => (
          <button
            key={idx}
            onClick={() => onSelect(item.text)}
            style={{
              padding: "6px 10px",
              border: "1px solid #ccc",
              borderRadius: "6px",
              cursor: "pointer"
            }}
          >
            {item.title}
          </button>
        ))}
      </div>
    </div>
  );
}