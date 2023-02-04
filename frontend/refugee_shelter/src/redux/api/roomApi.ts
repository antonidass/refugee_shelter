import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { pushRoom, setRooms, updateRoom } from "../features/roomSlice";
import { IRoom, IRoomRequest } from "./types";

export const roomsApi = createApi({
  reducerPath: "roomsApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8081/api/v1/" }),
  endpoints: (build) => ({
    getRooms: build.query({
      query: () => "rooms",
    }),
    getRoomById: build.query({
      query: (id) => `rooms/${id}`,
    }),
    getRoomByOwnerId: build.query({
      query: ({ id, token }) => {
        return {
          url: `users/${id}/rooms`,
          headers: {
            Authorization: "Bearer " + token,
          },
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          // localStorage.setItem("access_token", data.access_token);
          // localStorage.setItem("refresh_token", data.refresh_token);
          // localStorage.setItem("roles", "ROLE_USER");
          console.log("data after getRoomByOwnerId = ", data);
          dispatch(setRooms(data));
          // const decodedJWT: any = jwt_decode(data.access_token);
          // localStorage.setItem("roles", decodedJWT["roles"]);
        } catch (error) {}
      },
    }),
    addRoom: build.mutation<
      { data: any },
      { access_token: string; roomData: IRoomRequest }
    >({
      query({ access_token, roomData }) {
        return {
          url: `rooms`,
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + access_token,
          },
          body: roomData,
          credentials: "include",
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data }: any | void = await queryFulfilled;
          // localStorage.setItem("access_token", data.access_token);
          // localStorage.setItem("refresh_token", data.refresh_token);
          // localStorage.setItem("roles", "ROLE_USER");
          console.log("data after save room = ", data);

          dispatch(pushRoom(data));
          // const decodedJWT: any = jwt_decode(data.access_token);
          // localStorage.setItem("roles", decodedJWT["roles"]);
        } catch (error) {}
      },
    }),
    delRoom: build.mutation<
      { data: any },
      { access_token: string; id: number }
    >({
      query({ access_token, id }) {
        return {
          url: `rooms/${id}`,
          method: "DELETE",
          headers: {
            Authorization: "Bearer " + access_token,
          },
          credentials: "include",
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data }: any | void = await queryFulfilled;
          console.log("data after delete room = ", data);
          // const decodedJWT: any = jwt_decode(data.access_token);
          // localStorage.setItem("roles", decodedJWT["roles"]);
        } catch (error) {}
      },
    }),
    changeRoom: build.mutation<
      { data: any },
      { access_token: string; roomData: IRoomRequest; id: number }
    >({
      query({ access_token, roomData, id }) {
        return {
          url: `rooms/${id}`,
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + access_token,
          },
          body: roomData,
          credentials: "include",
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data }: any | void = await queryFulfilled;
          console.log("data after change room = ", data);
          dispatch(updateRoom(data));
        } catch (error) {}
      },
    }),
  }),
});

export const {
  useGetRoomsQuery,
  useGetRoomByIdQuery,
  useGetRoomByOwnerIdQuery,
  useAddRoomMutation,
  useDelRoomMutation,
  useChangeRoomMutation,
} = roomsApi;
