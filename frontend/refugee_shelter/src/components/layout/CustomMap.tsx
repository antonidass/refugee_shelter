import {
  YMaps,
  Map,
  Placemark,
  ZoomControl,
  RulerControl,
} from "@pbe/react-yandex-maps";

import FilterMain from "./FilterMain";
import { VscListFlat } from "react-icons/vsc";

const CustomMap: React.FC<{}> = () => {
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
    <div className="w-full h-full relative">
      <YMaps>
        <Map defaultState={defaultState} width="full" height="100%">
          <Placemark
            geometry={[55.684758, 37.738521]}
            options={{
              iconLayout: "default#image",
              iconImageHref: "assets/placemark.png",
              iconImageSize: [28, 42],
              iconImageOffset: [-3, -42],
            }}
          />
          <Placemark
            geometry={[55.684758, 37.938521]}
            options={{
              iconLayout: "default#image",
              iconImageHref: "assets/placemark.png",
              iconImageSize: [28, 42],
              iconImageOffset: [-3, -42],
            }}
          />
          <Placemark
            geometry={[55.584758, 37.638521]}
            options={{
              iconLayout: "default#image",
              iconImageHref: "assets/placemark.png",
              iconImageSize: [28, 42],
              iconImageOffset: [-3, -42],
            }}
          />
          <ZoomControl />
          <RulerControl />
        </Map>
      </YMaps>
      <div className="absolute left-0 top-0 py-4 px-8">
        <FilterMain />
      </div>
      <div className="absolute inset-x-0 bottom-0 mx-auto mb-10 bg-primary rounded-xl w-60 flex items-center justify-center px-6 space-x-8 py-2">
        <h1 className="text-2xl text-[#FEFFFF]">Show List</h1>
        <VscListFlat size={32} color="#FEFFFF" />
      </div>
    </div>
  );
};

export default CustomMap;
