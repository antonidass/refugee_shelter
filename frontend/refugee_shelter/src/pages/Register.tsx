import { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useRegisterUserMutation } from "../redux/api/authApi";
import Button from "../components/layout/Button";
import Input from "../components/layout/Input";
import handleServerResponse from "../utils/Utils";

const Register: React.FC<{}> = () => {
  const [login, setLogin] = useState("");
  const [password, setPassword] = useState("");
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");

  const onChangeLogin = (event: React.ChangeEvent<HTMLInputElement>) => {
    setLogin(event.target.value);
  };

  const onChangePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  const onChangeName = (event: React.ChangeEvent<HTMLInputElement>) => {
    setName(event.target.value);
  };

  const onChangeEmail = (event: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(event.target.value);
  };

  // Api permutation
  const [registerUser, result] = useRegisterUserMutation();

  const onSubmitHandler = () => {
    // Check if field is empty Login and Password

    if (login === "" || password === "") {
      toast.error("Please fill Login and Password!", {
        position: "top-center",
      });
      return;
    }

    const regex =
      /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
    if (email !== "" && regex.test(email) === false) {
      toast.error("Incorrect Email!", {
        position: "top-center",
      });
      return;
    }

    registerUser({
      name: name,
      username: login,
      password: password,
      email: email,
    });
  };
  const navigate = useNavigate();

  // Обрабатываем ответ сервера
  useEffect(() => {
    if (
      handleServerResponse(
        result,
        "You successfully registered! Now please log in..."
      ) === 0
    ) {
      navigate("/");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [result.isLoading]);

  return (
    <div className="bg-gradient-to-b from-primary/10 to-neutral/5  shadow-2xl my-8 flex flex-col items-center mx-auto rounded-xl ">
      <h1 className="text-2xl text-[#FEFFFF] mt-8 font-bold">Register</h1>
      <div className="flex flex-col space-y-8 mt-8 max-w-xl px-20 pb-6">
        <div className="flex items-center justify-between space-x-10">
          <h3 className="text-xl text-[#FEFFFF]">Username *</h3>
          <Input
            placeholder="Your username..."
            value={login}
            color=""
            size="w-lg"
            type="text"
            onChangeHandler={onChangeLogin}
          />
        </div>
        <div className="flex items-center justify-between space-x-10">
          <h3 className="text-xl text-[#FEFFFF]">Password *</h3>
          <Input
            placeholder="Your password..."
            value={password}
            color=""
            size="w-lg"
            type="password"
            onChangeHandler={onChangePassword}
          />
        </div>
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
            Register
          </Button>
        </div>

        <Link
          to={"/login"}
          className="text-secondary underline underline-offset-4 mx-auto"
        >
          Already a user? Sign In
        </Link>
      </div>
    </div>
  );
};

export default Register;
