import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import { IResevations, IResevationsRequest } from "./types";

export const reservationsApi = createApi({
  reducerPath: "reservationsApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8081/api/v1/" }),
  endpoints: (build) => ({
    getReservations: build.query({
      query: () => "reservations",
    }),
    getReservationsById: build.query({
      query: (id) => `reservations/${id}`,
    }),
    getReservationsByRoomId: build.query({
      query: (id) => {
        return {
          url: `rooms/${id}/reservations`,
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          console.log("data after reservatiosn get = ", data);
          // localStorage.setItem("access_token", data.access_token);
          // localStorage.setItem("refresh_token", data.refresh_token);
          // localStorage.setItem("roles", "ROLE_USER");
          //   console.log("data after getRoomByOwnerId = ", data);
          //   dispatch(setRooms(data));
          // const decodedJWT: any = jwt_decode(data.access_token);
          // localStorage.setItem("roles", decodedJWT["roles"]);
        } catch (error) {
          console.log(error);
        }
      },
    }),
    addReservation: build.mutation<
      { data: any },
      { access_token: string; reservationData: IResevationsRequest }
    >({
      query({ access_token, reservationData }) {
        return {
          url: `reservations`,
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + access_token,
          },
          body: reservationData,
          credentials: "include",
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data }: any | void = await queryFulfilled;
          console.log("data after save reservation = ", data);
          //   dispatch(pushRoom(data));
        } catch (error) {}
      },
    }),
  }),
});

export const {
  useGetReservationsQuery,
  useGetReservationsByIdQuery,
  useGetReservationsByRoomIdQuery,
  useAddReservationMutation,
} = reservationsApi;
