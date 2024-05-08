/* eslint-disable no-unused-vars */
import React, { useEffect, useState } from "react";
import EmployeeCard from "./OpentalkCard";
import { Col, Container, Row } from "react-bootstrap";
import EmployeePaginator from "../common/EmployeePaginator";
import { getAllEmployees, getAllOpenTalks } from "../utils/ApiFunction";
import EmployeeFilter from "../common/EmployeeFilter";
import OpentalkCard from "./OpentalkCard";

const Opentalk = () => {
  const [data, setData] = useState([]);
  const [error, setError] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [currentPage, setCurrentPage] = useState(1);
  const [employeesPerPage, setEmployeesPerPage] = useState(6);
  const [filteredData, setFilteredData] = useState([{ id: "" }]);

  useEffect(() => {
    setIsLoading(true);
    getAllOpenTalks()
      .then((data) => {
        setData(data);
        setFilteredData(data);
        setIsLoading(false);
      })
      .catch((error) => {
        setError(error.message);
        setIsLoading(false);
      });
  }, []);
  if (isLoading) {
    return <div>Loading Employees.....</div>;
  }
  if (error) {
    return <div className="text-danger">Error: {error}</div>;
  }

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const totalPages = Math.ceil(filteredData.length / employeesPerPage);

  const renderEmployees = () => {
    const startIndex = (currentPage - 1) * employeesPerPage;
    const endIndex = startIndex + employeesPerPage;
    return filteredData.slice(startIndex, endIndex).map((opentalk) => {
      return <OpentalkCard key={opentalk.id} opentalk={opentalk} />;
    });
  };

  return (
    <Container>
      <Row>
        {/* <Col md={6} className="mb-3 mb-md-0">
          <EmployeeFilter data={data} setFilteredData={setFilteredData} />
        </Col>
        <Col md={6} className="d-flex align-items-center justify-content-end">
          <EmployeePaginator
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={handlePageChange}
          />
        </Col> */}
      </Row>

      <Row>{renderEmployees()}</Row>

      <Row>
        {/* <Col md={6} className="d-flex align-items-center justify-content-end">
          <EmployeePaginator
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={handlePageChange}
          />
        </Col> */}
      </Row>
    </Container>
  );
};

export default Opentalk;
