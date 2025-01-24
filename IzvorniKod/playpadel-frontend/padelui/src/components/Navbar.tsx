import { useNavigate } from 'react-router-dom';

interface NavbarProps {
  setIsLoginPage: (value: boolean) => void;
}

function Navbar({ setIsLoginPage }: NavbarProps) {
  const navigate = useNavigate();
  const userType = localStorage.getItem("userType");  

  // Function to check if the user is authenticated
  const isAuthenticated = () => {
    const token = localStorage.getItem("jwtToken");
    return !!token;  // Returns true if token exists, false otherwise
  };

  // Handle logout action
  const handleLogout = () => {
    localStorage.removeItem("jwtToken");  
    localStorage.removeItem("userId");
    localStorage.removeItem("userType");  
    navigate("/auth");  
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-danger w-100">
      <div className="container-fluid">
        {/* Navbar brand or logo */}
        <a className="navbar-brand text-light" href="/">
          PadelApp
        </a>

        <div className="ms-auto d-flex align-items-center">
          {/* Show "Courts" button if userType is OWNER */}
          {isAuthenticated() && userType === "OWNER" && (
            <button
              onClick={() => navigate("/court")}
              className="btn btn-light mx-2"
            >
              Courts
            </button>
          )}

          {isAuthenticated() ? (
            // Show Logout button if the user is authenticated
            <button onClick={handleLogout} className="btn btn-light mx-2">
              Logout
            </button>
          ) : (
            // Show Login and Register buttons if the user is not authenticated
            <>
              <button
                onClick={() => setIsLoginPage(true)}
                className="btn btn-light mx-2"
              >
                Login
              </button>
              <button
                onClick={() => setIsLoginPage(false)}
                className="btn btn-light mx-2"
              >
                Register
              </button>
            </>
          )}
        </div>
      </div>
    </nav>
  );
}

export default Navbar;
