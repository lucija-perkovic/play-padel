import { useState, FormEvent } from "react";
import { useNavigate } from 'react-router-dom';  
import api from '../utils/axios';

interface RegisterProps {
  onAuthChange: (isAuthenticated: boolean) => void; // Accept onAuthChange prop
}

function Register({ onAuthChange }: RegisterProps) {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    firstName: "",
    lastName: "",
    userType: "",
    contactNumber: "",
    address: "",
    padelHallName: "",
  });
  const [loading, setLoading] = useState<boolean>(false);
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const validatePhoneNumber = (phoneNumber: string) => {
    const phoneRegex = /^09\d{8}$/;
    return phoneRegex.test(phoneNumber);
  };

  const handleRegister = async (e: FormEvent) => {
    e.preventDefault();

    if (!validatePhoneNumber(formData.contactNumber)) {
      setError("Please enter a valid phone number in the format 09XXXXXXXX.");
      alert("Please enter a valid phone number");
      return;
    }

    setLoading(true);
    setError(null);

    try {
      const response = await api.post("/user/register", formData);
      
      if (response.status === 200) {
        // After successful registration, log the user in automatically
        const loginResponse = await api.post("/user/login", {
          username: formData.username,
          password: formData.password,
        });

        if (loginResponse.status === 200) {
          const { token, userDto } = loginResponse.data;  // Fix: use loginResponse, not registration response
          const { id, userType } = userDto;
    
          localStorage.setItem("jwtToken", token); 
          localStorage.setItem("userId", id);
          localStorage.setItem("userType", userType); 
          onAuthChange(true); // Update authentication status
          navigate("/home");
        } else {
          setError("Login failed. Please try again.");
        }
      } else {
        setError("Registration failed. Please try again.");
      }
    } catch (error) {
      setError("An error occurred. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="d-flex justify-content-center align-items-start min-vh-100 pt-4">
      <div className="w-100 max-w-md p-4">
        <h2 className="h4 text-center mb-4 text-danger">Register</h2>
        <form onSubmit={handleRegister}>
          {Object.keys(formData).map((key) => {
            if (key === "userType") {
              // Special case for dropdown (userType)
              return (
                <div key={key} className="mb-3">
                  <label className="form-label">User Type:</label>
                  <select
                    name="userType"
                    value={formData[key as keyof typeof formData]}
                    onChange={handleChange}
                    required
                    className="form-control"
                  >
                    <option value="">Select User Type</option>
                    <option value="OWNER">OWNER</option>
                    <option value="PLAYER">PLAYER</option>
                  </select>
                </div>
              );
            }

            if (key === "padelHallName" && formData.userType !== "OWNER") {
              // Don't render padelHallName field if the user type is not "OWNER"
              return null;
            }

            return (
              <div key={key} className="mb-3">
                <label className="form-label">
                  {key.replace(/([A-Z])/g, " $1")}:
                </label>
                <input
                  type={key.includes("password") ? "password" : "text"}
                  name={key}
                  value={formData[key as keyof typeof formData]}
                  onChange={handleChange}
                  required={key === "username" || key === "password" || key === "userType" || key === "firstName" || key === "lastName" || (key === "padelHallName" && formData.userType === "OWNER")}
                  className="form-control"
                />
              </div>
            );
          })}

          <button
            type="submit"
            className="w-100 btn btn-danger"
            disabled={loading}
          >
            {loading ? "Registering..." : "Register"}
          </button>
        </form>

        {error && <div className="alert alert-danger mt-3">{error}</div>}
      </div>
    </div>
  );
}

export default Register;
