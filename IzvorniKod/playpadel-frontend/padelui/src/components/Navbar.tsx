import { useNavigate } from 'react-router-dom';

interface NavbarProps {
  setIsLoginPage: (value: boolean) => void;
}

function Navbar({ setIsLoginPage }: NavbarProps) {
  const navigate = useNavigate();
  const userType = localStorage.getItem("userType");  


  const isAuthenticated = () => {
    const token = localStorage.getItem("jwtToken");
    return !!token;  
  };

  const handleLogout = () => {
    localStorage.removeItem("jwtToken");  
    localStorage.removeItem("userId");
    localStorage.removeItem("userType");  
    navigate("/auth");  
  };

  return (
    <nav className="navbar navbar-expand-lg navbar-light bg-danger w-100">
      <div className="container-fluid">

        <a className="navbar-brand text-light" href="/">
          PadelApp
        </a>

        <div className="ms-auto d-flex align-items-center">
          {}
          {isAuthenticated() && userType === "OWNER" && (
            <button
              onClick={() => navigate("/court")}
              className="btn btn-light mx-2"
            >
              Courts
            </button>
          )}

          {isAuthenticated() ? (
            <button onClick={handleLogout} className="btn btn-light mx-2">
              Logout
            </button>
          ) : (
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
