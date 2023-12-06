import React from "react";
import "./Message.css";
import userimg1 from "../../assets/images/userimg.jpg";
import userimg2 from "../../assets/images/userimg2.jpg";

function Message() {
  return (
    <div className="">
      <div className="sender">
        <img src={userimg1} alt="user" className="userimg" />
        <div class="sender-message">
          <p class="message-content">
            I agree that your message is awesome! I agree that your message is
            awesome! I agree that your message is awesome! I agree that your
            message is awesome!
          </p>
          <div class="message-timestamp-sender"> 13:37</div>
        </div>
      </div>
      <div className="reciever">
        <img src={userimg2} alt="user" className="userimg" />
        <div class="reciever-message">
          <p class="message-content">I agree that your message is awesome!</p>
          <div class="message-timestamp-reciever"> 13:37</div>
        </div>
      </div>
      <div className="sender">
        <img src={userimg1} alt="user" className="userimg" />
        <div class="sender-message">
          <p class="message-content">I agree that your message is awesome!</p>
          <div class="message-timestamp-sender"> 13:37</div>
        </div>
      </div>
      <div className="reciever">
        <img src={userimg2} alt="user" className="userimg" />
        <div class="reciever-message">
          <p class="message-content">I agree that your message is awesome!</p>
          <div class="message-timestamp-reciever"> 13:37</div>
        </div>
      </div>
      <div className="sender">
        <img src={userimg1} alt="user" className="userimg" />
        <div class="sender-message">
          <p class="message-content">I agree that your message is awesome!</p>
          <div class="message-timestamp-sender"> 13:37</div>
        </div>
      </div>
      <div className="reciever">
        <img src={userimg2} alt="user" className="userimg" />
        <div class="reciever-message">
          <p class="message-content">I agree that your message is awesome!</p>
          <div class="message-timestamp-reciever"> 13:37</div>
        </div>
      </div>
    </div>
  );
}

export default Message;
