import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";
import {
  pushRes,
  setReservations,
  updateRes,
} from "../features/reservationsSlice";
import {
  IResevationsRequest,
  IResevationsRequestUpdate,
  IResevations,
} from "./types";

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
        } catch (error) {
          console.log(error);
        }
      },
    }),
    getReservationsByOwnerId: build.query({
      query: ({ id, token }) => {
        return {
          url: `users/${id}/reservations`,
          headers: { Authorization: "Bearer " + token },
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          console.log("data after reservatiosn by owner id get = ", data);
          dispatch(setReservations(data));
        } catch (error) {
          console.log(error);
        }
      },
    }),
    addReservation: build.mutation<
      IResevations,
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
          const { data } = await queryFulfilled;
          console.log("data after save reservation = ", data);
          dispatch(pushRes(data));
        } catch (error) {}
      },
    }),
    delReservation: build.mutation<{}, { access_token: string; id: number }>({
      query({ access_token, id }) {
        return {
          url: `reservations/${id}`,
          method: "DELETE",
          headers: {
            Authorization: "Bearer " + access_token,
          },
          credentials: "include",
        };
      },
    }),
    changeRes: build.mutation<
      IResevations,
      { access_token: string; resData: IResevationsRequestUpdate; id: number }
    >({
      query({ access_token, resData, id }) {
        return {
          url: `reservations/${id}`,
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: "Bearer " + access_token,
          },
          body: resData,
          credentials: "include",
        };
      },
      async onQueryStarted(args, { dispatch, queryFulfilled }) {
        try {
          const { data } = await queryFulfilled;
          console.log("data after change room = ", data);
          dispatch(updateRes(data));
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
  useGetReservationsByOwnerIdQuery,
  useDelReservationMutation,
  useChangeResMutation,
} = reservationsApi;
