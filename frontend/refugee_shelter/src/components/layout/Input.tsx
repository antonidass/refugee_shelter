import React from "react";

interface InputProps {
  placeholder: string;
  value: string | number;
  color: string;
  size: string;
  type: string;
  onChangeHandler: (arg0: React.ChangeEvent<HTMLInputElement>) => void;
}

const Input: React.FC<InputProps> = ({
  placeholder,
  value,
  color,
  size,
  type,
  onChangeHandler,
}) => {
  return (
    <input
      type={type}
      className={`${size} rounded-md px-2 py-2 text-textColor placeholder:font-thin text-md placeholder-borderColor focus:outline-4 outline-textColor  border-textColor border bg-opacity-0 bg-yellow-50`}
      placeholder={placeholder}
      value={value}
      onChange={onChangeHandler}
    />
  );
};

export default Input;
