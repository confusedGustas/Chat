export async function fetchChatHistory(roomId) {
    const response = await fetch(`http://localhost:8080/message/get/all/${roomId}`);
    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.error || "Failed to load chat history");
    }
    return await response.json();
}

export async function createRoom() {
    const response = await fetch("http://localhost:8080/room/create");
    if (!response.ok) {
        const error = await response.json();
        throw new Error(error.error || "Failed to create room");
    }
    return await response.json();
}

export function setupAPICalls() {}
