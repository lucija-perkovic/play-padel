import axios from 'axios';
import {User} from "@/models/user";

const instance = axios.create({
  baseURL: 'http://localhost:8000/api/v1',
  headers: {
    'Content-Type': 'application/json',
  },
});

enum Controllers {
  USERS = '/users',
  LOGIN = '/login',
}

instance.interceptors.request.use((config) => {
  if (config.headers) {
    config.headers['X-Accesstoken'] = localStorage.getItem('accessToken');
  }
  return config;
});

export const register = async (user: User) => {
  console.log("backend service", user);
  return instance.post(Controllers.USERS, user);
}

export const login = async (email: string, password: string) => {
  return instance.post(`${Controllers.LOGIN}/access-token`, { email, password });
}