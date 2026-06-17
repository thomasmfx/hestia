import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    // Em modo real (VITE_USE_MOCK=false), encaminha as chamadas da API
    // para o backend Spring em :8080 — evita CORS sem tocar no backend.
    proxy: {
      '/hospedes': 'http://localhost:8080',
      '/auth': 'http://localhost:8080',
    },
  },
});
