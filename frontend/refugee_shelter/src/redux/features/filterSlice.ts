import { createSlice, PayloadAction } from "@reduxjs/toolkit";

interface IFilter {
  priceFrom: number;
  priceTo: number;
  beds: number;
  people: number;
  isHiddenBeds: boolean;
  isHiddenPerson: boolean;
  holderPriceFrom: number;
  holderPriceTo: number;
  holderBeds: number;
  holderPeople: number;
}

const initialState: IFilter = {
  priceFrom: 0,
  priceTo: 100000,
  beds: 0,
  people: 0,
  isHiddenBeds: true,
  isHiddenPerson: true,
  holderPriceFrom: 0,
  holderPriceTo: 100000,
  holderBeds: 0,
  holderPeople: 0,
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
