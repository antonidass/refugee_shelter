import jwtDecode from "jwt-decode";
import MainList from "../components/MainList";
import { useGetRoomByOwnerIdQuery } from "../redux/api/roomApi";
import { Link } from "react-router-dom";
import { MdSubject } from "react-icons/md";
import { useAppSelector } from "../redux/store";

const MyRooms: React.FC<{}> = () => {
  const userInfo: any = jwtDecode(localStorage.getItem("access_token") || "");

  const { data = [], isLoading } = useGetRoomByOwnerIdQuery({
    id: userInfo.userId,
    token: localStorage.getItem("access_token") || "",
  });

  const currentUserRooms = useAppSelector((state) => state.roomState);
  console.log("current user rooms = ", currentUserRooms);

  return (
    <div className={`w-full h-full relative`}>
      <MainList data={currentUserRooms} />

      {/* Кнопка Добавить комнату  */}
      <Link
        to={"add"}
        className={`fixed inset-x-0 bottom-0 mx-auto mb-10 bg-primary rounded-xl w-60 flex items-center justify-center space-x-8 py-2 px-6 cursor-pointer`}
        onClick={() => {}}
      >
        <h1 className="text-2xl text-[#FEFFFF] ">Add Room</h1>
      </Link>
    </div>
  );
};

export default MyRooms;
