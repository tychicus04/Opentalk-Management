import React, { useState } from "react";
import { updateEmployee } from "../utils/ApiFunction";
import { useParams } from "react-router-dom";

import { Link } from "react-router-dom";
import CompanyBranchSelector from "../common/CompanyBranchSelector";

const EditEmployee = () => {
  const { employeeId } = useParams();
  const [employee, setEmployee] = useState({
    id: "${employeeId}",
    email: "",
    username: "",
    fullName: "",
    password: "",
    companyBranchName: "",
    rolesId: [2],
    enabled: true,
  });
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleInputChange = (e) => {
    const { name, value } = e.target;

    setEmployee({ ...employee, [name]: value, id: employeeId });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      setEmployee({ ...employee, id: employeeId });
      const response = await updateEmployee(employee);

      if (response !== undefined) {
        setSuccessMessage("Employee updated successfully");
        setEmployee({
          email: "",
          username: "",
          fullName: "",
          password: "",
          companyBranchName: "",
          rolesId: [2],
          enabled: true,
        });
        setErrorMessage("");
      } else {
        setErrorMessage("Error updating Employee");
      }
    } catch (error) {
      console.log(error);
      setErrorMessage(error.message);
    }

    setTimeout(() => {
      setSuccessMessage("");
      setErrorMessage("");
    }, 3000);
  };

  return (
    <div className="container  mt-5 mb-5">
      <h3 className="text-center mb-5  mt-5">Update Employee</h3>
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          {successMessage && (
            <div className="alert alert-success" role="alert">
              {successMessage}
            </div>
          )}
          {errorMessage && (
            <div className="alert alert-danger" role="alert">
              {errorMessage}
            </div>
          )}
          <form onSubmit={handleSubmit}>
            <div className="mb-3">
              <label htmlFor="id" className="form-label main-color">
                Employee Id
              </label>
              <input
                className="form-control"
                id="id"
                type="number"
                name="id"
                value={employeeId}
                readOnly
              />
            </div>

            <div className="mb-3">
              <label htmlFor="companyBranch" className="form-label">
                Company Branch
              </label>
              <CompanyBranchSelector
                handleEmployeeInputChange={handleInputChange}
                newEmployee={employee}
              />
            </div>
            <div className="mb-3">
              <label htmlFor="username" className="form-label main-color">
                Username
              </label>
              <input
                className="form-control"
                id="username"
                type="text"
                name="username"
                value={employee.username}
                onChange={handleInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="fullName" className="form-label main-color">
                Fullname
              </label>
              <input
                className="form-control"
                id="fullName"
                type="text"
                name="fullName"
                value={employee.fullName}
                onChange={handleInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="email" className="form-label main-color">
                Email
              </label>
              <input
                className="form-control"
                id="email"
                type="text"
                name="email"
                value={employee.email}
                onChange={handleInputChange}
              />
            </div>

            <div className="mb-3">
              <label htmlFor="password" className="form-label main-color">
                Password
              </label>
              <input
                className="form-control"
                id="password"
                type="text"
                name="password"
                value={employee.password}
                onChange={handleInputChange}
              />
            </div>

            <div className="d-grid d-md-flex mt-2">
              <Link
                to={"/existing-employees"}
                className="btn btn-outline-info ml-5"
              >
                Back
              </Link>
              <button type="submit" className="btn btn-outline-warning">
                Edit Employee
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>
  );
};

export default EditEmployee;
