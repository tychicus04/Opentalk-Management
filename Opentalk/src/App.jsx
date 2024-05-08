/* eslint-disable no-undef */
/* eslint-disable no-unused-vars */
import React from "react";
import "../node_modules/bootstrap/dist/css/bootstrap.min.css";
import "/node_modules/bootstrap/dist/js/bootstrap.min.js";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./components/home/Home";
import NavBar from "./components/layout/NavBar";
import Footer from "./components/layout/Footer";
import Admin from "./components/admin/Admin";
import Checkout from "./components/opentalks/Checkout";
import BookingSuccess from "./components/opentalks/BookingSuccess";
import Login from "./components/auth/Login";
import { AuthProvider } from "./components/auth/AuthProvider";
import RequireAuth from "./components/auth/RequireAuth";
import AddEmployee from "./components/employee/AddEmployee";
import ExistingEmployees from "./components/employee/ExistingEmployees";
import Sidebar from "./components/layout/Sidebar";
import EditEmployee from "./components/employee/EditEmployee";
import ExistingBranch from "./components/companyBranch/ExistingBranch";
import AddBranch from "./components/companyBranch/AddBranch";
import Opentalks from "./components/opentalks/Opentalks";
import AddOpentalk from "./components/opentalks/AddOpentalk";
import OpentalkListing from "./components/opentalks/OpentalkListing";
import CreateOpenTalk from "./components/opentalks/CreateOpenTalk";
import ExistingUsers from "./components/user/ExistingUsers";
import AddEmployeeFromUser from "./components/user/AddEmployeeFromUser";
import Profile from "./components/auth/Profile";

function App() {
  localStorage.removeItem("token");
  localStorage.removeItem("userId");
  localStorage.removeItem("userRole");
  return (
    <AuthProvider>
      <main>
        <Router>
          <NavBar />
          <Routes>
            <Route path="/" element={<Home />} />
            <Route
              path="/edit-employee/:employeeId"
              element={<EditEmployee />}
            />
            <Route path="/existing-employees" element={<ExistingEmployees />} />
            <Route path="/add-employee" element={<AddEmployee />} />
            <Route path="/existing-branch" element={<ExistingBranch />} />
            <Route path="/add-branch" element={<AddBranch />} />
            <Route
              path="/interested-in-opentalk/:opentalkId"
              element={
                // <RequireAuth>
                <Checkout />
                // </RequireAuth>
              }
            />
            <Route path="/browse-all-opentalks" element={<OpentalkListing />} />
            <Route path="/add-opentalk" element={<AddOpentalk />} />

            <Route
              path="/create-opentalk"
              element={
                <RequireAuth>
                  <CreateOpenTalk />
                </RequireAuth>
              }
            />
            <Route path="/sync-user" element={<ExistingUsers />} />
            <Route
              path="/add-employee-from-user"
              element={<AddEmployeeFromUser />}
            />
            <Route path="/admin" element={<Admin />} />
            <Route path="/booking-success" element={<BookingSuccess />} />
            <Route path="/existing-opentalks" element={<Opentalks />} />
            {/* <Route path="/find-booking" element={<FindBooking />} /> */}
            <Route path="/login" element={<Login />} />
            <Route path="/profile" element={<Profile />} />
            <Route path="/logout" element={<Login />} />
          </Routes>
        </Router>
        <Footer />
      </main>
    </AuthProvider>
  );
}

export default App;
