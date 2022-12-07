import ProfilePage from "./pages/Profile.page";
import LoginPage from "./pages/Login";
import { Route, Routes } from "react-router-dom";
import RegisterPage from "./pages/Register.page";
import RequireUser from "./components/RequireUser";
import { ToastContainer } from "react-toastify";
import Button from "./components/layout/Button";
import Input from "./components/layout/Input";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";

function App() {
  const onChangeHandler = (text: string) => {
    console.log(text);
  };

  return (
    <div className="h-screen flex flex-col bg-neutral">
      <Navbar />
      {/* <FiltersBar /> */}

      {/* <Route element={<RequireUser allowedRoles={["user", "admin"]} />}> */}
      <ToastContainer />
      <Routes>
        <Route path="home" element={<Home />} />
        <Route path="login" element={<LoginPage />} />
        <Route path="register" element={<RegisterPage />} />
        <Route
          element={<RequireUser allowedRoles={["ROLE_USER", "ROLE_ADMIN"]} />}
        >
          <Route path="profile" element={<ProfilePage />} />
        </Route>
        <Route element={<RequireUser allowedRoles={["ROLE_ADMIN"]} />}>
          <Route path="admin" element={<LoginPage />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
