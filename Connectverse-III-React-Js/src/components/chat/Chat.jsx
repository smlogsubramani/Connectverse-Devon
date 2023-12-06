import React, { useState } from "react";
import "./Chat.css";
import Message from "./Message";

import { AiOutlineArrowUp } from "react-icons/ai";
function Chat() {
  const [currentMessage, setCurrentMessage] = useState("");

  return (
    <div class="container">
      <header>
        <h1>Hotel Management</h1>
      </header>
      <Message />

      <div className="chat-footer">
        <input
          type="text"
          value={currentMessage}
          placeholder="Hey..."
          className="chat-input"
          onChange={(event) => {
            setCurrentMessage(event.target.value);
          }}
        />
        <button className="send-btn">
          <AiOutlineArrowUp />
        </button>
      </div>
    </div>
  );
}

export default Chat;
