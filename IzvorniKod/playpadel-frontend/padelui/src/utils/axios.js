import axios from 'axios';

// Function to get the JWT token from storage (e.g., localStorage)
const getToken = () => {
  return localStorage.getItem('jwtToken'); // Adjust to how you store your token
};

// Create an Axios instance with a default configuration
const api = axios.create({
  baseURL: 'http://localhost:8080/api/v1', // Your API URL here
  headers: {
    'Content-Type': 'application/json',
  },
});

// Add a request interceptor to inject the token into the headers
api.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      // If the token is available, set the Authorization header
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // Handle error
    return Promise.reject(error);
  }
);

export default api;