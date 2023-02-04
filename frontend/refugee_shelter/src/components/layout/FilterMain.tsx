import { FaFilter } from "react-icons/fa";

interface FilterMainProps {
  onClickHandle: () => void;
}

const FilterMain: React.FC<FilterMainProps> = ({ onClickHandle }) => {
  return (
    <div
      onClick={onClickHandle}
      className="cursor-pointer bg-primary rounded-3xl flex items-center px-6 space-x-8 py-2"
    >
      <p className="text-[#FEFFFF] text-xl">Filters</p>
      <FaFilter size={20} color="white" />
    </div>
  );
};

export default FilterMain;
