import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from './Navbar';
import WebSocketComponent from '../WebSocketComponent';

const Layout = () => {
  return (
    <div>
      <Navbar />
      <div className="content">
        <Outlet />
      </div>
    </div>
  );
};

export default Layout;
