import client from "./client";

export const analyzeTranscript = async (transcript) => {
  const payload = {
    transcript
  };

  const response = await client.post("/api/analyze", payload);
  return response.data;
};