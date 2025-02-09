import { defineConfig } from 'vite'
export default defineConfig({
    define: {
        global: 'window'
    },
    server: {
        proxy: {
            '/rooms': 'http://localhost:8080',
            '/ws': {
                target: 'http://localhost:8080',
                ws: true
            }
        }
    }
})
