import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
// import { LoginInput } from "../../pages/Login.page";
// import { RegisterInput } from "../../pages/Register.page";
import { IGenericResponse } from "./types";
import { userApi } from "./userApi";
import jwt_decode from "jwt-decode";

// const BASE_URL = process.env.REACT_APP_SERVER_ENDPOINT as string;

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `http://localhost:8081/api/v1/`,
  }),
  endpoints: (builder) => ({
    registerUser: builder.mutation<
      IGenericResponse,
      {
        name: string;
        password: string;
        email: string;
        username: string;
        phone: string;
      }
    >({
      query(data) {
        return {
          url: "register",
          method: "POST",
          body: data,
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          console.log("data after register = ", data);
        } catch (error) {
          console.log("Error while register...");
        }
      },
    }),
    loginUser: builder.mutation<
      { access_token: string; refresh_token: string },
      { username: string; password: string }
    >({
      query(data) {
        return {
          url: `login?username=${data.username}&password=${data.password}`,
          method: "POST",
          headers: { "Content-Type": "application/x-www-form-urlencoded" },
          body: data,
          credentials: "include",
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          console.log("data response = ", data);
          localStorage.setItem("access_token", data.access_token);
          localStorage.setItem("refresh_token", data.refresh_token);
          const decodedJWT: any = jwt_decode(data.access_token);
          localStorage.setItem("roles", decodedJWT["roles"]);
          console.log(jwt_decode(data.access_token));
          await dispatch(userApi.endpoints.getMe.initiate(null));
        } catch (error) {
          console.log("Invalid login please try again...");
        }
      },
    }),
  }),
});

export const { useLoginUserMutation } = authApi;
export const { useRegisterUserMutation } = authApi;
