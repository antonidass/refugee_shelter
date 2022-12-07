import { useAppSelector } from "../redux/store";
import { userApi } from "../redux/api/userApi";

const ProfilePage = () => {
  const logged_in = true;

  const { data: user } = userApi.endpoints.getMe.useQuery(null, {
    skip: !logged_in,
  });

  //   const user2 = useAppSelector((state) => state.userState.user);
  //   console.log(user);
  return (
    <div>
      <strong>Id:</strong> {user?.description}
    </div>
  );
};

export default ProfilePage;
