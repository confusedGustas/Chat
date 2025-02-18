import { createRoom, fetchChatHistory } from "./api.js";
import { publishMessage, connectWebSocketForRoom } from "./websocket.js";

export function initializeChatRoom() {
    const createRoomForm = document.getElementById("createRoomForm");
    const joinRoomBtn = document.getElementById("joinRoomBtn");
    const sendButton = document.getElementById("sendButton");
    createRoomForm.addEventListener("submit", createRoomHandler);
    joinRoomBtn.addEventListener("click", joinRoomHandler);
    sendButton.addEventListener("click", sendMessageHandler);
    document.getElementById("messageInput").addEventListener("keydown", (e) => {
        if (e.key === "Enter" && !e.shiftKey) {
            e.preventDefault();
            sendButton.click();
        }
    });
}

async function createRoomHandler(e) {
    e.preventDefault();
    document.getElementById("messages").innerHTML = "";
    document.getElementById("chatSection").classList.add("hidden");
    try {
        const room = await createRoom();
        await initializeRoom(room.id);
    } catch (error) {
        showError("Failed to create room");
    }
}

async function joinRoomHandler() {
    const roomId = document.getElementById("joinRoomId").value.trim();
    if (!roomId) return;
    if (roomId === window.currentRoomId) {
        await loadHistory();
        return;
    }
    try {
        await initializeRoom(roomId);
    } catch (error) {
        showError("Failed to join room");
    }
}

function sendMessageHandler() {
    const messageInput = document.getElementById("messageInput");
    const usernameInput = document.getElementById("username");
    const content = messageInput.value.trim();
    const sender = usernameInput.value.trim();
    if (content && sender && window.currentRoomId) {
        if (!usernameInput.disabled) {
            usernameInput.disabled = true;
            usernameInput.classList.add("bg-gray-100", "cursor-not-allowed");
        }
        publishMessage(window.currentRoomId, { sender, content });
        messageInput.value = "";
    }
}

export async function initializeRoom(roomId) {
    window.currentRoomId = roomId;
    document.getElementById("joinRoomId").value = roomId;
    document.getElementById("chatSection").classList.remove("hidden");
    document.getElementById("username").disabled = false;
    document.getElementById("username").classList.remove("bg-gray-100", "cursor-not-allowed");
    try {
        await connectWebSocketForRoom(roomId);
        await loadHistory();
    } catch (error) {
        showError("Failed to initialize room");
    }
}

export async function loadHistory() {
    try {
        const messages = await fetchChatHistory(window.currentRoomId);
        document.getElementById("messages").innerHTML = "";
        messages.forEach(displayMessage);
    } catch (error) {
        showError("Failed to load chat history");
    }
}

export function displayMessage(message) {
    const messagesDiv = document.getElementById("messages");
    const messageElement = document.createElement("div");
    messageElement.className = "bg-gray-700 p-4 rounded-lg space-y-1";
    messageElement.innerHTML = `<div class="flex items-center gap-2"><span class="font-medium text-indigo-400">${message.sender}</span><span class="text-sm text-gray-400">${new Date(message.time).toLocaleTimeString()}</span></div><div class="text-gray-100">${message.content}</div>`;
    messagesDiv.appendChild(messageElement);
    messagesDiv.scrollTop = messagesDiv.scrollHeight;
}

export function showError(message) {
    const chatSection = document.getElementById("chatSection");
    const existingErrors = chatSection.getElementsByClassName("error-banner");
    while (existingErrors.length > 0) {
        existingErrors[0].remove();
    }
    const banner = document.createElement("div");
    banner.className = "error-banner bg-red-600 text-white p-4 rounded-lg mb-4 flex justify-between items-center";
    banner.innerHTML = `<span class="flex items-center gap-2">${message}</span><button onclick="this.parentElement.remove()">&times;</button>`;
    chatSection.prepend(banner);
    setTimeout(() => banner.remove(), 5000);
}
