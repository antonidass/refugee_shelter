import { configureStore } from "@reduxjs/toolkit";
import { TypedUseSelectorHook, useDispatch, useSelector } from "react-redux";
import { authApi } from "./api/authApi";
import { roomsApi } from "./api/roomApi";
import authReducer from "./features/authSlice";
import filterReducer from "./features/filterSlice";
import reservationsReducer from "./features/reservationsSlice";
import roomReducer from "./features/roomSlice";
import { reservationsApi } from "./api/reservationsApi";

export const store = configureStore({
  reducer: {
    [authApi.reducerPath]: authApi.reducer,
    [roomsApi.reducerPath]: roomsApi.reducer,
    [reservationsApi.reducerPath]: reservationsApi.reducer,
    authState: authReducer,
    filterState: filterReducer,
    roomState: roomReducer,
    reservationsState: reservationsReducer,
  },
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({}).concat([
      authApi.middleware,
      roomsApi.middleware,
      reservationsApi.middleware,
    ]),
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
