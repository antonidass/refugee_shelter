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
    getImages: build.query({
      query: (image_name: string) => `image/${image_name}`,
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
          console.log("data after getRoomByOwnerId = ", data);
          dispatch(setRooms(data));
        } catch (error) {}
      },
    }),
    addRoom: build.mutation<
      IRoom,
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
          const { data } = await queryFulfilled;
          console.log("data after save room = ", data);
          dispatch(pushRoom(data));
        } catch (error) {}
      },
    }),
    delRoom: build.mutation<{}, { access_token: string; id: number }>({
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
    }),
    changeRoom: build.mutation<
      IRoom,
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
          const { data } = await queryFulfilled;
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
  useGetImagesQuery,
} = roomsApi;
