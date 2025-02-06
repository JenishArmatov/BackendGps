import React from "react";
import "./App.css";
import { Route, Routes, useLocation, Navigate } from "react-router-dom";
import Form from "./components/LoginForm/Form";
import Sidebar from "./components/Sidebar/Sidebar";
import Home from "./Pages/Home/Home";
import PrivateRoute from "./components/PrivateRoute";


function App() {
  const location = useLocation();
  const hideSidebar = location.pathname === "/login";

  return (
    <div style={{ display: "flex" }}>
      {!hideSidebar && <Sidebar />}
      <main style={{ flexGrow: 1 }}>
        <Routes>
            <Route path="/login" element={<Form />} />

          <Route element={<PrivateRoute />}>
            <Route path="/" element={<Home />} />
          </Route>

          <Route path="*" element={<Navigate to="/login" />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
