import { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useUpdateUserMutation } from "../redux/api/authApi";
import Button from "../components/layout/Button";
import Input from "../components/layout/Input";
import jwt_decode from "jwt-decode";
import { toast } from "react-toastify";

interface UserInfo {
  sub: string;
  roles: string[];
  iss: string;
  exp: number;
  userId: number;
}

const ChangeProfile: React.FC<{}> = () => {
  const userInfo: UserInfo = jwt_decode(
    localStorage.getItem("access_token") || ""
  );

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const onChangeName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setName(event.target.value);
  };

  const onChangeEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };
  const [updateUser, { isError, error, isSuccess }] = useUpdateUserMutation();
  const navigate = useNavigate();

  const onSubmitHandler = () => {
    const regex =
      /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    if (email !== "" && regex.test(email) === false) {
      toast.error("Incorrect Email!", {
        position: "top-center",
      });
      return;
    }

    updateUser({
      name: name,
      email: email,
      id: userInfo.userId,
      token: localStorage.getItem("access_token") || "",
    });
    navigate("/profile");
  };

  return (
    <>
      <div className="bg-gradient-to-b from-primary/10 to-neutral/5  shadow-2xl my-8 flex flex-col items-center mx-auto rounded-xl ">
        <h1 className="text-2xl text-[#FEFFFF] mt-8 font-bold">
          Change Profile
        </h1>
        <div className="flex flex-col space-y-8 mt-8 max-w-xl px-20 pb-6">
          <div className="flex items-center justify-between space-x-10">
            <h3 className="text-xl text-[#FEFFFF]">Name</h3>
            <Input
              placeholder="Your name..."
              value={name}
              color=""
              size="w-lg"
              type="text"
              onChangeHandler={onChangeName}
            />
          </div>
          <div className="flex items-center justify-between space-x-10">
            <h3 className="text-xl text-[#FEFFFF]">Email</h3>
            <Input
              placeholder="Your email..."
              value={email}
              color=""
              size="w-lg"
              type="email"
              onChangeHandler={onChangeEmail}
            />
          </div>

          <div className="pt-2">
            <Button
              onClick={onSubmitHandler}
              border="none"
              width="full"
              height="fit"
              color="secondary"
              textColor="textColor"
            >
              Change Profile
            </Button>
          </div>
        </div>
      </div>
    </>
  );
};

export default ChangeProfile;
