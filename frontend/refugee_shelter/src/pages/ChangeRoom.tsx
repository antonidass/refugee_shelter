import Button from "../components/layout/Button";
import Input from "../components/layout/Input";
import {
  useAddRoomMutation,
  useChangeRoomMutation,
  useGetRoomByIdQuery,
} from "../redux/api/roomApi";
import { useEffect, useState } from "react";
import { IRoomRequest } from "../redux/api/types";
import { toast } from "react-toastify";

import {
  YMaps,
  Map,
  Placemark,
  ZoomControl,
  RulerControl,
} from "@pbe/react-yandex-maps";
import { useNavigate, useParams } from "react-router-dom";

const ChangeRoom: React.FC<{}> = () => {
  const [name, setName] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState(0);
  const [beds, setBeds] = useState(1);
  const [people, setPeople] = useState(1);
  const [images, setImages] = useState<FileList | null>();
  const [latitude, setLatitude] = useState(55.751574);
  const [longitude, setLongitude] = useState(37.573856);
  const [address, setAddress] = useState("");

  const navigate = useNavigate();
  const { id } = useParams();
  const { data = {}, isLoading } = useGetRoomByIdQuery(id);
  const [changeRoom, result] = useChangeRoomMutation();

  const defaultState = {
    center: [55.751574, 37.573856],
    zoom: 12,
  };

  const handleSetImage = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { files } = event.target;
    setImages(files);
  };

  const handleChangeRoom = async () => {
    const roomPayload: IRoomRequest = {
      name,
      description,
      price,
      beds,
      people,
      address,
      longitude,
      latitude,
      imageUrl: "none",
    };

    const accessToken = localStorage.getItem("access_token") || "";
    // Изменение комнаты
    if (id === undefined) {
      return;
    }

    const { data }: any = await changeRoom({
      access_token: accessToken,
      roomData: roomPayload,
      id: +id,
    });

    if (images === null || images === undefined) {
      return;
    }

    // Удаление старых фото
    let requestDelOptions = {
      method: "DELETE",
    };
    fetch(`http://localhost:8081/api/v1/image/${data.id}`, requestDelOptions)
      .then((response) => response.text())
      .then((result) => console.log(result))
      .catch((error) => console.log("error", error));

    // Сохранение фото комнаты
    for (let i = 0; i < images.length; i++) {
      let formdata = new FormData();
      formdata.append("img", images[i]);
      formdata.append("name", data.id + "_" + (i + 1));

      let requestOptions = {
        method: "POST",
        body: formdata,
      };

      fetch("http://localhost:8081/api/v1/image", requestOptions)
        .then((response) => response.text())
        .then((result) => console.log(result))
        .catch((error) => console.log("error", error));
    }
  };

  // Обрабатываем ответ сервера
  useEffect(() => {
    if (result.isSuccess) {
      toast.success("You successfully added room!", {
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

  const onMapClick = (e: any) => {
    setLatitude(e.get("coords")[0]);
    setLongitude(e.get("coords")[1]);
  };

  useEffect(() => {
    setName(data.name);
    setPrice(data.price);
    setAddress(data.address);
    setDescription(data.description);
    setLongitude(data.longitude);
    setLatitude(data.latitude);
    setBeds(data.beds);
    setPeople(data.people);
  }, [isLoading]);

  return (
    <div className="flex justify-center items-center my-20">
      <div className="bg-neutral flex flex-col rounded-2xl space-y-12 pb-12 shadow-3xl">
        {/* Change room */}
        <div className="flex items-center justify-center px-8 py-6">
          <h1 className="text-2xl font-bold   text-textColor text-center">
            Change Room
          </h1>
        </div>

        {/* Name  */}
        <div className="flex items-center justify-between space-x-32 mx-12">
          <h3 className="text-xl text-[#FEFFFF]">Name</h3>
          <Input
            placeholder="Name of room..."
            value={name}
            color=""
            size="w-96"
            type="text"
            onChangeHandler={(event) => {
              setName(event.target.value);
            }}
          />
        </div>

        {/* Price  */}
        <div className="flex items-center justify-between mx-12">
          <h3 className="text-xl text-[#FEFFFF]">Price</h3>
          <Input
            placeholder="Price..."
            value={price}
            color=""
            size="w-96"
            type="text"
            onChangeHandler={(event) => {
              setPrice(+event.target.value);
            }}
          />
        </div>

        {/* Description */}
        <div className="flex items-start justify-between mx-12">
          <h3 className="text-xl text-[#FEFFFF]">Description</h3>
          <textarea
            className="resize-none w-96 h-36 rounded-md px-2 py-2 text-textColor placeholder:font-thin text-md placeholder-borderColor focus:outline-4 outline-textColor  border-textColor border bg-opacity-0 bg-yellow-50"
            placeholder="Description..."
            value={description}
            onChange={(event) => {
              setDescription(event.target.value);
            }}
          ></textarea>
        </div>

        {/* Address  */}
        <div className="flex items-center justify-between space-x-32 mx-12">
          <h3 className="text-xl text-[#FEFFFF]">Address</h3>
          <Input
            placeholder="Type your address..."
            value={address}
            color=""
            size="w-96"
            type="text"
            onChangeHandler={(event) => {
              setAddress(event.target.value);
            }}
          />
        </div>

        {/* Map  */}
        <div className="flex items-start justify-between mx-12">
          <h3 className="text-xl text-[#FEFFFF]">Map</h3>
          <div className="w-96 h-60">
            <YMaps>
              <Map
                defaultState={defaultState}
                width="100%"
                height="100%"
                onClick={onMapClick}
              >
                <Placemark
                  onClick={() => {}}
                  geometry={[latitude, longitude]}
                />
                <ZoomControl />
                <RulerControl />
              </Map>
            </YMaps>
          </div>
        </div>

        {/* Pictures  */}
        <div className="flex items-center justify-between mx-12">
          <h3 className="text-xl text-[#FEFFFF]">Pictures</h3>
          {/* Кнопка показать картинки  */}
          <input
            onChange={(e) => handleSetImage(e)}
            type="file"
            multiple
            accept="image/png, image/jpeg"
            className="text-slate-500
            text-lg
            w-96
      file:mr-4 file:py-2 file:px-4
      file:rounded-md file:border-0
      file:bg-primary file:text-textColor
      file:bg-opacity-90
      hover:file:bg-opacity-100
      file:cursor-pointer
      duration-200
    "
          />
        </div>

        {/* Beds and People */}
        <div className="flex flex-col mx-12 space-y-12">
          <div className="flex items-center justify-between">
            <h1 className="text-textColor text-xl">Beds</h1>
            <div className="space-x-2">
              <button
                onClick={() => {
                  setBeds(1);
                }}
                className={`rounded-lg shadow-xl border  ${
                  beds === 1 ? "bg-primary border-primary" : "bg-neutral"
                } px-8 py-1 text-textColor text-lg hover:bg-primary hover:border-primary duration-200`}
              >
                1
              </button>
              <button
                onClick={() => {
                  setBeds(2);
                }}
                className={`rounded-lg shadow-xl border  ${
                  beds === 2 ? "bg-primary border-primary" : "bg-neutral"
                } px-7 py-1 text-textColor text-lg hover:bg-primary hover:border-primary duration-200`}
              >
                2
              </button>
              <button
                onClick={() => {
                  setBeds(3);
                }}
                className={`rounded-lg shadow-xl border  ${
                  beds === 3 ? "bg-primary border-primary" : "bg-neutral"
                } px-7 py-1 text-textColor text-lg hover:bg-primary hover:border-primary duration-200`}
              >
                3
              </button>
              <button
                onClick={() => {
                  setBeds(4);
                }}
                className={`rounded-lg shadow-xl border  ${
                  beds === 4 ? "bg-primary border-primary" : "bg-neutral"
                } px-7 py-1 text-textColor text-lg hover:bg-primary hover:border-primary duration-200`}
              >
                4
              </button>
              <button
                onClick={() => {
                  setBeds(5);
                }}
                className={`rounded-lg shadow-xl border  ${
                  beds === 5 ? "bg-primary border-primary" : "bg-neutral"
                } px-7 py-1 text-textColor text-lg hover:bg-primary hover:border-primary duration-200`}
              >
                5
              </button>
            </div>
          </div>

          {/* People Amount  */}
          <div className=" flex items-center justify-between">
            <h1 className="text-textColor text-xl">People</h1>
            <div className="space-x-2">
              <button
                onClick={() => {
                  setPeople(1);
                }}
                className={`rounded-lg shadow-xl border  ${
                  people === 1 ? "bg-primary border-primary" : "bg-neutral"
                } px-8 py-1 text-textColor text-lg hover:bg-primary hover:border-primary  duration-200`}
              >
                1
              </button>
              <button
                onClick={() => {
                  setPeople(2);
                }}
                className={`rounded-lg shadow-xl border  ${
                  people === 2 ? "bg-primary border-primary" : "bg-neutral"
                } px-7 py-1 text-textColor text-lg hover:bg-primary hover:border-primary  duration-200`}
              >
                2
              </button>
              <button
                onClick={() => {
                  setPeople(3);
                }}
                className={`rounded-lg shadow-xl border  ${
                  people === 3 ? "bg-primary border-primary" : "bg-neutral"
                } px-7 py-1 text-textColor text-lg hover:bg-primary hover:border-primary  duration-200`}
              >
                3
              </button>
              <button
                onClick={() => {
                  setPeople(4);
                }}
                className={`rounded-lg shadow-xl border  ${
                  people === 4 ? "bg-primary border-primary" : "bg-neutral"
                } px-7 py-1 text-textColor text-lg hover:bg-primary hover:border-primary  duration-200`}
              >
                4
              </button>
              <button
                onClick={() => {
                  setPeople(5);
                }}
                className={`rounded-lg shadow-xl border  ${
                  people === 5 ? "bg-primary border-primary" : "bg-neutral"
                } px-7 py-1 text-textColor text-lg hover:bg-primary hover:border-primary  duration-200`}
              >
                5
              </button>
            </div>
          </div>
        </div>
        {/* Show Variants Button  */}
        <div className="pt-8 px-12">
          <Button
            onClick={handleChangeRoom}
            border="none"
            width="full"
            height="fit"
            color="secondary"
            textColor="textColor"
          >
            ChangeRoom
          </Button>
        </div>
      </div>
    </div>
  );
};

export default ChangeRoom;
