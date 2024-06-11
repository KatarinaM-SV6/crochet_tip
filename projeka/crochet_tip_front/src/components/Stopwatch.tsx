import axios from "axios";
import React, { useState, useEffect } from "react";
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { toast } from 'react-toastify';
import { useNavigate } from "react-router-dom";

const Stopwatch = () => {
  const [time, setTime] = useState(0);
  const navigate = useNavigate();
  const [isRunning, setIsRunning] = useState(false);

  const finish = async () => {
    try {
      const token = localStorage.getItem("Authorization");

      const config = {
        headers: {
          Authorization: `${token}`,
        },
      };

      const result = await axios.get("http://localhost:8080/api/finish-project",config);
      localStorage.setItem("user", JSON.stringify(result.data));
      navigate("/user/homescreen");
    } catch (error) {
      console.error(error);
    }
  };

  const handleStart = async () => {
    try {
      const token = localStorage.getItem("Authorization");

      const config = {
        headers: {
          Authorization: `${token}`,
        },
      };

      await axios.get("http://localhost:8080/api/timer/start",config);
    } catch (error) {
      console.error(error);
    }
  };

  const handleStop = async () => {
    try {
      const token = localStorage.getItem("Authorization");

      const config = {
        headers: {
          Authorization: `${token}`,
        },
      };

      await axios.get("http://localhost:8080/api/timer/stop",config);
    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    let intervalId: any;
    if (isRunning) {
      // setting time from 0 to 1 every 10 milisecond using javascript setInterval method
      intervalId = setInterval(() => setTime(time + 1), 10);
    }
    return () => clearInterval(intervalId);
  }, [isRunning, time]);
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
          if (message.body === "Now you have to take a break!"){
            startAndStop();
          }
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
  const hours = Math.floor(time / 360000);
  const minutes = Math.floor((time % 360000) / 6000);
  const seconds = Math.floor((time % 6000) / 100);
  const milliseconds = time % 100;

  // Method to start and stop timer
  const startAndStop = () => {
    if (isRunning) {
        handleStop();
    } else {
        handleStart();
    }
    setIsRunning(!isRunning);
    
  };

  // Method to reset timer back to 0
  const reset = () => {
    setTime(0);
  };
  return (
    <div className="stopwatch-container">
      <p className="stopwatch-time">
        {hours}:{minutes.toString().padStart(2, "0")}:
        {seconds.toString().padStart(2, "0")}:
        {milliseconds.toString().padStart(2, "0")}
      </p>
      <div className="stopwatch-buttons gap-10 mb-[10px]">
        <button className="bg-amber-700 hover:bg-amber-500 shadow-md rounded px-8 h-[60px] w-[50%] text-white text-2xl" onClick={startAndStop}>
          {isRunning ? "Stop" : "Start"}
        </button>
        <button className="bg-amber-900 hover:bg-amber-500 shadow-md rounded px-8 h-[60px] w-[50%] text-white text-2xl" onClick={finish}>
            Finish
        </button>
      </div>
    </div>
  );
};

export default Stopwatch;
