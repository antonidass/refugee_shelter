import { useParams } from "react-router-dom";
import { useGetReservationsByOwnerIdQuery } from "../redux/api/reservationsApi";
import jwtDecode from "jwt-decode";
import ReservationsList from "../components/ReservationsList";
import { useAppSelector } from "../redux/store";
import { UserInfo } from "../redux/api/types";

const MyReservations: React.FC<{}> = () => {
  const userInfo: UserInfo = jwtDecode(
    localStorage.getItem("access_token") || ""
  );

  const { data = [], isLoading } = useGetReservationsByOwnerIdQuery({
    id: userInfo.userId,
    token: localStorage.getItem("access_token") || "",
  });

  const currentUserReservations = useAppSelector(
    (state) => state.reservationsState
  );

  return (
    <div>
      <ReservationsList data={currentUserReservations} />
    </div>
  );
};

export default MyReservations;
