import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import moment from "moment";
import {
  deleteEmployee,
  getEmployeeByUsername,
  getOpentalkByEmployeeId,
} from "../utils/ApiFunction";

const Profile = () => {
  const [user, setUser] = useState({
    id: "",
    email: "",
    username: "",
    fullName: "",
    companyBranchName: "",
    roles: [],
  });

  const [opentalks, setOpentalks] = useState([
    {
      topic: "",
      startTime: "",
      endTime: "",
      linkMeeting: "",
      host: {
        id: "",
        name: "",
      },
      companyBranchName: "",
      participants: [],
    },
  ]);

  const [message, setMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const userId = localStorage.getItem("userId");
  const employeeId = localStorage.getItem("employeeId");

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const userData = await getEmployeeByUsername(userId);
        setUser(userData);
        console.log(userData);
      } catch (error) {
        console.error(error);
      }
    };

    fetchUser();
  }, [userId]);

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await getOpentalkByEmployeeId(employeeId);
        console.log(response);
        setOpentalks(response.content);
      } catch (error) {
        console.error("Error fetching bookings:", error.message);
        setErrorMessage(error.message);
      }
    };

    fetchBookings();
  }, [employeeId]);

  const handleDeleteAccount = async () => {
    const confirmed = window.confirm(
      "Are you sure you want to delete your account? This action cannot be undone."
    );
    if (confirmed) {
      await deleteEmployee(userId)
        .then((response) => {
          setMessage(response.data);
          localStorage.removeItem("token");
          localStorage.removeItem("userId");
          localStorage.removeItem("userRole");
          navigate("/");
          window.location.reload();
        })
        .catch((error) => {
          setErrorMessage(error.data);
        });
    }
  };

  return (
    <div className="container">
      {errorMessage && <p className="text-danger">{errorMessage}</p>}
      {message && <p className="text-danger">{message}</p>}
      {user ? (
        <div
          className="card p-5 mt-5"
          style={{ backgroundColor: "whitesmoke" }}
        >
          <h4 className="card-title text-center">User Information</h4>
          <div className="card-body">
            <div className="col-md-10 mx-auto">
              <div className="card mb-3 shadow">
                <div className="row g-0">
                  <div className="col-md-2">
                    <div className="d-flex justify-content-center align-items-center mb-4">
                      <img
                        src="https://themindfulaimanifesto.org/wp-content/uploads/2020/09/male-placeholder-image.jpeg"
                        alt="Profile"
                        className="rounded-circle"
                        style={{
                          width: "150px",
                          height: "150px",
                          objectFit: "cover",
                        }}
                      />
                    </div>
                  </div>

                  <div className="col-md-10">
                    <div className="card-body">
                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">
                          ID:
                        </label>
                        <div className="col-md-10">
                          <p className="card-text">{user.id}</p>
                        </div>
                      </div>
                      <hr />

                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">
                          Full Name:
                        </label>
                        <div className="col-md-10">
                          <p className="card-text">{user.fullName}</p>
                        </div>
                      </div>
                      <hr />

                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">
                          User Name:
                        </label>
                        <div className="col-md-10">
                          <p className="card-text">{user.username}</p>
                        </div>
                      </div>
                      <hr />

                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">
                          Email:
                        </label>
                        <div className="col-md-10">
                          <p className="card-text">{user.email}</p>
                        </div>
                      </div>
                      <hr />

                      <div className="form-group row">
                        <label className="col-md-2 col-form-label fw-bold">
                          Roles:
                        </label>
                        <div className="col-md-10">
                          <ul className="list-unstyled">
                            {user?.roles?.map((role, index) => (
                              <li key={index} className="card-text">
                                {role}
                              </li>
                            ))}
                          </ul>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <h4 className="card-title text-center">Booking History</h4>

              {opentalks.length > 0 ? (
                <table className="table table-bordered table-hover shadow">
                  <thead>
                    <tr>
                      <th scope="col">Open talk ID</th>
                      <th scope="col">Topic</th>
                      <th scope="col">Host Name</th>
                      <th scope="col">Start Time</th>
                      <th scope="col">End Time</th>
                      <th scope="col">Link Meeting</th>
                    </tr>
                  </thead>
                  <tbody>
                    {opentalks.map((opentalk, index) => (
                      <tr key={index}>
                        <td>{opentalk.id}</td>
                        <td>{opentalk.topic}</td>
                        <td>{opentalk.host.name}</td>
                        <td>{opentalk.startTime}</td>
                        <td>{opentalk.endTime}</td>
                        <td>{opentalk.linkMeeting}</td>
                        <td
                          className={
                            moment(opentalk.startTime).isAfter(moment())
                              ? "text-success"
                              : ""
                          }
                        >
                          {moment(opentalk.startTime).isAfter(moment())
                            ? "On-going"
                            : "Already happened"}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              ) : (
                <p>You have not made any open talk yet.</p>
              )}

              <div className="d-flex justify-content-center">
                <div className="mx-2">
                  <button
                    className="btn btn-danger btn-sm"
                    onClick={handleDeleteAccount}
                  >
                    Close account
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      ) : (
        <p>Loading user data...</p>
      )}
    </div>
  );
};

export default Profile;
