import axios from "axios";
import { FunctionComponent, useEffect, useState } from "react";

interface StatsPageProps {}

interface stats {
    daily: number,
    weekly: number,
    monthly: number,
    done: number,
    n: number,
}

const StatsPage: FunctionComponent<StatsPageProps> = () => {
    const [stats, setStats] = useState<stats | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      const token = localStorage.getItem("Authorization");

      const config = {
        headers: {
          Authorization: `${token}`,
        },
      };
      const response = await axios.get(
        "http://localhost:8080/api/stats/",
        config
      );
      console.log(response.data);
      setStats(response.data);
    };

    // fetchData();
  }, []); // Load data from localStorage on initial render
  return (
    <div className="flex flex-row flex-wrap pt-[100px] justify-center items-center gap-10">
        <div className="bg-amber-400 w-[200px] h-[200px] mx-[300px] shadow-lg rounded px-8 pt-6 pb-8 mb-4 flex flex-col justify-evenly items-center">
            <p className="text-2xl font-semibold text-center">Finished patterns</p>
            <p className="text-3xl font-semibold">{stats?.done} / {stats?.n}</p>
        </div>
        <div className="bg-amber-400 w-[200px] h-[200px] shadow-lg rounded px-8 pt-6 pb-8 mb-4 flex flex-col justify-evenly items-center">
            <p className="text-2xl font-semibold text-center">Crochet minutes this month</p>
            <p className="text-3xl font-semibold">{stats?.monthly} mins</p>
        </div>
        <div className="bg-amber-400 w-[200px] h-[200px] shadow-lg rounded px-8 pt-6 pb-8 mb-4 flex flex-col justify-evenly items-center">
            <p className="text-2xl font-semibold text-center">Crochet minutes this week</p>
            <p className="text-3xl font-semibold">{stats?.weekly} mins</p>
        </div>
        <div className="bg-amber-400 w-[200px] h-[200px]  shadow-lg rounded px-8 pt-6 pb-8 mb-4 flex flex-col justify-evenly items-center">
            <p className="text-2xl font-semibold text-center">Crochet minutes today</p>
            <p className="text-3xl font-semibold">{stats?.daily} mins</p>
        </div>
    </div>
  );
};

export default StatsPage;
