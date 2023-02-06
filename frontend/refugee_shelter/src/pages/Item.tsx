import { useCallback, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useGetRoomByIdQuery } from "../redux/api/roomApi";
import ItemImages from "../components/ItemImages";
import Button from "../components/layout/Button";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import {
  useAddReservationMutation,
  useGetReservationsByRoomIdQuery,
} from "../redux/api/reservationsApi";
import handleServerResponse from "../utils/Utils";

const Item: React.FC<{}> = () => {
  const { id } = useParams();

  const { data = {}, isLoading } = useGetRoomByIdQuery(id);
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const navigate = useNavigate();

  const { data: reservationsData, isLoading: isLoadingRes } =
    useGetReservationsByRoomIdQuery(id);

  const [addReservation, result] = useAddReservationMutation();

  const handleBookUp = () => {
    if (localStorage.getItem("access_token") == null) {
      navigate("/login");
      return;
    }

    if (id === undefined) {
      return;
    }

    addReservation({
      access_token: localStorage.getItem("access_token") || "",
      reservationData: {
        startDate: startDate.getTime(),
        endDate: endDate.getTime(),
        roomId: +id,
      },
    });
  };

  // Обрабатываем ответ сервера
  useEffect(() => {
    if (handleServerResponse(result, "You successfully booked room!") === 0) {
      navigate(`/profile/reservations`);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [result.isLoading]);

  if (isLoadingRes) {
    return <h1>Loading...</h1>;
  }

  const dates = [];
  for (let i = 0; i < reservationsData.length; i++) {
    dates.push({
      start: new Date(reservationsData[i]["startDate"]),
      end: new Date(reservationsData[i]["endDate"]),
    });
  }

  return (
    <div className="flex flex-col items-center space-y-8 py-8 max-w-3xl mx-auto">
      <h1 className="text-4xl text-textColor text-center">{data.name}</h1>
      <ItemImages id={id} />

      <div className="flex items-start justify-between w-full space-x-12">
        <div className="flex flex-col items-left justify-start">
          <p className="text-xl text-textColor font-semibold max-w-xs mt-6">
            {data.address}
          </p>
          <p className="text-xl text-textColor mt-4">
            Max People : {data.people}
          </p>
          <p className="text-xl text-textColor">Beds : {data.beds}</p>
          <p className="text-md text-textColor font-thin max-w-xs mt-4">
            {data.description}
          </p>
        </div>

        {/* Блок заказа  */}
        <div className="bg-gradient-to-b from-primary/10 to-neutral/10 shadow-2xl flex flex-col my-auto items-center rounded-xl px-12 py-6 space-y-4">
          {/* Сумма  */}
          <h1 className="w-full text-2xl text-textColor">{data.price}$</h1>

          <div className="border border-white rounded-lg flex flex-col text-md text-textColor">
            {/* Выбор даты */}
            <div className="flex justify-between px-4">
              <div className="py-4">
                <DatePicker
                  className="bg-white bg-opacity-0 w-24 font-bold"
                  selected={startDate}
                  onChange={(date: Date) => setStartDate(date)}
                  selectsStart
                  startDate={startDate}
                  endDate={endDate}
                  excludeDateIntervals={dates}
                />
              </div>

              {/* Разделительная линия  */}
              <div className="min-h-full border-l border-white ml-12"></div>

              <div className="ml-4 mr-12 py-4 font-bold ">
                <DatePicker
                  className="bg-white bg-opacity-0 w-24"
                  selected={endDate}
                  onChange={(date: Date) => setEndDate(date)}
                  selectsEnd
                  startDate={startDate}
                  endDate={endDate}
                  excludeDateIntervals={dates}
                />
              </div>
            </div>
          </div>
          {/* Book Up Button  */}
          <div className="w-full">
            <Button
              onClick={handleBookUp}
              border="none"
              width="full"
              height="fit"
              color="secondary"
              textColor="textColor"
            >
              Book Up
            </Button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Item;
