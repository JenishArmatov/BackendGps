import React, { useState } from "react";
import "./Sidebar.scss";
import profile from '../../images/profile.png'

const Sidebar = () => {
  const [isOpen, setIsOpen] = useState(true);

  return (
    <div className="sidebar">
      <div className="sidebar-header">
        <h2>FB Compservice</h2>
      </div>
      <hr />

      <div className="profile-section">
        <img src={profile} alt="profile-img" className="profile-img" />
        <p>Руслан</p>
      </div>
      <hr />

      <ul className="menu">
        <li><span className="icon">🔔</span> <span>Водители</span></li>
        <li><span className="icon">📋</span> <span>Мои заказы</span></li>
      </ul>
    </div>
  );
};

export default Sidebar;