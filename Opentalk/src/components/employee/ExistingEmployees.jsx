/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import { deleteEmployee, getAllEmployees } from "../utils/ApiFunction";
import { Col, Row } from "react-bootstrap";
import { FaEdit, FaPlus, FaTrashAlt } from "react-icons/fa";
import { Link } from "react-router-dom";
import EmployeePaginator from "../common/EmployeePaginator";
// import EmployeeFilter from "../common/EmployeeFilter";
import CompanyBranchSelector from "../common/CompanyBranchSelector";

const ExistingEmployees = () => {
  const [employees, setEmployees] = useState([]);
  const [branch, setBranch] = useState("");
  const [currentPage, setCurrentPage] = useState(0);
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    fetchEmployees();
  }, [currentPage, branch]);

  const fetchEmployees = async () => {
    setIsLoading(true);
    try {
      const result = await getAllEmployees(currentPage, branch);
      setEmployees(result);
      setIsLoading(false);
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  // Now you can use currentemployees as intended
  const handlePaginationClick = (number) => {
    setCurrentPage(number);
  };

  const handleInputChange = (e) => {
    setBranch(e.target.value);
  };

  const handleDeleteEmployeeById = async (employeeId) => {
    const result = await deleteEmployee(employeeId);
    try {
      if (result === "") {
        setSuccessMessage(`employee no ${employeeId} was deleted`);
        fetchEmployees();
      }
    } catch (error) {
      setErrorMessage(`Error deleting employee: ${result.message}`);
    }
    setTimeout(() => {
      setErrorMessage("");
      setSuccessMessage("");
    }, 3000);
  };

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
        <p>Loading existing employees</p>
      ) : (
        <>
          <section className="mt-5 mb-5 container">
            <div className="d-flex justify-content-between mb-3 mt-5">
              <h2>Existing employees</h2>
            </div>
            <Row>
              <Col md={8} className="mb-2 md-mb-0">
                <CompanyBranchSelector
                  handleEmployeeInputChange={handleInputChange}
                  newEmployee={employees}
                />
              </Col>

              <Col md={4} className="d-flex  justify-content-end">
                <Link to={"/add-employee"}>
                  <FaPlus /> Add employee
                </Link>
              </Col>
            </Row>

            <table className="table table-bordered table-hover">
              <thead>
                <tr className="text-center">
                  <th className="">ID</th>
                  <th className="">Username</th>
                  <th className="">Email</th>
                  <th className="">FullName</th>
                  <th className="">Company Branch</th>
                  <th className="">Role</th>
                  <th className="">Actions</th>
                </tr>
              </thead>

              <tbody>
                {employees?.content?.map((employee) => (
                  <tr key={employee.id} className="text-center">
                    <td>{employee.id}</td>
                    <td>{employee.username}</td>
                    <td>{employee.email}</td>
                    <td>{employee.fullName}</td>
                    <td>{employee.companyBranch}</td>
                    <td>
                      {employee?.roles?.map((role, index) => (
                        <li key={index} className="card-text">
                          {role}
                        </li>
                      ))}
                    </td>
                    <td className="gap-2">
                      <Link to={`/edit-employee/${employee.id}`}>
                        <span className="btn btn-warning btn-sm">
                          <FaEdit />
                        </span>
                      </Link>

                      <button
                        className="btn btn-danger btn-sm"
                        onClick={() => {
                          handleDeleteEmployeeById(employee.id);
                        }}
                      >
                        <FaTrashAlt />
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
            <EmployeePaginator
              currentPage={employees?.pageable?.pageNumber}
              totalPages={employees?.totalPages}
              onPageChange={handlePaginationClick}
            />
          </section>
        </>
      )}
    </>
  );
};

export default ExistingEmployees;
