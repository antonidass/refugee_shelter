import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
// import { LoginInput } from "../../pages/Login.page";
// import { RegisterInput } from "../../pages/Register.page";
import jwt_decode from "jwt-decode";
// const BASE_URL = process.env.REACT_APP_SERVER_ENDPOINT as string;
import { login, setUser } from "../features/authSlice";
import { IChangeUser, IUser } from "./types";

export const authApi = createApi({
  reducerPath: "authApi",
  baseQuery: fetchBaseQuery({
    baseUrl: `http://localhost:8081/api/v1/`,
  }),
  endpoints: (builder) => ({
    registerUser: builder.mutation<
      {},
      {
        name: string;
        password: string;
        email: string;
        username: string;
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
    updateUser: builder.mutation<
      {},
      {
        name: string;
        email: string;
        id: number;
        token: string;
      }
    >({
      query({ name, email, id, token }) {
        return {
          url: `users/${id}`,
          method: "PATCH",
          body: { name, email },
          headers: {
            Authorization: "Bearer " + token,
          },
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data }: any = await queryFulfilled;
          console.log("data after change user = ", data);
          dispatch(setUser(data));
        } catch (error) {
          console.log("Error while update user...");
        }
      },
    }),
    getUserInfo: builder.mutation<{}, { id: number; token: string }>({
      query: ({ id, token }) => ({
        url: `users/${id}`,
        headers: {
          Authorization: "Bearer " + token,
        },
      }),
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data }: any = await queryFulfilled;
          console.log("data after get user = ", data);
          dispatch(setUser(data));
        } catch (error) {
          console.log("Error while getting user data...");
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
          localStorage.setItem("access_token", data.access_token);
          localStorage.setItem("refresh_token", data.refresh_token);
          localStorage.setItem("roles", "ROLE_USER");
          dispatch(login());
          // const decodedJWT: any = jwt_decode(data.access_token);
          // localStorage.setItem("roles", decodedJWT["roles"]);
        } catch (error) {}
      },
    }),
  }),
});

export const { useLoginUserMutation } = authApi;
export const {
  useRegisterUserMutation,
  useGetUserInfoMutation,
  useUpdateUserMutation,
} = authApi;
