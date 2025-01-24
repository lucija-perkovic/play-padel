import { useState, FormEvent } from "react";
import { useNavigate } from 'react-router-dom';  
import api from '../utils/axios';

interface LoginProps {
  onAuthChange: (isAuthenticated: boolean) => void; // Accept the onAuthChange prop
}

function Login({ onAuthChange }: LoginProps) {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleLogin = async (e: FormEvent) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
  
    try {
      const response = await api.post("/user/login", {
        username,
        password,
      });
  
      if (response.status === 200) {
        console.log("Login successful:", response.data);
        const { token, userDto } = response.data;
        const { id, userType } = userDto;
  
        localStorage.setItem("jwtToken", token); 
        localStorage.setItem("userId", id);
        localStorage.setItem("userType", userType); 
        onAuthChange(true);  // Update authentication status
        navigate("/home"); 
      } else {
        setError("Login failed. Please try again.");
      }
    } catch (err) {
      console.error("Login error:", err);
      setError("An error occurred. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-start min-vh-100 pt-4">
      <div className="w-100 max-w-md p-4">
        <h2 className="h4 text-center mb-4 text-danger">Login</h2>
        <form onSubmit={handleLogin}>
          <div className="mb-3">
            <label htmlFor="username" className="form-label">
              Username:
            </label>
            <input
              id="username"
              type="text"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
              required
              className="form-control"
            />
            <div className="invalid-feedback">Username is required.</div>
          </div>

          <div className="mb-3">
            <label htmlFor="password" className="form-label">
              Password:
            </label>
            <input
              id="password"
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              className="form-control"
            />
            <div className="invalid-feedback">Password is required.</div>
          </div>

          <button
            type="submit"
            className="w-100 btn btn-danger"
            disabled={loading}
          >
            {loading ? "Logging in..." : "Login"}
          </button>
        </form>

        {error && <div className="alert alert-danger mt-3">{error}</div>}
      </div>
    </div>
  );
}

export default Login;
