import axios from 'axios';
import { useAuthStore } from '../store/auth';

console.log('🔍 NEXT_PUBLIC_API_URL:', process.env.NEXT_PUBLIC_API_URL);

export const httpClient = axios.create({
  baseURL: process.env.NEXT_PUBLIC_API_URL,
});

httpClient.interceptors.request.use((config) => {
  console.log('🔍 HTTP Request:', {
    method: config.method?.toUpperCase(),
    url: config.url,
    baseURL: config.baseURL,
    fullURL: `${config.baseURL}${config.url}`
  });
  
  const tokens = useAuthStore.getState().tokens;
  
  if (tokens) {
    config.headers.Authorization = `Bearer ${tokens}`;
  }
  
  return config;
});
