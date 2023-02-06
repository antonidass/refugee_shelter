import { Route, Routes } from "react-router-dom";
import RequireUser from "./components/RequireUser";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Item from "./pages/Item";
import Profile from "./pages/Profile";
import ProfileChange from "./pages/ChangeProfile";
import MyRooms from "./pages/MyRooms";
import MyRoomItem from "./pages/MyRoomItem";
import AddRoom from "./pages/AddRoom";
import ChangeRoom from "./pages/ChangeRoom";
import MyReservations from "./pages/MyReservations";
import ChangeReservation from "./pages/ChangeReservation";

function App() {
  return (
    <div className="flex flex-col h-screen bg-neutral">
      <Navbar />

      <ToastContainer />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />
        <Route path="items/:id" element={<Item />} />

        <Route element={<RequireUser allowedRoles={["ROLE_ADMIN"]} />}>
          <Route path="admin" element={<Login />} />
        </Route>
        <Route element={<RequireUser allowedRoles={["ROLE_USER"]} />}>
          <Route path="profile" element={<Profile />} />
        </Route>

        <Route element={<RequireUser allowedRoles={["ROLE_USER"]} />}>
          <Route path="profile/change" element={<ProfileChange />} />
        </Route>

        <Route element={<RequireUser allowedRoles={["ROLE_USER"]} />}>
          <Route element={<MyRooms />} path="profile/rooms" />
        </Route>

        <Route element={<RequireUser allowedRoles={["ROLE_USER"]} />}>
          <Route element={<ChangeRoom />} path="profile/rooms/:id/change" />
        </Route>

        <Route element={<RequireUser allowedRoles={["ROLE_USER"]} />}>
          <Route element={<MyRoomItem />} path="profile/rooms/:id" />
        </Route>

        <Route element={<RequireUser allowedRoles={["ROLE_USER"]} />}>
          <Route element={<AddRoom />} path="profile/rooms/add" />
        </Route>

        <Route element={<RequireUser allowedRoles={["ROLE_USER"]} />}>
          <Route element={<MyReservations />} path="profile/reservations" />
        </Route>

        <Route element={<RequireUser allowedRoles={["ROLE_USER"]} />}>
          <Route
            element={<ChangeReservation />}
            path="profile/rooms/:roomId/reservations/:resId/update"
          />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
