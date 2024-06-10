import { FunctionComponent } from "react";
import { Link } from "react-router-dom";

interface CustomButtonProps {
  title: string;
  disabled?: boolean;
  onClick?: () => void; 
  to?: string;
}

const CustomButton: FunctionComponent<CustomButtonProps> = ({
  title,
  disabled = false,
  onClick,
  to,
}) => {
    console.log(disabled);
  return (
    <button 
    onClick={onClick}
    disabled={disabled}
    className={`${disabled ? 'bg-gray-400' : 'bg-amber-700'} ${disabled ? '': 'hover:bg-amber-500'} shadow-md rounded px-8 h-[60px] w-[50%] text-white text-2xl`}>
      
      { to ? <Link to={to}>{title}</Link> : <p className="m-auto text-center">{title}</p>}
      
    </button>
  );
};

export default CustomButton;
