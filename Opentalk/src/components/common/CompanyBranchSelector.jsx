import React, { useEffect, useState } from "react";
import { getAllBranches } from "../utils/ApiFunction";

const CompanyBranchSelector = ({ handleEmployeeInputChange, newEmployee }) => {
  const [companyBranches, setCompanyBranches] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getAllBranches();
        setCompanyBranches(data);
        console.log(data);
      } catch (error) {
        console.error("Error fetching company branch", error);
      }
    };

    fetchData();
  }, []);

  return (
    <>
      {companyBranches.length >= 0 && (
        <div>
          <select
            required
            className="form-select"
            name="companyBranchName"
            value={newEmployee.companyBranch}
            onChange={(e) => {
              handleEmployeeInputChange(e);
              console.log(e.target.value);
              // }
            }}
          >
            <option value={""}>Select company branch</option>
            {companyBranches.map((type, index) => (
              <option key={index} value={type.name}>
                {type.name}
              </option>
            ))}
          </select>
        </div>
      )}
    </>
  );
};

export default CompanyBranchSelector;
