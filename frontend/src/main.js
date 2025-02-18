import { initializeChatRoom } from "./chat.js";
import { setupWebSocket } from "./websocket.js";
import { setupAPICalls } from "./api.js";

document.addEventListener("DOMContentLoaded", () => {
    setupAPICalls();
    setupWebSocket();
    initializeChatRoom();
});
