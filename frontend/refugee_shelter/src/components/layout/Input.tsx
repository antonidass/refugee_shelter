interface InputProps {
  placeholder: string;
  value: string;
  color: string;
  size: string;
  type: string;
  onChangeHandler: (arg0: string) => void;
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
      className={`input input-${color} ${size}`}
      placeholder={placeholder}
      value={value}
      onChange={() => onChangeHandler(value)}
    />
  );
};

export default Input;
