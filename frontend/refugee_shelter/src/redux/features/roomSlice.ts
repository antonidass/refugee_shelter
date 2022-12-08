import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface IUserState {
  isLoggedIn: boolean;
}

const initialState: IUserState = {
  isLoggedIn: false,
};

export const roomSlice = createSlice({
  initialState,
  name: "authSlice",
  reducers: {
    logout: () => initialState,
    login: () => {
      const newState: IUserState = {
        isLoggedIn: true,
      };
      return newState;
    },
  },
});

export default roomSlice.reducer;

export const { logout, login } = roomSlice.actions;
