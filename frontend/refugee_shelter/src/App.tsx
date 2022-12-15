import { Route, Routes } from "react-router-dom";
import RequireUser from "./components/RequireUser";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Button from "./components/layout/Button";
import Input from "./components/layout/Input";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Register from "./pages/Register";
import Login from "./pages/Login";

function App() {
  const onChangeHandler = (text: string) => {
    console.log(text);
  };

  return (
    <div className="h-screen flex flex-col bg-neutral">
      <Navbar />

      {/* <Route element={<RequireUser allowedRoles={["user", "admin"]} />}> */}
      <ToastContainer />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="login" element={<Login />} />
        <Route path="register" element={<Register />} />

        <Route element={<RequireUser allowedRoles={["ROLE_ADMIN"]} />}>
          <Route path="admin" element={<Login />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
