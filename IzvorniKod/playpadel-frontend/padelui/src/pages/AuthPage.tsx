import { useState } from "react";
import Login from "../components/Login";
import Register from "../components/Register";
import Navbar from "../components/Navbar";

interface AuthPageProps {
  onAuthChange: (isAuthenticated: boolean) => void;
}

function AuthPage({ onAuthChange }: AuthPageProps) {
  const [isLoginPage, setIsLoginPage] = useState(true);

  return (
    <div className="container mt-5">
      <Navbar setIsLoginPage={setIsLoginPage} />
      <div className="row justify-content-center mt-4">
        <div className="col-md-6">
          {isLoginPage ? (
            <Login onAuthChange={onAuthChange} />
          ) : (
            <Register onAuthChange={onAuthChange} />
          )}
        </div>
      </div>
    </div>
  );
}

export default AuthPage;
