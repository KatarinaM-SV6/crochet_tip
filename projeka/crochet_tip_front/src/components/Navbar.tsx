import React from "react";
import "../App.css";
import { Link } from "react-router-dom";
interface NavbarProps {}

const Navbar: React.FC<NavbarProps> = () => {
  const isAuthenticated = !!localStorage.getItem("Authorization");

  const handleLogout = () => {
    localStorage.clear();
  }

  return (
    <div className=" bg-amber-700 w-full h-[60px] fixed">
      <div className="h-full py-[10px] px-[20px] flex flex-row items-center justify-between">
        <p className="font-sans text-[24px] font-semibold tracking-wide text-white">
          CROCHETIER
        </p>

        {isAuthenticated && (
          <div>
            <Link to={"/user/stats"} className="text-[16px] text-white px-[10px]">
              Stats
            </Link>
            <Link to={"/login"} className="text-[16px] text-white px-[10px]" onClick={handleLogout}>
              Logout
            </Link>
          </div>
        )}
      </div>
    </div>
  );
};

export default Navbar;
