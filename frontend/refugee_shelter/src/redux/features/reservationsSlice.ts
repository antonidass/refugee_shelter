import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { IResevations } from "../api/types";

const initialState: IResevations[] = [];

export const reservationsSlice = createSlice({
  initialState,
  name: "reservationsSlice",
  reducers: {
    setReservations: (state, action: PayloadAction<IResevations[]>) => {
      console.log("IN SET RESERVATIONS", state, action.payload);
      let newState: IResevations[] = [];
      for (let i = 0; i < action.payload.length; i++) {
        newState.push({
          id: action.payload[i]["id"],
          startDate: action.payload[i]["startDate"],
          endDate: action.payload[i]["endDate"],
          user: action.payload[i]["user"],
          rooms: action.payload[i]["rooms"],
        });
      }
      return newState;
    },
    pushRes: (state, action: PayloadAction<IResevations>) => {
      console.log("IN ADD RES SLICE");
      return [...state, action.payload];
    },
    updateRes: (state, action: PayloadAction<IResevations>) => {
      const newState = state.filter((res) => res.id !== action.payload.id);
      return [...newState, action.payload];
    },
    delResState: (state, action: PayloadAction<number>) => {
      const newState = state.filter((res) => res.id !== action.payload);
      return newState;
    },
  },
});

export default reservationsSlice.reducer;

export const { setReservations, updateRes, pushRes, delResState } =
  reservationsSlice.actions;
