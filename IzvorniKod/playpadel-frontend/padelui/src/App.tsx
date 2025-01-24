import { useState, useEffect } from "react";
import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import HomePage from "./pages/HomePage";
import AuthPage from "./pages/AuthPage";
import CourtsPage from "./pages/CourtsPage";

// Utility to check if the token is valid
const isTokenValid = (token: string | null) => {
  if (!token) return false;

  try {
    const decodedToken = JSON.parse(atob(token.split(".")[1]));
    return decodedToken.exp * 1000 > Date.now(); // Check if the token is not expired
  } catch {
    return false;
  }
};

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  // Sync authentication status with localStorage on app load
  useEffect(() => {
    const token = localStorage.getItem("jwtToken");
    setIsAuthenticated(isTokenValid(token));
  }, []); // Run only once on component mount

  const handleAuthChange = (authStatus: boolean) => {
    setIsAuthenticated(authStatus);
  };

  return (
    <Router>
      <Routes>
        <Route
          path="/auth"
          element={<AuthPage onAuthChange={handleAuthChange} />}
        />
        <Route
          path="/home"
          element={isAuthenticated ? <HomePage /> : <Navigate to="/auth" />}
        />
        <Route
          path="/court"
          element={isAuthenticated ? <CourtsPage /> : <Navigate to="/auth" />}
        />
        <Route
          path="/"
          element={<Navigate to={isAuthenticated ? "/home" : "/auth"} />}
        />
      </Routes>
    </Router>
  );
}

export default App;
