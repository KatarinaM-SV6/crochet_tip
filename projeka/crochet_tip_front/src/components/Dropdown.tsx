import React, { useState } from 'react';
import CustomButton from './CustomButton';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const DropdownSelect = () => {
  const [selectedItem, setSelectedItem] = useState('');
  const navigate = useNavigate();
  const handleChange = (event: any) => {
    setSelectedItem(event.target.value);
  };
  
  const handleRec = async () => {
    try {
      const token = localStorage.getItem("Authorization");

      const config = {
        headers: {
          Authorization: `${token}`,
        },
        params: {
            difficulty: selectedItem,
        },
      };
      const response = await axios.get("http://localhost:8080/api/recommendation/", config);
      localStorage.setItem("rec", JSON.stringify(response.data));
      navigate("/user/rec")
      console.log(response.data);
    } catch (error) {
      console.error("Login error:", error);
      // Handle login error here
    }
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100 gap-10">
      <label htmlFor="difficulty" className='text-black text-2xl'>Select difficulty:</label>
      <select id="difficulty" value={selectedItem} onChange={handleChange} className='shadow-md rounded px-8 h-[40px] w-[50%] text-black text-2xl'>
        <option value="">Select...</option>
        <option value="BEGGINER">BEGGINER</option>
        <option value="INTERMEDIATE">INTERMEDIATE</option>
        <option value="ADVANCED">ADVANCED</option>
      </select>
      <CustomButton title='Continue' onClick={handleRec}></CustomButton>
    </div>
  );
};

export default DropdownSelect;