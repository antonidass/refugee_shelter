import {
  YMaps,
  Map,
  Placemark,
  ZoomControl,
  RulerControl,
} from "@pbe/react-yandex-maps";
import { IRoom } from "../../redux/api/types";
import { useNavigate } from "react-router-dom";

interface CustomMapProps {
  data: Array<IRoom>;
}

const CustomMap: React.FC<CustomMapProps> = ({ data }) => {
  const defaultState = {
    center: [55.751574, 37.573856],
    zoom: 12,
  };

  let navigate = useNavigate();

  return (
    <div className="w-full h-full">
      <YMaps>
        <Map defaultState={defaultState} width="full" height="100%">
          {data.map((item: IRoom) => (
            <Placemark
              key={item.id}
              onClick={() => {
                navigate(`/items/${item.id}`);
              }}
              geometry={[item.latitude, item.longitude]}
              options={{
                iconLayout: "default#image",
                iconImageHref: "assets/placemark.png",
                iconImageSize: [28, 42],
                iconImageOffset: [-3, -42],
              }}
            />
          ))}
          <ZoomControl />
          <RulerControl />
        </Map>
      </YMaps>
    </div>
  );
};

export default CustomMap;
