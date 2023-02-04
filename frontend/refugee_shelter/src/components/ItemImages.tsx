import { useState, useEffect } from "react";

interface ItemImagesProps {
  id: string | undefined;
}

const ItemImages: React.FC<ItemImagesProps> = ({ id }) => {
  const [imges, setImg] = useState<string[]>([]);

  // Получение списка картинок -> в сервис
  const fetchImages = async () => {
    for (let i = 1; i < 5; i++) {
      console.log("HERE");
      const res = await fetch(
        "http://localhost:8081/api/v1/image/" + id + "_" + i
      );
      if (res.status === 400) {
      } else {
        const imageBlob = await res.blob();
        const imageObjectURL = URL.createObjectURL(imageBlob);
        setImg((state) => {
          return [...state, imageObjectURL];
        });
      }
    }
  };

  useEffect(() => {
    fetchImages();
  }, []);

  return (
    <>
      <div className="carousel rounded-2xl h-80 ">
        {imges.map((img, img_index) => (
          <div
            id={`slide${img_index}`}
            className="carousel-item relative w-full"
          >
            <img src={imges[img_index]} className="w-full" />
            <div className="absolute flex justify-between transform -translate-y-1/2 left-5 right-5 top-1/2">
              <a
                href={`#slide${
                  img_index === 0 ? imges.length - 1 : img_index - 1
                }`}
                className="btn btn-circle"
              >
                ❮
              </a>
              <a
                href={`#slide${
                  img_index === imges.length - 1 ? 0 : img_index + 1
                }`}
                className="btn btn-circle"
              >
                ❯
              </a>
            </div>
          </div>
        ))}
      </div>
    </>
  );
};

export default ItemImages;
