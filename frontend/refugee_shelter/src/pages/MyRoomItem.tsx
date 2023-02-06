import { useDelRoomMutation } from "../redux/api/roomApi";
import { useCallback, useEffect, useState } from "react";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { useGetRoomByIdQuery } from "../redux/api/roomApi";
import ItemImages from "../components/ItemImages";
import Button from "../components/layout/Button";
import "react-datepicker/dist/react-datepicker.css";
import { useDispatch } from "react-redux";
import { delRoomState } from "../redux/features/roomSlice";
import handleServerResponse from "../utils/Utils";

const MyRoomItem: React.FC<{}> = () => {
  const { id } = useParams();

  const { data = {}, isLoading } = useGetRoomByIdQuery(id);
  const navigate = useNavigate();
  const [delRoom, result] = useDelRoomMutation();
  const dispatch = useDispatch();

  const onDeleteHandler = () => {
    const accessToken = localStorage.getItem("access_token") || "";
    if (id === undefined) {
      return;
    }
    // Удаление из бекенда
    delRoom({
      access_token: accessToken,
      id: +id,
    });

    dispatch(delRoomState(+id));
  };

  const onChangeHandler = () => {
    navigate(`/profile/rooms/${id}/change`);
  };

  // Обрабатываем ответ сервера
  useEffect(() => {
    if (handleServerResponse(result, "You successfully delete room!") === 0) {
      navigate("/profile/rooms");
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [result.isLoading]);

  return (
    <div className="flex flex-col items-center space-y-8 py-8 max-w-3xl mx-auto">
      <h1 className="text-4xl text-textColor text-center">{data.name}</h1>
      <ItemImages id={id} />

      <div className="flex items-center justify-center w-full">
        <div className="flex flex-col items-center">
          <p className="text-md text-textColor text-center font-thin max-w-sm">
            {data.description}
          </p>
          <p className="text-xl text-textColor mt-8">
            Max People : {data.people}
          </p>
          <p className="text-xl text-textColor">Beds : {data.beds}</p>
          <p className="text-xl text-textColor">Price : {data.price}$</p>
          <p className="text-xl text-semibold text-textColor">
            Address : {data.address}
          </p>
        </div>
      </div>

      {/* Change Room Button  */}
      <div className="w-80">
        <Button
          onClick={onChangeHandler}
          border="none"
          width="full"
          height="fit"
          color="secondary"
          textColor="textColor"
        >
          Change Room
        </Button>
      </div>
      {/* Delete Room Button  */}
      <div className="w-80">
        <Button
          onClick={onDeleteHandler}
          border="none"
          width="full"
          height="fit"
          color="secondary"
          textColor="textColor"
        >
          Delete Room
        </Button>
      </div>
    </div>
  );
};

export default MyRoomItem;
