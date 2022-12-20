export interface IUser {
  id: number;
  name: string;
  email: string;
  phone: string;
}

export interface IRoom {
  id: number;
  address: string;
  latitude: number;
  longitude: number;
  price: number;
  imageUrl: string;
  beds: number;
  people: number;
  description: string;
  user: IUser;
}
