import React, { useState } from "react";
import Login from "./Login";
import Register from "./Register";
const Homepage: React.FC = () => {
  const [isLogin, setIsLogin] = useState(true);

  return (
    <div className="container">
      <h1>Welcome to the Homepage</h1>
      <div>
        <button
          className={isLogin ? "" : "secondary"}
          onClick={() => setIsLogin(true)}
        >
          Login
        </button>
        <button
          className={!isLogin ? "" : "secondary"}
          onClick={() => setIsLogin(false)}
        >
          Register
        </button>
      </div>
      {isLogin ? <Login /> : <Register />}
    </div>
  );
};

export default Homepage;
