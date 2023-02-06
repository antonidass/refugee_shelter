import { BiLogIn } from "react-icons/bi";
import { BiLogOut } from "react-icons/bi";
import { useAppDispatch } from "../redux/store";
import { Link, useNavigate } from "react-router-dom";
import { logout } from "../redux/features/authSlice";
import { CgProfile } from "react-icons/cg";

const Navbar: React.FC<{}> = (type) => {
  const isLoggedIn = localStorage.getItem("isLoggedIn") || "0";
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const onLogoutHandler = () => {
    localStorage.removeItem("access_token");
    localStorage.removeItem("refresh_token");
    localStorage.removeItem("roles");
    dispatch(logout());
    navigate("/");
  };

  return (
    <div className="flex justify-between items-center bg-neutral px-8 py-8 shadow-xl z-50">
      <Link to={"/"} className="flex items-center space-x-8">
        <h1 className="text-[#FEFFFF] text-left w-32 font-bold text-3xl">
          Refugee Shelter
        </h1>
        <img src="assets/logo.svg" className="w-20" alt="" />
      </Link>

      {isLoggedIn === "1" ? (
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
