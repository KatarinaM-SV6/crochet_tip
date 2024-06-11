import React, { FunctionComponent, useEffect, useState } from 'react';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { toast } from 'react-toastify';

interface WebSocketComponentProps {
    
}
 
const WebSocketComponent: FunctionComponent<WebSocketComponentProps> = () => {
    const user = JSON.parse(localStorage.getItem("user")!);
    let userId = '';

    if (user) {
        userId = user.id;
    }
    
    useEffect(() => {
        // Create a new SockJS client
        const socket = new SockJS('http://localhost:8080/api/socket');
        // Create a new STOMP client
        const stompClient = new Client({
          webSocketFactory: () => socket,
          reconnectDelay: 5000,
        });
    
        // Function to handle successful connection
        const onConnect = () => {
          console.log('Connected');
          // Subscribe to the topic
          console.log('/topic/' + userId);
          stompClient.subscribe('/topic/' + userId, (message) => {
            toast.info(message.body);
            console.log('Received message: ', message.body);
          });
        };
    
        // Function to handle disconnection
        const onDisconnect = () => {
          console.log('Disconnected');
        };
    
        // Set up the STOMP client event handlers
        stompClient.onConnect = onConnect;
        stompClient.onDisconnect = onDisconnect;
        stompClient.activate();
    
        // Cleanup function
        return () => {
          if (stompClient.connected) {
            stompClient.deactivate();
          }
        };
      }, []);

    return ( <div/> );
}
 

export default WebSocketComponent;
