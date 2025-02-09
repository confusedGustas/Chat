import { Client } from '@stomp/stompjs'
import SockJS from 'sockjs-client'

let stompClient = null
let currentRoomId = null

document.getElementById('createRoomForm').addEventListener('submit', async (e) => {
    e.preventDefault()
    const response = await fetch('http://localhost:8080/rooms/create')
    const room = await response.json()
    currentRoomId = room.id
    document.getElementById('roomIdValue').textContent = currentRoomId
    document.getElementById('roomIdDisplay').classList.remove('hidden')
    document.getElementById('chatSection').classList.remove('hidden')
    connectWebSocket()
})

document.getElementById('sendButton').addEventListener('click', () => {
    const messageInput = document.getElementById('messageInput')
    const sender = document.getElementById('username').value
    const content = messageInput.value.trim()

    if (content && sender && currentRoomId) {
        const message = {
            sender: sender,
            content: content,
            roomId: currentRoomId
        }
        stompClient.publish({
            destination: `/app/chat/${currentRoomId}`,
            body: JSON.stringify(message)
        })
        messageInput.value = ''
    }
})

function connectWebSocket() {
    const socket = new SockJS('http://localhost:8080/ws')
    stompClient = new Client({
        webSocketFactory: () => socket,
        reconnectDelay: 5000,
        heartbeatIncoming: 4000,
        heartbeatOutgoing: 4000
    })

    stompClient.onConnect = () => {
        stompClient.subscribe(`/topic/rooms/${currentRoomId}`, (message) => {
            const msg = JSON.parse(message.body)
            displayMessage(msg)
        })
    }

    stompClient.activate()
}

function displayMessage(message) {
    const messagesDiv = document.getElementById('messages')
    const messageElement = document.createElement('div')
    messageElement.className = 'bg-gray-50 p-3 rounded'
    messageElement.innerHTML = `
    <div class="font-medium text-blue-600">${message.sender}</div>
    <div class="text-gray-800">${message.content}</div>
    <div class="text-sm text-gray-500">${new Date(message.timestamp).toLocaleTimeString()}</div>
  `
    messagesDiv.appendChild(messageElement)
    messagesDiv.scrollTop = messagesDiv.scrollHeight
}

document.getElementById('roomIdValue').addEventListener('click', () => {
    navigator.clipboard.writeText(currentRoomId).then(r => console.log('Copied!'))
})