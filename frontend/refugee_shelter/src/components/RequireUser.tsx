import { useCookies } from "react-cookie";
import { Navigate, Outlet, useLocation, useNavigate } from "react-router-dom";

const RequireUser = ({ allowedRoles }: { allowedRoles: string[] }) => {
  const userRole: string | null = localStorage.getItem("roles");
  console.log("user have role: ", userRole);
  const location = useLocation();

  if (userRole === null) {
    return <Navigate to={"/login"} state={{ from: location }} replace />;
  }

  if (userRole.includes(allowedRoles[0])) {
    return <Outlet />;
  }

  return allowedRoles.includes(userRole) ? <Outlet /> : <h1>Not allowed!</h1>;
};

export default RequireUser;
