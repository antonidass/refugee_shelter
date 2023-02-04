import { useDelRoomMutation } from "../redux/api/roomApi";
import { useCallback, useEffect, useState } from "react";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { useGetRoomByIdQuery } from "../redux/api/roomApi";
import ItemImages from "../components/ItemImages";
import Button from "../components/layout/Button";
import "react-datepicker/dist/react-datepicker.css";
import { toast } from "react-toastify";
import { useDispatch } from "react-redux";
import { delRoomState } from "../redux/features/roomSlice";
import { IRoom, IUser } from "../redux/api/types";

const MyRoomItem: React.FC<{}> = () => {
  const { id } = useParams();

  const { data = {}, isLoading } = useGetRoomByIdQuery(id);
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
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
    if (result.isSuccess) {
      toast.success("You successfully delete room!", {
        position: "top-center",
      });
      navigate("/profile/rooms");
    }
    if (result.isError) {
      if (Array.isArray((result.error as any).data.error)) {
        (result.error as any).data.error.forEach((el: any) =>
          toast.error(el.message, {
            position: "top-center",
          })
        );
      } else {
        console.log(result.error);
        toast.error((result.error as any).data.message, {
          position: "top-center",
        });
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [result.isLoading]);

  return (
    <div className="flex flex-col items-center space-y-8 py-8 max-w-3xl mx-auto">
      <h1 className="text-4xl text-textColor text-center">
        This is Name of Item
      </h1>
      <ItemImages id={id} />

      <div className="flex items-center justify-center w-full">
        <div className="flex flex-col items-center">
          <p className="text-md text-textColor font-thin max-w-sm">
            {data.description}
          </p>
          <p className="text-xl text-textColor mt-8">
            Max People : {data.people}
          </p>
          <p className="text-xl text-textColor">Beds : {data.beds}</p>
          <p className="text-xl text-textColor">Price : {data.price}$</p>
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
