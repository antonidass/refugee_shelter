import FilterMain from "./FilterMain";

const FiltersBar: React.FC<{}> = () => {
  return (
    <div className="flex justify-left items-center bg-neutral mt-1 shadow-inner py-4 px-8">
      <FilterMain />
    </div>
  );
};

export default FiltersBar;
