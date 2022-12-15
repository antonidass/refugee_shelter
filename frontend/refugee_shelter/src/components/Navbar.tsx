import { BiLogIn } from "react-icons/bi";
import { BiLogOut } from "react-icons/bi";
import { useAppSelector, useAppDispatch } from "../redux/store";
import { Link } from "react-router-dom";
import { logout } from "../redux/features/authSlice";
import { CgProfile } from "react-icons/cg";

const Navbar: React.FC<{}> = (type) => {
  const isLoggedIn = useAppSelector((state) => state.authState.isLoggedIn);
  const dispatch = useAppDispatch();

  const onLogoutHandler = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    dispatch(logout());
  };

  return (
    <div className="flex justify-between items-center bg-neutral px-8 py-8 shadow-xl">
      <Link to={"/"} className="flex items-center space-x-8">
        <h1 className="text-[#FEFFFF] text-left w-32 font-bold text-3xl">
          Refugee Shelter
        </h1>
        <img src="assets/logo.svg" className="w-20" alt="" />
      </Link>

      {isLoggedIn ? (
        <div className="flex items-center space-x-4">
          <Link to={"/profile"}>
            <CgProfile size={44} className="text-[#FEFFFF]" />
          </Link>
          <Link to={"/"} onClick={onLogoutHandler}>
            <BiLogOut size={50} className="text-[#FEFFFF]" />
          </Link>
        </div>
      ) : (
        <Link to={"/login"}>
          <BiLogIn size={50} className="text-[#FEFFFF]" />
        </Link>
      )}
    </div>
  );
};

export default Navbar;
