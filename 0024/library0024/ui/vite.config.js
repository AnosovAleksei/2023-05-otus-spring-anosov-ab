import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
// const path = require('path'); 
import { resolve } from 'path';



export default defineConfig({
  build: {
    outDir: resolve(__dirname, '../src/main/resources/static'),

    },

  plugins: [react()],
  server: {
    proxy: {
      '/api': {
          target: 'http://localhost:8000',
          changeOrigin: true,
          secure: false
      }
    }
  },
  // root: path.join(__dirname, "src"),
  
})
