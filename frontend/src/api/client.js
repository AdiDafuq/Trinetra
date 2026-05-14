import axios from "axios";
import ENV from "../config/env";

const client = axios.create({
  baseURL: ENV.API_BASE_URL,
  headers: {
    "Content-Type": "application/json"
  },
  timeout: 60000
});

// Optional: global response debugging
client.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error("API Error:", error?.response || error.message);
    return Promise.reject(error);
  }
);

export default client;