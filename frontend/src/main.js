import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
let stompClient = null;
let currentRoomId = null;
let reconnectAttempts = 0;
const MAX_RECONNECT_ATTEMPTS = 5;
const RECONNECT_DELAY = 5000;
async function loadHistory() {
    try {
        const response = await fetch(`http://localhost:8080/message/get/all/${currentRoomId}`);
        if (!response.ok) {
            const error = await response.json();
            showError(error.error || 'Failed to load chat history');
            return;
        }
        const messages = await response.json();
        document.getElementById('messages').innerHTML = '';
        messages.forEach(displayMessage);
    } catch (error) {
        showError('Failed to load chat history');
    }
}
async function initializeRoom(roomId) {
    currentRoomId = roomId;
    document.getElementById('joinRoomId').value = roomId;
    document.getElementById('chatSection').classList.remove('hidden');
    document.getElementById('username').disabled = false;
    document.getElementById('username').classList.remove('bg-gray-100', 'cursor-not-allowed');
    try {
        await connectWebSocket();
        await loadHistory();
    } catch (error) {
        showError('Failed to initialize room');
    }
}
document.getElementById('createRoomForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    document.getElementById('messages').innerHTML = '';
    document.getElementById('chatSection').classList.add('hidden');
    try {
        const response = await fetch('http://localhost:8080/room/create');
        if (!response.ok) {
            const error = await response.json();
            showError(error.error || 'Failed to create room');
            return;
        }
        const room = await response.json();
        await initializeRoom(room.id);
    } catch (error) {
        showError('Failed to create room');
    }
});
document.getElementById('joinRoomBtn').addEventListener('click', async () => {
    const roomId = document.getElementById('joinRoomId').value.trim();
    if (!roomId) return;
    if (roomId === currentRoomId) {
        await loadHistory();
        return;
    }
    try {
        const response = await fetch(`http://localhost:8080/message/get/all/${roomId}`);
        if (!response.ok) {
            const error = await response.json();
            showError(error.error || 'Failed to join room');
            return;
        }
        await initializeRoom(roomId);
    } catch (error) {
        showError('Failed to join room');
    }
});
document.getElementById('messageInput').addEventListener('keydown', (e) => {
    if (e.key === 'Enter' && !e.shiftKey) {
        e.preventDefault();
        document.getElementById('sendButton').click();
    }
});
document.getElementById('sendButton').addEventListener('click', () => {
    const messageInput = document.getElementById('messageInput');
    const usernameInput = document.getElementById('username');
    const content = messageInput.value.trim();
    const sender = usernameInput.value.trim();
    if (content && sender && currentRoomId) {
        if (!usernameInput.disabled) {
            usernameInput.disabled = true;
            usernameInput.classList.add('bg-gray-100', 'cursor-not-allowed');
        }
        stompClient.publish({
            destination: `/app/message/send/${currentRoomId}`,
            body: JSON.stringify({ sender, content })
        });
        messageInput.value = '';
    }
});
async function connectWebSocket() {
    if (stompClient?.connected) await stompClient.deactivate();
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 0,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000
    });
    stompClient.onConnect = () => {
        reconnectAttempts = 0;
        stompClient.subscribe(`/topic/rooms/${currentRoomId}`, (message) => {
            displayMessage(JSON.parse(message.body));
        });
        stompClient.subscribe('/user/queue/errors', (message) => {
            try {
                const error = JSON.parse(message.body);
                showError(error.error || 'An error occurred');
            } catch (e) {
                showError('An unexpected error occurred');
            }
            document.getElementById('username').disabled = false;
            document.getElementById('username').classList.remove('bg-gray-100', 'cursor-not-allowed');
        });
    };
    stompClient.onStompError = (frame) => {
        showError(frame.headers.message || 'Connection error');
        attemptReconnect();
    };
    stompClient.onWebSocketError = () => {
        showError('WebSocket connection error');
        attemptReconnect();
    };
    stompClient.activate();
}
function attemptReconnect() {
    if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
        reconnectAttempts++;
        setTimeout(connectWebSocket, RECONNECT_DELAY);
    } else {
        showError('Unable to reconnect to the chat server. Please refresh the page.');
    }
}
function showError(message) {
    const chatSection = document.getElementById('chatSection');
    const existingErrors = chatSection.getElementsByClassName('error-banner');
    while (existingErrors.length > 0) {
        existingErrors[0].remove();
    }
    const banner = document.createElement('div');
    banner.className = 'error-banner bg-red-600 text-white p-4 rounded-lg mb-4 flex justify-between items-center';
    banner.innerHTML = `<span class="flex items-center gap-2">${message}</span><button onclick="this.parentElement.remove()">&times;</button>`;
    chatSection.prepend(banner);
    setTimeout(() => banner.remove(), 5000);
}
function displayMessage(message) {
    const messagesDiv = document.getElementById('messages');
    const messageElement = document.createElement('div');
    messageElement.className = 'bg-gray-700 p-4 rounded-lg space-y-1';
    messageElement.innerHTML = `<div class="flex items-center gap-2"><span class="font-medium text-indigo-400">${message.sender}</span><span class="text-sm text-gray-400">${new Date(message.time).toLocaleTimeString()}</span></div><div class="text-gray-100">${message.content}</div>`;
    messagesDiv.appendChild(messageElement);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}
