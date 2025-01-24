import { BrowserRouter as Router, Routes, Route, Navigate } from "react-router-dom";
import HomePage from "./pages/HomePage";
import AuthPage from "./pages/AuthPage";
import CourtsPage from "./pages/CourtsPage";

const isAuthenticated = () => {
  const token = localStorage.getItem("jwtToken");
  
  if (!token) return false; 
  
  const decodedToken = JSON.parse(atob(token.split('.')[1]));
  const isExpired = decodedToken.exp * 1000 < Date.now(); 
  
  if (isExpired) {
    localStorage.removeItem("jwtToken");
    return false;
  }

  return true;
};

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/auth" element={<AuthPage />} />
        <Route path="/home" element={isAuthenticated() ? <HomePage /> : <Navigate to="/auth" />} />
        <Route path="/court" element={isAuthenticated() ? <CourtsPage /> : <Navigate to="/auth" />} />
        <Route path="/" element={<Navigate to={isAuthenticated() ? "/home" : "/auth"} />} />
      </Routes>
    </Router>
  );
}

export default App;