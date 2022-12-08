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

  return (
    <>
      {showModal ? (
        <>
          <div className="flex justify-center items-center fixed inset-0 z-50 outline-none focus:outline-none">
            <div className="bg-neutral flex flex-col rounded-2xl space-y-12">
              {/* Top Header */}
              <div className="flex items-center justify-between px-8 py-6">
                <h1 className="text-2xl font-bold mx-40  text-textColor text-center">
                  Filters
                </h1>
                <MdClose color="textColor" size={36} />
              </div>

              {/* Price Range Form */}
              <div className="flex flex-col justify-center bg-gradient-to-b from-primary/10 to-neutral/10 shadow-2xl rounded-xl mx-12 px-8 py-8 space-y-4">
                <h1 className="text-textColor text-lg">Price Range</h1>
                <div className=" flex justify-between items-center space-x-4">
                  <Input
                    placeholder="From..."
                    value=""
                    size="xl"
                    type="text"
                    color=""
                    onChangeHandler={() => {}}
                  />
                  <Input
                    placeholder="To..."
                    value=""
                    size="xl"
                    type="text"
                    color=""
                    onChangeHandler={() => {}}
                  />
                </div>
              </div>

              {/* Other Options Form */}
              <div className="flex flex-col justify-center bg-gradient-to-b from-primary/10 to-neutral/10 shadow-2xl rounded-xl mx-12 px-8 py-8 space-y-4">
                <div className=" flex justify-between items-center space-x-4">
                  <h1 className="text-textColor text-lg">Beds</h1>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    1
                  </Button>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    2
                  </Button>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    3
                  </Button>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    4
                  </Button>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    5+
                  </Button>
                </div>

                {/* People Amount  */}
                <div className=" flex justify-between items-center space-x-4">
                  <h1 className="text-textColor text-lg">People</h1>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    1
                  </Button>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    2
                  </Button>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    3
                  </Button>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    4
                  </Button>
                  <Button
                    onClick={() => {}}
                    border="none"
                    width="20"
                    height="fit"
                    color="primary"
                    textColor="textColor"
                  >
                    5+
                  </Button>
                </div>
              </div>
            </div>
          </div>
        </>
      ) : null}
    </>
  );
};

export default FilterModal;
