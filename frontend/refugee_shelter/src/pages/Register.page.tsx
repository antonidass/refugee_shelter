import { useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useRegisterUserMutation } from "../redux/api/authApi";

const RegisterPage = () => {
  // 👇 Calling the Register Mutation
  const [registerUser, { isLoading, isSuccess, error, isError }] =
    useRegisterUserMutation();

  const onSubmitHandler: any = () => {
    // 👇 Executing the RegisterUser Mutation
    registerUser({
      username: "user20",
      password: "user20",
      name: "kekes",
      phone: "",
      email: "",
    });
  };
  return (
    <div>
      <button onClick={onSubmitHandler}>Register</button>
    </div>
  );
};

export default RegisterPage;
