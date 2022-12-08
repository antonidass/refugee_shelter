import { useState } from "react";
import CustomMap from "../components/layout/CustomMap";
import MainList from "../components/MainList";
import { useGetRoomsQuery } from "../redux/api/roomApi";
import FilterMain from "../components/layout/FilterMain";
import { BsMap } from "react-icons/bs";
import { VscListFlat } from "react-icons/vsc";
import FilterModal from "../components/FilterModal";

const Home: React.FC<{}> = () => {
  const [isShowedMap, setIsShowedMap] = useState(true);
  const { data = [], isLoading } = useGetRoomsQuery({});

  const [showModal, setShowModal] = useState(false);

  const onClickShowModal = () => {
    setShowModal((prevState: boolean) => !prevState);
  };

  // Показывать Карту или Список
  const onChangeShowedItemHandler = () => {
    setIsShowedMap((prevState: boolean) => !prevState);
  };

  return (
    <div className={`w-full h-full relative`}>
      <FilterModal showModal={showModal} setShowModal={onClickShowModal} />

      <div
        className={` w-full h-full ${isShowedMap ? "" : "mt-10"} ${
          showModal ? "blur-sm" : ""
        }`}
      >
        {/* Модальное окно */}
        {isShowedMap ? (
          <>
            <CustomMap data={data} />
            <div
              className="fixed inset-x-0 bottom-0 mx-auto mb-10 bg-primary rounded-xl w-60 flex items-center justify-center space-x-8 py-2 px-6"
              onClick={onChangeShowedItemHandler}
            >
              <h1 className="text-2xl text-[#FEFFFF]">Show List</h1>
              <VscListFlat size={32} color="#FEFFFF" />
            </div>
          </>
        ) : (
          <>
            <MainList data={data} />
            <div
              className="fixed inset-x-0 bottom-0 mx-auto mb-10 bg-primary rounded-xl w-60 flex items-center justify-center space-x-8 py-2 px-6"
              onClick={onChangeShowedItemHandler}
            >
              <h1 className="text-2xl text-[#FEFFFF]">Show Map</h1>
              <BsMap size={32} color="#FEFFFF" />
            </div>
          </>
        )}

        {/* Кнопка Фильтры  */}
        <div
          className="fixed mt-40 left-0 top-0 mx-8"
          onClick={() => setShowModal(true)}
        >
          <FilterMain />
        </div>
      </div>
    </div>
  );
};

export default Home;
