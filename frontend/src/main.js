import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

let stompClient = null;
let currentRoomId = null;

async function loadHistory() {
    try {
        const response = await fetch(`http://localhost:8080/message/get/all/${currentRoomId}`);
        if (!response.ok) return;
        const messages = await response.json();
        document.getElementById('messages').innerHTML = '';
        messages.forEach(displayMessage);
    } catch (error) {
        console.error(error);
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
        console.error(error);
    }
}

document.getElementById('createRoomForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    document.getElementById('messages').innerHTML = '';
    document.getElementById('chatSection').classList.add('hidden');

    try {
        const response = await fetch('http://localhost:8080/room/create');
        if (!response.ok) return;
        const room = await response.json();
        await initializeRoom(room.id);
    } catch (error) {
        console.error(error);
    }
});

document.getElementById('joinRoomBtn').addEventListener('click', async () => {
    const roomId = document.getElementById('joinRoomId').value.trim();
    if (!roomId) return;
    // If already in this room, simply reload the history
    if (roomId === currentRoomId) {
        await loadHistory();
        return;
    }
    try {
        const response = await fetch(`http://localhost:8080/message/get/all/${roomId}`);
        if (response.ok) await initializeRoom(roomId);
    } catch (error) {
        console.error(error);
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
    if (stompClient?.connected) {
        try {
            await stompClient.deactivate();
        } catch (error) {
            console.error(error);
        }
    }

    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000
    });

    stompClient.onConnect = () => {
        stompClient.subscribe(`/topic/rooms/${currentRoomId}`, (message) => {
            displayMessage(JSON.parse(message.body));
        });
    };

    stompClient.activate();
}

function displayMessage(message) {
    const messagesDiv = document.getElementById('messages');
    const messageElement = document.createElement('div');
    messageElement.className = 'bg-gray-700 p-4 rounded-lg space-y-1';
    messageElement.innerHTML = `
    <div class="flex items-center gap-2">
      <span class="font-medium text-indigo-400">${message.sender}</span>
      <span class="text-sm text-gray-400">${new Date(message.time).toLocaleTimeString()}</span>
    </div>
    <div class="text-gray-100">${message.content}</div>
  `;
    messagesDiv.appendChild(messageElement);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}
