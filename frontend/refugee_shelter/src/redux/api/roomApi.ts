import { createApi, fetchBaseQuery } from "@reduxjs/toolkit/query/react";

export const roomsApi = createApi({
  reducerPath: "roomsApi",
  baseQuery: fetchBaseQuery({ baseUrl: "http://localhost:8081/api/v1/" }),
  endpoints: (build) => ({
    getRooms: build.query({
      query: () => "rooms",
    }),
  }),
});

export const { useGetRoomsQuery } = roomsApi;
