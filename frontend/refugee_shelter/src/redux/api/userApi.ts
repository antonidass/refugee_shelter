import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { url } from "inspector";
import { setUser } from "../features/userSlice";
import { IUser } from "./types";

// const BASE_URL = process.env.REACT_APP_SERVER_ENDPOINT as string;

export const userApi = createApi({
  reducerPath: "userApi",
  baseQuery: fetchBaseQuery({
    baseUrl: "http://localhost:8081/api/v1/rooms/",
  }),
  tagTypes: ["User"],
  endpoints: (builder) => ({
    getMe: builder.query<IUser, null>({
      query() {
        return {
          url: "1",
          credentials: "include",
        };
      },
      transformResponse: (result: IUser) => {
        console.log(result);
        return result;
        // return result.data.user;
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          console.log(data);
          dispatch(setUser(data));
        } catch (error) {
          console.log("HERE");
        }
      },
    }),
  }),
});
