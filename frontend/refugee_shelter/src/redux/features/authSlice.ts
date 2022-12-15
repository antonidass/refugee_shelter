import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface IUserState {
  isLoggedIn: boolean;
}

const initialState: IUserState = {
  isLoggedIn: false,
};

export const authSlice = createSlice({
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

export default authSlice.reducer;

export const { logout, login } = authSlice.actions;
