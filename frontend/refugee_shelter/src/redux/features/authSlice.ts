import { createSlice, PayloadAction } from "@reduxjs/toolkit";
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
      localStorage.setItem("isLoggedIn", "1");
    },
    setUser: (state, action: PayloadAction<IUser>) => {
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
