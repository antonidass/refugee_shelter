import { useState } from "react";
import { MdClose } from "react-icons/md";
import Input from "./layout/Input";
import Button from "./layout/Button";
import { useAppSelector, useAppDispatch } from "../redux/store";
import { addFilter } from "../redux/features/filterSlice";

interface FilterModalProps {
  showModal: boolean;
  setShowModal: () => void;
}

const FilterModal: React.FC<FilterModalProps> = ({
  showModal,
  setShowModal,
}) => {
  // <!--modal content-->

  // const { beds, people, priceFrom, priceTo } = useAppSelector(
  //   (state) => state.filterState
  // );
  const [beds, setBeds] = useState(1);
  const [people, setPeople] = useState(1);
  const [priceFrom, setPriceFrom] = useState(0);
  const [priceTo, setPriceTo] = useState(1000);

  const dispatch = useAppDispatch();

  const onChangeFrom = (event: React.ChangeEvent<HTMLInputElement>) => {
    let fromValue: number = 0;

    if (event.target.value !== "-") {
      fromValue = +event.target.value;
    }

    if (isNaN(fromValue)) {
      return;
    }
    setPriceFrom(fromValue);
  };

  const onChangeTo = (event: React.ChangeEvent<HTMLInputElement>) => {
    let toValue: number = 0;

    if (event.target.value !== "-") {
      toValue = +event.target.value;
    }

    if (isNaN(toValue)) {
      return;
    }

    setPriceTo(toValue);
  };

  const onChangeBeds = (bedsCount: number) => {
    setBeds(bedsCount);
  };
  const onChangePeople = (peopleCount: number) => {
    setPeople(peopleCount);
  };

  const onShowVariants = () => {
    dispatch(
      addFilter({
        beds,
        people,
        priceFrom,
        priceTo,
      })
    );
    setShowModal();
  };

  return (
    <>
      {showModal ? (
        <>
          <div className="flex justify-center items-center fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="bg-neutral flex flex-col rounded-2xl space-y-12 pb-12 shadow-3xl">
              {/* Top Header */}
              <div className="flex items-center justify-center px-8 py-6 relative">
                <h1 className="text-2xl font-bold   text-textColor text-center">
                  Filters
                </h1>
                <MdClose
                  color="white"
                  size={40}
                  onClick={() => {
                    setShowModal();
                  }}
                  className="absolute right-0 top-0 mx-4 my-5 cursor-pointer"
                />
              </div>

              {/* Price Range Form */}
              <div className="flex flex-col justify-center bg-gradient-to-b from-primary/10 to-neutral/10 shadow-2xl rounded-xl mx-12 px-8 py-8 space-y-4">
                <h1 className="text-textColor text-lg">Price Range</h1>
                <div className=" flex justify-between items-center space-x-4">
                  <input
                    placeholder="From..."
                    value={priceFrom}
                    onChange={onChangeFrom}
                    className="w-52 text-lg rounded-md px-2 py-2 text-textColor placeholder:font-thin text-md placeholder-borderColor focus:outline-4 outline-textColor  border-textColor border bg-opacity-0 bg-yellow-50"
                    type="text"
                  />
                  <input
                    placeholder="To..."
                    value={priceTo}
                    onChange={onChangeTo}
                    type="text"
                    className="w-52 text-lg rounded-md px-2 py-2 text-textColor placeholder:font-thin text-md placeholder-borderColor focus:outline-4 outline-textColor  border-textColor border bg-opacity-0 bg-yellow-50"
                  />
                </div>
              </div>

              {/* Other Options Form */}
              <div className="flex flex-col justify-center bg-gradient-to-b from-primary/10 to-neutral/10 shadow-2xl rounded-xl mx-12 px-8 py-8 space-y-6">
                <div className="flex justify-start items-center space-x-2">
                  <h1 className="text-textColor text-lg w-20">Beds</h1>
                  <button
                    onClick={() => {
                      onChangeBeds(1);
                    }}
                    className={`rounded-lg ${
                      beds === 1 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    1
                  </button>
                  <button
                    onClick={() => {
                      onChangeBeds(2);
                    }}
                    className={`rounded-lg ${
                      beds === 2 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    2
                  </button>
                  <button
                    onClick={() => {
                      onChangeBeds(3);
                    }}
                    className={`rounded-lg ${
                      beds === 3 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    3
                  </button>
                  <button
                    onClick={() => {
                      onChangeBeds(4);
                    }}
                    className={`rounded-lg ${
                      beds === 4 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    4
                  </button>
                  <button
                    onClick={() => {
                      onChangeBeds(5);
                    }}
                    className={`rounded-lg ${
                      beds >= 5 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    5
                  </button>
                </div>

                {/* People Amount  */}
                <div className=" flex justify-between items-center space-x-2">
                  <h1 className="text-textColor text-lg w-20">People</h1>
                  <button
                    onClick={() => {
                      onChangePeople(1);
                    }}
                    className={`rounded-lg ${
                      people === 1 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    1
                  </button>
                  <button
                    onClick={() => {
                      onChangePeople(2);
                    }}
                    className={`rounded-lg ${
                      people === 2 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    2
                  </button>
                  <button
                    onClick={() => {
                      onChangePeople(3);
                    }}
                    className={`rounded-lg ${
                      people === 3 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    3
                  </button>
                  <button
                    onClick={() => {
                      onChangePeople(4);
                    }}
                    className={`rounded-lg ${
                      people === 4 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    4
                  </button>
                  <button
                    onClick={() => {
                      onChangePeople(5);
                    }}
                    className={`rounded-lg ${
                      people >= 5 ? "bg-primary" : "bg-neutral"
                    } px-8 py-1 text-textColor text-lg hover:bg-primary duration-200`}
                  >
                    5
                  </button>
                </div>
              </div>
              {/* Show Variants Button  */}
              <div className="pt-8 px-12">
                <Button
                  onClick={onShowVariants}
                  border="none"
                  width="full"
                  height="fit"
                  color="secondary"
                  textColor="textColor"
                >
                  Show Variants
                </Button>
              </div>
            </div>
          </div>
        </>
      ) : null}
    </>
  );
};

export default FilterModal;
