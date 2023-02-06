import { IResevations, IRoom } from "../redux/api/types";
import ReservationListItem from "./ReservationListItem";

interface RoomsListItemProps {
  data: Array<IResevations>;
}

const ReservationsList: React.FC<RoomsListItemProps> = ({ data }) => {
  return (
    <div className="py-20 bg-neutral">
      <div className="flex flex-col bg-neutral max-w-7xl mx-auto space-y-8">
        {data.map((item: IResevations) => (
          <ReservationListItem key={item.id} resItem={item} />
        ))}
      </div>
    </div>
  );
};
export default ReservationsList;
