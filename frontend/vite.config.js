import { defineConfig } from 'vite'
import tailwindcss from '@tailwindcss/vite'

export default defineConfig({
    define: {
        global: 'window'
    },
    plugins: [
        tailwindcss(),
    ],
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
