import { useEffect, useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { toast } from "react-toastify";
import { useLoginUserMutation } from "../redux/api/authApi";
import Button from "../components/layout/Button";
import Input from "../components/layout/Input";

const LoginPage = () => {
  const navigate = useNavigate();
  const location = useLocation();

  // Api permutation
  const [loginUser, { isLoading, isError, error, isSuccess }] =
    useLoginUserMutation();

  // From location
  const from = ((location.state as any)?.from.pathname as string) || "/";

  // Обрабатываем ответ сервера
  useEffect(() => {
    if (isSuccess) {
      toast.success("You successfully logged in!", {
        position: "top-center",
      });
      navigate(from);
    }
    if (isError) {
      if (Array.isArray((error as any).data.error)) {
        (error as any).data.error.forEach((el: any) =>
          toast.error(el.message, {
            position: "top-center",
          })
        );
      } else {
        console.log(error);
        toast.error((error as any).data.message, {
          position: "top-center",
        });
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [isLoading]);

  // Обрабатываем Login
  const onSubmitHandler = () => {
    loginUser({ username: username, password: password });
  };

  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const onChangeLogin = (event: React.ChangeEvent<HTMLInputElement>) => {
    setUsername(event.target.value);
  };

  const onChangePassword = (event: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(event.target.value);
  };

  return (
    <div className="bg-gradient-to-b from-primary/5 to-neutral/5  shadow-2xl flex flex-col my-auto items-center mx-auto rounded-xl ">
      <h1 className="text-2xl text-[#FEFFFF] mt-8 font-bold">Login</h1>
      <div className="flex flex-col space-y-8 mt-12 max-w-xl px-20 pb-12">
        <div className="flex items-center justify-between space-x-10">
          <h3 className="text-xl text-[#FEFFFF]">Username</h3>
          <Input
            placeholder="Your username..."
            value={username}
            color=""
            size="w-lg"
            type="text"
            onChangeHandler={onChangeLogin}
          />
        </div>
        <div className="flex items-center justify-between space-x-10">
          <h3 className="text-xl text-[#FEFFFF]">Password</h3>
          <Input
            placeholder="Your password..."
            value={password}
            color=""
            size="w-lg"
            type="password"
            onChangeHandler={onChangePassword}
          />
        </div>

        <div className="pt-8">
          <Button
            onClick={onSubmitHandler}
            border="none"
            width="full"
            height="fit"
            color="secondary"
            textColor="textColor"
          >
            Login
          </Button>
        </div>

        <Link
          to={"/register"}
          className="text-secondary underline underline-offset-4 mx-auto"
        >
          Not a user? Sign Up
        </Link>
      </div>
    </div>
  );
};

export default LoginPage;
