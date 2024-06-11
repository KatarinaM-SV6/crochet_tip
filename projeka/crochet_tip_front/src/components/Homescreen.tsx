import { FunctionComponent } from "react";
import CustomButton from "./CustomButton";
import axios from "axios";
import { useNavigate, useNavigation } from "react-router-dom";
import WebSocketComponent from "../WebSocketComponent";

interface HomescreenProps {}

const Homescreen: FunctionComponent<HomescreenProps> = () => {
  const user = JSON.parse(localStorage.getItem("user")!);
  const disable = !!!user.currPattern;
  const navigate = useNavigate();

  const handleRec = () => {
    navigate("/user/get-rec");
  };

  const handleCurr = async () => {
    try {
      const token = localStorage.getItem("Authorization");

      const config = {
        headers: {
          Authorization: `${token}`,
        },
      };
      const response = await axios.get("http://localhost:8080/api/current-project", config);
      localStorage.setItem("rec", JSON.stringify({accepted: true, pattern: response.data}));
      navigate("/user/rec");
    } catch (error) {
      console.error("Login error:", error);
      // Handle login error here
    }
  };

  console.log(disable);
  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 gap-10">
      <CustomButton title="Find new project" onClick={handleRec}/>
      <CustomButton title="Load current project" disabled={disable} onClick={handleCurr}/>
    </div>
  );
};

export default Homescreen;
