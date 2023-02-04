import { MdClose } from "react-icons/md";

interface FilterBadgeProps {
  text: string;
  isHidden: boolean;
  onCloseBadge: () => void;
}

const FilterBadge: React.FC<FilterBadgeProps> = ({
  text,
  isHidden,
  onCloseBadge,
}) => {
  return (
    <div
      className={`${
        isHidden ? "hidden" : ""
      } bg-secondary rounded-3xl flex items-center w-36 px-4 justify-between`}
    >
      <p className="text-[#FEFFFF] text-xl">{text}</p>
      <MdClose
        size={20}
        color="white"
        onClick={onCloseBadge}
        className="cursor-pointer"
      />
    </div>
  );
};

export default FilterBadge;
