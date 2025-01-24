import axios from 'axios';

const getToken = () => {
  return localStorage.getItem('jwtToken');
};


const api = axios.create({
  baseURL: 'https://playpadel.duckdns.org/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});


api.interceptors.request.use(
  (config) => {
    const token = getToken();
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default api;
