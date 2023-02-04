export interface IUser {
  id: number;
  name: string;
  email: string;
  phone: string;
}

export interface IRoom {
  id: number;
  name: string;
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

export interface IResevations {
  id: number;
  startDate: number;
  endDate: number;
  rooms: IRoom;
  user: IUser;
}

export interface IResevationsRequest {
  startDate: number;
  endDate: number;
  roomId: number;
}

export interface IRoomRequest {
  name: string;
  address: string;
  latitude: number;
  longitude: number;
  price: number;
  imageUrl: string;
  beds: number;
  people: number;
  description: string;
}

export interface IChangeUser {
  name: string;
  email: string;
  password: string;
}
