import { FaFilter } from "react-icons/fa";
import { IconContext } from "react-icons";

const FilterMain: React.FC<{}> = () => {
  return (
    <>
      <div className="bg-primary rounded-3xl flex items-center px-6 space-x-8 py-2">
        <p className="text-[#FEFFFF] text-xl">Filters</p>
        <FaFilter size={20} color="white" />
      </div>
    </>
  );
};

export default FilterMain;
