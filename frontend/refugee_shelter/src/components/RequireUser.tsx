import { useCookies } from "react-cookie";
import { Navigate, Outlet, useLocation } from "react-router-dom";
// import { userApi } from "../redux/api/userApi";
// import FullScreenLoader from './FullScreenLoader';

const RequireUser = ({ allowedRoles }: { allowedRoles: string[] }) => {
  //   const [cookies] = useCookies(["logged_in"]);
  //   const logged_in = cookies.logged_in;

  //   const { data: user } = userApi.endpoints.getMe.useQuery(null, {
  //     skip: !logged_in,
  //   });

  //   const location = useLocation();

  //   if (logged_in && !user) {
  //     return <h1>Loading...</h1>;
  //   }
  const userRole: string | null = localStorage.getItem("roles");
  console.log("user have role: ", userRole);

  if (userRole === null) {
    return <h1>Not logged in!</h1>;
  }

  if (userRole.includes(allowedRoles[0])) {
    return <Outlet />;
  }

  return allowedRoles.includes(userRole) ? <Outlet /> : <h1>Not allowed!</h1>;
};

export default RequireUser;
