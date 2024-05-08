import React, { useState } from "react";
import { Link } from "react-router-dom";
import { FaPlus } from "react-icons/fa";
import { syncAccount, syncEmployee } from "../utils/ApiFunction";
import { Button, Col, Row } from "react-bootstrap";

const ExistingUsers = () => {
  const [users, setUsers] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  //   useEffect(() => {
  //     fetchUsers();
  //   }, []);

  const fetchUsers = async () => {
    setIsLoading(true);
    try {
      const result = await syncEmployee();
      console.log(result);
      setUsers(result);
      setIsLoading(false);
    } catch (error) {
      console.log("1");
      setErrorMessage(error.message);
    }
  };

  const handleSyncEmployee = async () => {
    try {
      const result = await syncAccount();
      console.log(result);
      setIsLoading(false);
      fetchUsers();
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  const usersPerPage = 10;
  const indexOfLastUser = currentPage * usersPerPage;
  const indexOfFirstUser = indexOfLastUser - usersPerPage;
  const currentUsers = users.slice(indexOfFirstUser, indexOfLastUser);

  return (
    <>
      <div className="container col-md-8 col-lg-6">
        {successMessage && (
          <p className="alert alert-success mt-5" role="alert">
            {successMessage}
          </p>
        )}
        {errorMessage && (
          <p className="alert alert-danger mt-5" role="alert">
            {errorMessage}
          </p>
        )}
      </div>
      {isLoading ? (
        <p>Loading existing users</p>
      ) : (
        <>
          <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-between mb-3 mt-5">
              <h2>Existing Users</h2>
            </div>
            <Row>
              <Col md={6} className="mb-2 md-mb-0">
                <Button onClick={handleSyncEmployee}>
                  Sync Data From HRM Tool
                </Button>
              </Col>
            </Row>

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">
                  <th className="">FirstName</th>
                  <th className="">LastName</th>
                  <th className="">Email</th>
                  <th className="">Actions</th>
                </tr>
              </thead>

              <tbody>
                {currentUsers.map((user, index) => {
                  console.log(user); // Add this line to log the user object
                  return (
                    <tr key={index} className="text-center">
                      <td>{user.firstName}</td>
                      <td>{user.lastName}</td>
                      <td>{user.email}</td>
                      <td className="gap-2">
                        <Link
                          to={{
                            pathname: "/add-employee-from-user",
                          }}
                          state={{
                            user: user,
                          }}
                        >
                          <span className="btn btn-info btn-sm">
                            <FaPlus />
                          </span>
                        </Link>
                      </td>
                    </tr>
                  );
                })}
              </tbody>
            </table>
            <button
              onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 1))}
            >
              Previous
            </button>
            <button
              onClick={() =>
                setCurrentPage((prev) =>
                  Math.min(prev + 1, Math.ceil(users.length / usersPerPage))
                )
              }
            >
              Next
            </button>
          </section>
        </>
      )}
    </>
  );
};

export default ExistingUsers;
