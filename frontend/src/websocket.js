import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import { displayMessage, showError } from "./chat.js";

let stompClient = null;
let reconnectAttempts = 0;
const MAX_RECONNECT_ATTEMPTS = 5;
const RECONNECT_DELAY = 5000;

export function setupWebSocket() {
    connectWebSocket("dummy");
}

export function publishMessage(roomId, message) {
    if (stompClient && stompClient.connected) {
        stompClient.publish({
            destination: `/app/message/send/${roomId}`,
            body: JSON.stringify(message)
        });
    } else {
        showError("WebSocket is not connected");
    }
}

export function connectWebSocketForRoom(roomId) {
    return new Promise((resolve, reject) => {
        if (stompClient && stompClient.connected) {
            stompClient.deactivate().then(() => {
                connectWebSocket(roomId, resolve, reject);
            });
        } else {
            connectWebSocket(roomId, resolve, reject);
        }
    });
}

function connectWebSocket(roomId, resolve, reject) {
    const socket = new SockJS("http://localhost:8080/ws");
    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 0,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000
    });
    stompClient.onConnect = () => {
        reconnectAttempts = 0;
        stompClient.subscribe(`/topic/rooms/${roomId}`, (message) => {
            displayMessage(JSON.parse(message.body));
        });
        stompClient.subscribe("/user/queue/errors", (message) => {
            try {
                const error = JSON.parse(message.body);
                showError(error.error || "An error occurred");
            } catch (e) {
                showError("An unexpected error occurred");
            }
            document.getElementById("username").disabled = false;
            document.getElementById("username").classList.remove("bg-gray-100", "cursor-not-allowed");
        });
        resolve();
    };
    stompClient.onStompError = (frame) => {
        showError(frame.headers.message || "Connection error");
        attemptReconnect(roomId, resolve, reject);
    };
    stompClient.onWebSocketError = () => {
        showError("WebSocket connection error");
        attemptReconnect(roomId, resolve, reject);
    };
    stompClient.activate();
}

function attemptReconnect(roomId, resolve, reject) {
    if (reconnectAttempts < MAX_RECONNECT_ATTEMPTS) {
        reconnectAttempts++;
        setTimeout(() => {
            connectWebSocket(roomId, resolve, reject);
        }, RECONNECT_DELAY);
    } else {
        showError("Unable to reconnect to the chat server. Please refresh the page.");
        reject();
    }
}
