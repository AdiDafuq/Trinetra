import { useEffect } from "react";
import api from "./api/axios";
import Dashboard from "./pages/Dashboard";

function App() {

  useEffect(() => {
    api.get("/health")
      .then((response) => {
        console.log(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return <Dashboard />;
}

export default App;