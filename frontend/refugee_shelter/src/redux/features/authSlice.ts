import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { Action } from "@remix-run/router";
import { IUser } from "../api/types";

interface IUserState extends IUser {}

const initialState: IUserState = {
  id: 0,
  name: "",
  email: "",
  phone: "",
};

export const authSlice = createSlice({
  initialState,
  name: "authSlice",
  reducers: {
    logout: () => {
      localStorage.removeItem("isLoggedIn");
      return initialState;
    },
    login: () => {
      // const newState: IUserState = {
      //   isLoggedIn: true,
      // };
      localStorage.setItem("isLoggedIn", "1");
      // return newState;
    },
    setUser: (state, action: any) => {
      console.log("HERE", state, action.payload);
      const newState: IUserState = {
        id: action.payload.id,
        email: action.payload.email,
        phone: action.payload.phone,
        name: action.payload.name,
      };
      return newState;
    },
  },
});

export default authSlice.reducer;

export const { logout, login, setUser } = authSlice.actions;
