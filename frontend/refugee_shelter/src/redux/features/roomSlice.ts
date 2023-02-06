import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { IRoom } from "../api/types";

const initialState: IRoom[] = [];

export const roomSlice = createSlice({
  initialState,
  name: "roomSlice",
  reducers: {
    setRooms: (state, action: PayloadAction<IRoom[]>) => {
      console.log("IN SET ROOMS", state, action.payload);
      let newState: IRoom[] = [];
      for (let i = 0; i < action.payload.length; i++) {
        newState.push({
          id: action.payload[i]["id"],
          name: action.payload[i]["name"],
          address: action.payload[i]["address"],
          latitude: action.payload[i]["latitude"],
          longitude: action.payload[i]["longitude"],
          price: action.payload[i]["price"],
          imageUrl: action.payload[i]["imageUrl"],
          beds: action.payload[i]["beds"],
          people: action.payload[i]["people"],
          description: action.payload[i]["description"],
          user: action.payload[i]["user"],
        });
      }
      return newState;
    },
    pushRoom: (state, action: PayloadAction<IRoom>) => {
      console.log("IN ADD ROOM SLICE");
      return [...state, action.payload];
    },
    updateRoom: (state, action: PayloadAction<IRoom>) => {
      const newState = state.filter((room) => room.id !== action.payload.id);
      return [...newState, action.payload];
    },
    delRoomState: (state, action: PayloadAction<number>) => {
      const newState = state.filter((room) => room.id !== action.payload);
      return newState;
    },
  },
});

export default roomSlice.reducer;

export const { setRooms, pushRoom, delRoomState, updateRoom } =
  roomSlice.actions;
