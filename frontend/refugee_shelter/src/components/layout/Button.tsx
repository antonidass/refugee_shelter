interface ButtonProps {
  border: string;
  onClick: () => void;
  width: string;
  height: string;
  children?: React.ReactNode;
  color: string;
  textColor: string;
}

const Button: React.FC<ButtonProps> = ({
  border,
  onClick,
  width,
  height,
  children,
  color,
  textColor,
}) => {
  return (
    <button
      className={`btn btn-${color} w-${width} h-${height} border-${border}`}
      onClick={onClick}
    >
      {children}
    </button>
  );
};

export default Button;
