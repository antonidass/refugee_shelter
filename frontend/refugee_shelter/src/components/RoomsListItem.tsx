import { IRoom } from "../redux/api/types";
import { useEffect, useState } from "react";

interface RoomsListItemProps {
  roomItem: IRoom;
}

const RoomsListItem: React.FC<RoomsListItemProps> = ({ roomItem }) => {
  const [img, setImg] = useState("");

  const fetchImage = async () => {
    const res = await fetch(
      "http://localhost:8081/api/v1/image/" + roomItem.id + "_1"
    );
    if (res.status === 400) {
      setImg("assets/noImage.jpeg");
    } else {
      const imageBlob = await res.blob();
      const imageObjectURL = URL.createObjectURL(imageBlob);
      setImg(imageObjectURL);
    }
  };

  useEffect(() => {
    fetchImage();
  }, []);

  return (
    <div className="flex flex-col">
      <img src={img} alt="icons" className="w-96 h-80 rounded-xl" />
      <h1 className="text-xl text-textColor font-bold mt-2">
        {roomItem.address}
      </h1>
      <h1 className="text-lg font-thin text-textColor mt-4">
        {roomItem.description}
      </h1>
      <h1 className="text-textColor font-normal text-lg">{roomItem.price}$</h1>
    </div>
  );
};

export default RoomsListItem;
