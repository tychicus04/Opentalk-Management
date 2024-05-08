import React from "react";
import MainHeader from "../layout/MainHeader";
// import EmployeeSearch from "../common/EmployeeSearch";
import { useLocation } from "react-router-dom";

const Home = () => {
  const location = useLocation();
  const message = location.state && location.state.message;
  const currentUser = localStorage.getItem("userId");

  return (
    <section>
      {message && <p className="text-warning px-5">{message}</p>}
      {currentUser && (
        <h6 className="text-success text-center">
          You are logged in as {currentUser}
        </h6>
      )}

      <MainHeader />

      <div className="container">
        {/* <EmployeeSearch /> */}
        {/* <RoomCarousel />
        <Parallax />
        <RoomCarousel />
        <HotelService />
        <Parallax />
        <RoomCarousel /> */}
      </div>
    </section>
  );
};

export default Home;
