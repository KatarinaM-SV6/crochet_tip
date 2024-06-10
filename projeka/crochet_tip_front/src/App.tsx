import React from "react";
import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Navbar from "./components/Navbar";
import Login from "./components/Login";
import Layout from "./components/Layout";
import Register from "./components/Register";
import Homescreen from "./components/Homescreen";
import DropdownSelect from "./components/Dropdown";
import Recommendation from "./components/Recommendation";
import StatsPage from "./components/StatsPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={ <Layout /> } >
          <Route path="login" element= { <Login />} />
          <Route path="register" element= { <Register />} />
        </Route>
        <Route path="/user" element= { <Layout />} >
          <Route path="/user/homescreen" element= { <Homescreen />} />
          <Route path="/user/get-rec" element= { <DropdownSelect />} />
          <Route path="/user/rec" element= { <Recommendation />} />
          <Route path="/user/stats" element= { <StatsPage />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
