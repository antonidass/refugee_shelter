import Button from "../components/layout/Button";
import { useEffect, useState } from "react";
import { IResevationsRequestUpdate } from "../redux/api/types";
import DatePicker from "react-datepicker";
import { useNavigate, useParams } from "react-router-dom";
import handleServerResponse from "../utils/Utils";
import {
  useGetReservationsByRoomIdQuery,
  useChangeResMutation,
} from "../redux/api/reservationsApi";

const ChangeReservation: React.FC<{}> = () => {
  const { resId, roomId } = useParams();

  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const navigate = useNavigate();

  const { data: reservationsData, isLoading: isLoadingRes } =
    useGetReservationsByRoomIdQuery(roomId);

  const [changeRes, result] = useChangeResMutation();

  const handleChangeRes = async () => {
    const resPayload: IResevationsRequestUpdate = {
      startDate: startDate.getTime(),
      endDate: endDate.getTime(),
    };

    if (resId === undefined) {
      return;
    }

    await changeRes({
      access_token: localStorage.getItem("access_token") || "",
      resData: resPayload,
      id: +resId,
    });
  };

  // Обрабатываем ответ сервера
  useEffect(() => {
    if (
      handleServerResponse(result, "You successfully change reservation!") === 0
    ) {
      navigate("/profile/reservations");
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
    <div className="flex flex-col justify-center items-center my-20">
      {/* Change room */}
      <div className="flex items-center justify-center px-8 py-6">
        <h1 className="text-2xl font-bold   text-textColor text-center">
          Change Reservation
        </h1>
      </div>
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
      {/* Change Reservations Button  */}
      <div className="pt-8 px-12 w-80">
        <Button
          onClick={handleChangeRes}
          border="none"
          width="full"
          height="fit"
          color="secondary"
          textColor="textColor"
        >
          Change Reservation
        </Button>
      </div>
    </div>
  );
};

export default ChangeReservation;
