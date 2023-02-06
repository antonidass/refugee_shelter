import { IResevations, IRoom } from "../redux/api/types";
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { AiOutlineCloseCircle } from "react-icons/ai";
import { AiOutlineEdit } from "react-icons/ai";
import { useDelReservationMutation } from "../redux/api/reservationsApi";
import handleServerResponse from "../utils/Utils";
import { useDispatch } from "react-redux";
import { delResState } from "../redux/features/reservationsSlice";

interface ReservationListItemProps {
  resItem: IResevations;
}

const ReservationListItem: React.FC<ReservationListItemProps> = ({
  resItem,
}) => {
  const [img, setImg] = useState("");
  const [delReservation, result] = useDelReservationMutation();
  const navigate = useNavigate();
  const dispatch = useDispatch();
  // Подгружаем фото
  const fetchImage = async () => {
    console.log("Item = ", resItem);
    const res = await fetch(
      "http://localhost:8081/api/v1/image/" + resItem.rooms.id + "_1"
    );
    console.log(
      "http://localhost:8081/api/v1/image/" + resItem.rooms.id + "_1"
    );
    if (res.status === 400) {
      setImg("noImage");
    } else {
      const imageBlob = await res.blob();
      const imageObjectURL = URL.createObjectURL(imageBlob);
      setImg(imageObjectURL);
    }
  };

  useEffect(() => {
    fetchImage();
  }, []);

  // Удаление брони
  const handleDeleteReservation = () => {
    delReservation({
      id: resItem.id,
      access_token: localStorage.getItem("access_token") || "",
    });

    dispatch(delResState(resItem.id));
  };

  // Обновление брони
  const handleUpdateReservation = () => {
    navigate(
      `/profile/rooms/${resItem.rooms.id}/reservations/${resItem.id}/update`
    );
  };

  // Обрабатываем ответ сервера
  useEffect(() => {
    if (
      handleServerResponse(result, "You successfully delete Reservation!") === 0
    ) {
      //   navigate("/profile/reservations");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [result.isLoading]);

  return (
    <div className="flex flex-row items-center mx-auto">
      {/* Аватарка */}
      <Link to={`/items/${resItem.rooms.id}`}>
        {img === "noImage" ? (
          <img
            src={require("./noImage.jpeg")}
            alt="icons"
            className="w-60 h-44 rounded-xl"
          />
        ) : (
          <img src={img} alt="icons" className="w-60 h-44 rounded-xl" />
        )}
      </Link>
      {/* Описание  */}
      <div className="flex flex-col w-96 ml-6">
        <h1 className="text-2xl text-textColor font-bold max-w-xs">
          {resItem.rooms.name}
        </h1>
        <h1 className="text-xl text-textColor font-light ">
          Start Date - {new Date(resItem.startDate).toLocaleDateString()}
        </h1>
        <h1 className="text-xl text-textColor font-light ">
          End Date - {new Date(resItem.endDate).toLocaleDateString()}
        </h1>
      </div>

      <AiOutlineCloseCircle
        size={50}
        onClick={() => {
          handleDeleteReservation();
        }}
        color={"white"}
        className="cursor-pointer"
      />

      <AiOutlineEdit
        size={50}
        onClick={() => {
          handleUpdateReservation();
        }}
        color={"white"}
        className="cursor-pointer ml-4"
      />
    </div>
  );
};

export default ReservationListItem;
