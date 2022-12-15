import {
  YMaps,
  Map,
  Placemark,
  ZoomControl,
  RulerControl,
} from "@pbe/react-yandex-maps";

import FilterMain from "./FilterMain";
import { IRoom } from "../../redux/api/types";

interface CustomMapProps {
  data: Array<IRoom>;
}

const CustomMap: React.FC<CustomMapProps> = ({ data }) => {
  const defaultState = {
    center: [55.751574, 37.573856],
    zoom: 12,
  };

  //   placemark.options.set('iconImageHref', 'http:www.example.com/images/1.png');
  //   placemark.options.set('iconImageOffset', [-1, -30]);
  //   placemark.options.set('iconImageSize', [28,29]);
  // placemark.options.set('iconShadowImageHref', 'http:www.example.com/images/2.png');
  //   placemark.options.set('iconShadowImageOffset', [2, -12]);
  //   placemark.options.set('iconShadowImageSize', [29, 7]);

  return (
    <div className="w-full h-full">
      <YMaps>
        <Map defaultState={defaultState} width="full" height="100%">
          {data.map((item: IRoom) => (
            <Placemark
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
