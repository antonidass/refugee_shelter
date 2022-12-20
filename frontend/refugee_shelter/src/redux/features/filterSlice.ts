import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface IFilter {
  priceFrom: number;
  priceTo: number;
  beds: number;
  people: number;
}

const initialState: IFilter = {
  priceFrom: 0,
  priceTo: 1000,
  beds: 1,
  people: 1,
};

const filterSlice = createSlice({
  name: "filterSlice",
  initialState,
  reducers: {
    addFilter(state, action: PayloadAction<IFilter>) {
      return action.payload;
    },
  },
});

export default filterSlice.reducer;
export const { addFilter } = filterSlice.actions;
