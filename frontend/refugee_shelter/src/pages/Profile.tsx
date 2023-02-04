import { useEffect } from "react";
import jwt_decode from "jwt-decode";
import { useGetUserInfoMutation } from "../redux/api/authApi";
import Button from "../components/layout/Button";
import { Link } from "react-router-dom";
import { useAppSelector } from "../redux/store";

interface UserInfo {
  sub: string;
  roles: string[];
  iss: string;
  exp: number;
  userId: number;
}

const Profile: React.FC<{}> = () => {
  const userInfo: UserInfo = jwt_decode(
    localStorage.getItem("access_token") || ""
  );
  const currentUser = useAppSelector((state) => state.authState);
  const token = localStorage.getItem("access_token") || "";
  const [getUserInfo] = useGetUserInfoMutation();

  useEffect(() => {
    getUserInfo({
      id: userInfo.userId,
      token,
    });
  }, []);

  return (
    <div className="flex flex-col justify-center items-center p-12">
      <h1 className="text-white text-4xl font-bold">Profile</h1>

      {/* Главная информация  */}
      <div className="flex flex-col justify-start space-y-2 w-80 mt-12">
        <h1 className="text-2xl text-white">Name: {currentUser.name}</h1>
        <h1 className="text-2xl text-white">Email: {currentUser.email}</h1>
      </div>

      {/* Изменить профиль */}
      <Link to={"change"} className="pt-8">
        <Button
          onClick={() => {}}
          border="none"
          width="80"
          height="fit"
          color="secondary"
          textColor="textColor"
        >
          Change Profile
        </Button>
      </Link>

      {/* Список комнат */}
      <Link to={"/profile/rooms"} className="pt-8">
        <Button
          onClick={() => {}}
          border="none"
          width="80"
          height="fit"
          color="secondary"
          textColor="textColor"
        >
          Show My Rooms
        </Button>
      </Link>
      {/* Список броней */}
      <div className="pt-8">
        <Button
          onClick={() => {}}
          border="none"
          width="80"
          height="fit"
          color="secondary"
          textColor="textColor"
        >
          Show My Reservations
        </Button>
      </div>
    </div>
  );
};

export default Profile;
