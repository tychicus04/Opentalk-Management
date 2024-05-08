import React, { useState } from "react";
import { addEmployee } from "../utils/ApiFunction";
import CompanyBranchSelector from "../common/CompanyBranchSelector";
import { Link } from "react-router-dom";
import { useLocation } from "react-router-dom";

const AddEmployeeFromUser = () => {
  const location = useLocation();
  const user = location.state ? location.state.user : null;
  const fullName = `${user?.lastName} ${user?.firstName}`;
  const [newEmployee, setNewEmployee] = useState({
    email: user?.email,
    username: "",
    fullName: fullName,
    password: "",
    companyBranchName: "",
    rolesId: [2],
    enabled: true,
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleEmployeeInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;
    setNewEmployee({ ...newEmployee, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(newEmployee);

    try {
      const success = await addEmployee(newEmployee);
      if (success !== undefined) {
        setSuccessMessage("A new employee was added to the database");
        setNewEmployee({
          email: user?.email,
          username: "",
          fullName: fullName,
          password: "",
          companyBranchName: "",
          rolesId: [2],
          enabled: true,
        });

        setErrorMessage("");
      } else {
        setErrorMessage("Error adding employee");
      }
    } catch (error) {
      setErrorMessage(error.message);
    }

    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <>
      <section className="container, mt-5 mb-5">
        <div className="row justify-content-center">
          <div className="col-md-8 col-lg-6">
            <h2 className="mt-5 mb-2">Add a New Employee</h2>

            {successMessage && (
              <div className="alert alert-success fade show">
                {successMessage}
              </div>
            )}

            {errorMessage && (
              <div className="alert alert-danger fade show">{errorMessage}</div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label htmlFor="companyBranch" className="form-label">
                  Company Branch
                </label>
                <CompanyBranchSelector
                  handleEmployeeInputChange={handleEmployeeInputChange}
                  newEmployee={newEmployee}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="username" className="form-label">
                  Username
                </label>
                <input
                  className="form-control"
                  required
                  id="username"
                  type="text"
                  name="username"
                  value={newEmployee.username}
                  onChange={handleEmployeeInputChange}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="fullName" className="form-label">
                  FullName
                </label>
                <input
                  className="form-control"
                  required
                  id="fullName"
                  type="text"
                  name="fullName"
                  value={newEmployee.fullName}
                  onChange={handleEmployeeInputChange}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="email" className="form-label">
                  Email
                </label>
                <input
                  className="form-control"
                  required
                  id="email"
                  type="text"
                  name="email"
                  value={newEmployee.email}
                  onChange={handleEmployeeInputChange}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="password" className="form-label">
                  Password
                </label>
                <input
                  className="form-control"
                  required
                  id="password"
                  type="text"
                  name="password"
                  value={newEmployee.password}
                  onChange={handleEmployeeInputChange}
                />
              </div>

              <div className="d-grid d-md-flex mt-2 ">
                <Link to={"/sync-user"} className="btn btn-outline-info">
                  Back
                </Link>
                <button className="btn btn-outline-primary ms-3">
                  Save Employee
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
};

export default AddEmployeeFromUser;
