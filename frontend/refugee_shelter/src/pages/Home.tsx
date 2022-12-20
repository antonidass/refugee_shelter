import { useEffect, useState } from "react";
import CustomMap from "../components/layout/CustomMap";
import MainList from "../components/MainList";
import { useGetRoomsQuery } from "../redux/api/roomApi";
import FilterMain from "../components/layout/FilterMain";
import { BsMap } from "react-icons/bs";
import { VscListFlat } from "react-icons/vsc";
import FilterModal from "../components/FilterModal";
import { useAppSelector } from "../redux/store";
import { IRoom } from "../redux/api/types";

const Home: React.FC<{}> = () => {
  const [isShowedMap, setIsShowedMap] = useState(true);
  const { data = [], isLoading } = useGetRoomsQuery({});
  const { beds, people, priceFrom, priceTo } = useAppSelector(
    (state) => state.filterState
  );

  const [showModal, setShowModal] = useState(false);
  const [filteredData, setFilteredData] = useState([]);

  const onClickShowModal = () => {
    setShowModal((prevState: boolean) => !prevState);
  };

  // Показывать Карту или Список
  const onChangeShowedItemHandler = () => {
    setIsShowedMap((prevState: boolean) => !prevState);
  };

  // Фильтруем данные по фильтрам
  useEffect(() => {
    console.log(data);
    setFilteredData(
      data.filter((item: IRoom) => {
        return (
          item.price >= priceFrom &&
          item.price <= priceTo &&
          item.people >= people &&
          item.beds >= beds
        );
      })
    );
  }, [isLoading, beds, people, priceFrom, priceTo]);

  return (
    <div className={`w-full h-full relative`}>
      {/* Модальное окно */}
      <FilterModal showModal={showModal} setShowModal={onClickShowModal} />

      {/* Карта или Список  */}
      <div className={`w-full h-full ${showModal ? "blur-sm" : ""}`}>
        {isShowedMap ? (
          <CustomMap data={filteredData} />
        ) : (
          <MainList data={filteredData} />
        )}
      </div>

      {/* Кнопка Фильтры  */}
      <div
        className={`fixed mt-40 left-0 top-0 mx-8 ${
          showModal ? "blur-sm" : ""
        }`}
        onClick={() => setShowModal(true)}
      >
        <FilterMain />
      </div>

      {/* Показывать карту или список кнопка  */}
      <div
        className={`fixed inset-x-0 bottom-0 mx-auto mb-10 bg-primary rounded-xl w-60 flex items-center justify-center space-x-8 py-2 px-6 ${
          showModal ? "blur-sm" : ""
        }`}
        onClick={onChangeShowedItemHandler}
      >
        <h1 className="text-2xl text-[#FEFFFF]">
          Show {isShowedMap ? "List" : "Map"}
        </h1>

        {isShowedMap ? (
          <BsMap size={32} color="#FEFFFF" />
        ) : (
          <VscListFlat size={32} color="#FEFFFF" />
        )}
      </div>
    </div>
  );
};

export default Home;
