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
      className={`bg-primary text-textColor w-${width} border-${border} rounded-md text-xl py-2 hover:opacity-100 duration-200 opacity-90`}
      onClick={onClick}
    >
      {children}
    </button>
  );
};

export default Button;
