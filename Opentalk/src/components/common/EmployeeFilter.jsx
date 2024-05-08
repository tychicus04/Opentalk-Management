import React, { useState } from "react";

const EmployeeFilter = ({ data, setFilteredData }) => {
  const [filter, setFilter] = useState("");

  const handleSelectChange = (e) => {
    const selectedCompanyBranch = e.target.value;
    setFilter(selectedCompanyBranch);
    const filteredEmployees = data.filter((employee) =>
      employee.companyBranch
        .toLowerCase()
        .includes(selectedCompanyBranch.toLowerCase())
    );
    setFilteredData(filteredEmployees);
  };

  const clearFilter = () => {
    setFilter("");
    setFilteredData(data);
  };

  //   const EmployeeTypes = ["", ...new Set(data.map((Employee) => Employee.EmployeeType))];
  const companyBranches = [
    "",
    ...new Set(
      Array.isArray(data) ? data.map((employee) => employee.companyBranch) : []
    ),
  ];

  return (
    <div className="input-group mb-3">
      <span className="input-group-text" id="company-branch-filter">
        Filter Employees by company Branch
      </span>
      <select
        name=""
        id=""
        className="form-select"
        value={filter}
        onChange={handleSelectChange}
      >
        <option value={""}>select a Branch to filter.....</option>
        {companyBranches.map((type, index) => (
          <option value={String(type)} key={index}>
            {String(type)}
          </option>
        ))}
      </select>
      <button className="btn btn-hotel" type="button" onClick={clearFilter}>
        Clear Filter
      </button>
    </div>
  );
};

export default EmployeeFilter;
