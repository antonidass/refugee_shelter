import { IRoom } from "../redux/api/types";
import RoomsListItem from "./RoomsListItem";

interface MainListProps {
  data: Array<IRoom>;
}

const MainList: React.FC<MainListProps> = ({ data }) => {
  return (
    <div className="py-20 bg-neutral">
      <div className="grid grid-cols-3 gap-12 bg-neutral max-w-7xl mx-auto">
        {data.map((item: IRoom) => (
          <RoomsListItem key={item.id} roomItem={item} />
        ))}
      </div>
    </div>
  );
};

export default MainList;
