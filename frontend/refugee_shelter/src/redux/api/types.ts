export interface IIUser {
  id: number;
  name: string;
  email: string;
  phone: string;
}

export interface IUser {
  id: number;
  address: string;
  latitude: number;
  longitude: number;
  price: number;
  imageUrl: string;
  hasKitchen: boolean;
  hasBathroom: boolean;
  description: string;
  user: IIUser;
}

export interface IGenericResponse {
  status: string;
  message: string;
}
