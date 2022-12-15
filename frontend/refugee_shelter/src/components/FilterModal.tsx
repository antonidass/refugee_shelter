import { useState } from "react";
import { MdClose } from "react-icons/md";
import Input from "./layout/Input";
import Button from "./layout/Button";

interface FilterModalProps {
  showModal: boolean;
  setShowModal: () => void;
}

const FilterModal: React.FC<FilterModalProps> = ({
  showModal,
  setShowModal,
}) => {
  // <!--modal content-->

  const [from, setFrom] = useState("");
  const [to, setTo] = useState("");

  const onChangeFrom = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFrom(event.target.value);
  };

  const onChangeTo = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTo(event.target.value);
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
                    value={from}
                    onChange={onChangeFrom}
                    className="w-52 text-lg rounded-md px-2 py-2 text-textColor placeholder:font-thin text-md placeholder-borderColor focus:outline-4 outline-textColor  border-textColor border bg-opacity-0 bg-yellow-50"
                    type="text"
                  />
                  <input
                    placeholder="To..."
                    value={to}
                    onChange={onChangeTo}
                    type="text"
                    className="w-52 text-lg rounded-md px-2 py-2 text-textColor placeholder:font-thin text-md placeholder-borderColor focus:outline-4 outline-textColor  border-textColor border bg-opacity-0 bg-yellow-50"
                  />
                </div>
              </div>

              {/* Other Options Form */}
              <div className="flex flex-col justify-center bg-gradient-to-b from-primary/10 to-neutral/10 shadow-2xl rounded-xl mx-12 px-8 py-8 space-y-6">
                <div className=" flex justify-start items-center space-x-2">
                  <h1 className="text-textColor text-lg w-20">Beds</h1>
                  <button className="rounded-lg bg-neutral px-8 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    1
                  </button>
                  <button className="rounded-lg bg-neutral px-8 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    2
                  </button>
                  <button className="rounded-lg bg-neutral px-8 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    3
                  </button>
                  <button className="rounded-lg bg-neutral px-8 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    4
                  </button>
                  <button className="rounded-lg bg-neutral px-7 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    5+
                  </button>
                </div>

                {/* People Amount  */}
                <div className=" flex justify-between items-center space-x-2">
                  <h1 className="text-textColor text-lg w-20">People</h1>
                  <button className="rounded-lg bg-neutral px-8 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    1
                  </button>
                  <button className="rounded-lg bg-neutral px-8 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    2
                  </button>
                  <button className="rounded-lg bg-neutral px-8 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    3
                  </button>
                  <button className="rounded-lg bg-neutral px-8 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    4
                  </button>
                  <button className="rounded-lg bg-neutral px-7 py-1 text-textColor text-lg hover:bg-primary duration-200">
                    5+
                  </button>
                </div>
              </div>
              {/* Show Variants Button  */}
              <div className="pt-8 px-12">
                <Button
                  onClick={() => {}}
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
