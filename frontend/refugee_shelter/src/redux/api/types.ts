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
  hasKitchen: boolean;
  hasBathroom: boolean;
  description: string;
  user: IUser;
}
