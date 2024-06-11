import { FunctionComponent, useEffect, useState } from "react";
import CustomButton from "./CustomButton";
import axios from "axios";
import { useNavigate, useNavigation } from "react-router-dom";
import Stopwatch from "./Stopwatch";

interface HomescreenProps {}

interface Pattern {
    name: string;
    patternImage: string;
  }

const Recommendation: FunctionComponent<HomescreenProps> = () => {
  const [user, setUser] = useState(null);
  const [rec, setRec] = useState(null);
  const [accepted, setAccepted] = useState(false);
  const [pattern, setPattern] = useState<Pattern | null>(null);

  useEffect(() => {
    const userFromStorage = JSON.parse(localStorage.getItem("user")!);
    const recFromStorage = JSON.parse(localStorage.getItem("rec")!);
    setUser(userFromStorage);
    setRec(recFromStorage);
    setAccepted(recFromStorage.accepted);
    setPattern(recFromStorage.pattern);
  }, []); // Load data from localStorage on initial render

  const handleAccept = async () => {
    try {
      const token = localStorage.getItem("Authorization");

      const config = {
        headers: {
          Authorization: `${token}`,
        },
      };

      const response = await axios.put(
        "http://localhost:8080/api/recommendation/accept",
        { pattern, accepted },
        config
      );
      localStorage.setItem("rec",JSON.stringify({pattern, accepted:true}));
      setAccepted(true);
    } catch (error) {
      console.error("Login error:", error);
      // Handle login error here
    }
  };

  const handleReject = async () => {
    try {
      const token = localStorage.getItem("Authorization");

      const config = {
        headers: {
          Authorization: `${token}`,
        },
      };

      const response = await axios.put(
        "http://localhost:8080/api/recommendation/reject",
        { pattern, accepted },
        config
      );
      setAccepted(false);
      localStorage.setItem("rec", JSON.stringify(response.data));
      setPattern(response.data.pattern);
    } catch (error) {
      console.error("Login error:", error);
      // Handle login error here
    }
  };

  return (
    <div className="flex h-100% pt-[60px] flex-col items-center justify-evenly min-h-screen bg-gray-100">
      {pattern && (
        <>
          <p className="font-semibold text-2xl">{pattern.name}</p>
          <iframe
            title="prev"
            src={`/${pattern.patternImage}`}
            height="500px"
            width="80%"
          />
        </>
      )}
      {!accepted ? (
        <div className="flex w-[80%] justify-center gap-10">
          <CustomButton title="Accept" onClick={handleAccept} />
          <CustomButton title="Reject" onClick={handleReject} />
        </div>
      ) : <Stopwatch/>}
    </div>
  );
};

export default Recommendation;
