import { useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
// import { LoadingButton as _LoadingButton } from '@mui/lab';
import { toast } from "react-toastify";
import { useLoginUserMutation } from "../redux/api/authApi";
import Button from "../components/layout/Button";
import Input from "../components/layout/Input";

const LoginPage = () => {
  // const methods = useForm<LoginInput>({
  // resolver: zodResolver(loginSchema),
  // });

  // 👇 API Login Mutation
  const [loginUser, { isLoading, isError, error, isSuccess }] =
    useLoginUserMutation();

  const onSubmitHandler: any = () => {
    // 👇 Executing the loginUser Mutation
    loginUser({ username: "user10", password: "user10" });
  };

  return (
    // <button className="" onClick={() => onSubmitHandler({})}>
    //   push me
    // </button>

    <div className="bg-gradient-to-b from-secondary to-neutral shadow-xl flex flex-col my-auto items-center mx-auto rounded-xl">
      <h1 className="text-2xl text-[#FEFFFF] mt-8 font-bold">Login</h1>
      <div className="flex flex-col space-y-8 mt-20">
        <div className="flex items-center justify-between space-x-10 mx-20">
          <h3 className="text-xl text-[#FEFFFF]">Username</h3>
          <Input
            placeholder="Your username..."
            value=""
            color=""
            size="w-lg"
            type="text"
            onChangeHandler={() => {}}
          />
        </div>
        <div className="flex items-center justify-between space-x-10 mx-20">
          <h3 className="text-xl text-[#FEFFFF]">Password</h3>
          <Input
            placeholder="Your password..."
            value=""
            color=""
            size="w-lg"
            type="text"
            onChangeHandler={() => {}}
          />
        </div>
      </div>
      <div className="mt-20 my-8">
        <Button
          onClick={() => {}}
          border="none"
          width="xl"
          height="fit"
          color="secondary"
          textColor="textColor"
        >
          Login
        </Button>
      </div>
    </div>
  );
};

export default LoginPage;
