import { useState } from "react";
import { useDispatch } from "react-redux";
import { addFilter } from "../../redux/features/filterSlice";
import { useAppSelector } from "../../redux/store";
import FilterBadge from "./FilterBadge";

const FiltersBar: React.FC<{}> = () => {
  const {
    beds,
    people,
    isHiddenBeds,
    isHiddenPerson,
    priceFrom,
    priceTo,
    holderBeds,
    holderPeople,
    holderPriceFrom,
    holderPriceTo,
  } = useAppSelector((state) => state.filterState);

  const dispatch = useDispatch();

  const onClosePersonsBadge = () => {
    dispatch(
      addFilter({
        beds,
        people: 0,
        priceFrom,
        priceTo,
        isHiddenBeds,
        isHiddenPerson: true,
        holderBeds,
        holderPeople: 0,
        holderPriceFrom,
        holderPriceTo,
      })
    );
  };

  const onCloseBedsBadge = () => {
    dispatch(
      addFilter({
        beds: 0,
        people,
        priceFrom,
        priceTo,
        isHiddenBeds: true,
        isHiddenPerson,
        holderBeds: 0,
        holderPeople,
        holderPriceFrom,
        holderPriceTo,
      })
    );
  };

  return (
    <>
      <FilterBadge
        text={`${beds} Beds`}
        isHidden={isHiddenBeds}
        onCloseBadge={onCloseBedsBadge}
      />
      <FilterBadge
        text={`${people} Person`}
        isHidden={isHiddenPerson}
        onCloseBadge={onClosePersonsBadge}
      />
    </>
  );
};

export default FiltersBar;
