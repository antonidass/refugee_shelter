import { BiLogIn } from "react-icons/bi";

const Navbar: React.FC<{}> = (type) => {
  return (
    <div className="flex justify-between items-center bg-neutral px-8 py-8 shadow-xl">
      <div className="flex items-center space-x-8">
        <h1 className="text-[#FEFFFF] text-left w-32 font-bold text-3xl">
          Refugee Shelter
        </h1>
        <img src="assets/logo.svg" className="w-20" alt="" />
      </div>
      <BiLogIn size={60} className="text-[#FEFFFF]" />
    </div>
  );
};

export default Navbar;
