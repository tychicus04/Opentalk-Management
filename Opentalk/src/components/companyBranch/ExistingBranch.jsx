import React, { useEffect, useState } from "react";
import { deleteBranch, getAllBranches } from "../utils/ApiFunction";
import { FaEdit, FaPlus, FaTrashAlt } from "react-icons/fa";
import { Link } from "react-router-dom";
import { Col, Row } from "react-bootstrap";

const ExistingBranch = () => {
  const [branches, setBranches] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  useEffect(() => {
    fetchBranches();
  }, []);

  const fetchBranches = async () => {
    setIsLoading(true);
    try {
      const result = await getAllBranches();
      setBranches(result);
      setIsLoading(false);
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  const handleDeleteBranchById = async (branchId) => {
    const result = await deleteBranch(branchId);
    try {
      if (result === "") {
        setSuccessMessage(`branch no ${branchId} was deleted`);
        fetchBranches();
      }
    } catch (error) {
      setErrorMessage(`Error deleting branch: ${result.message}`);
    }
    setTimeout(() => {
      setErrorMessage("");
      setSuccessMessage("");
    }, 3000);
  };

  return (
    <div>
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
          <p>Loading existing branches</p>
        ) : (
          <>
            <section className="mt-5 mb-5 container">
              <div className="d-flex justify-content-between mb-3 mt-5">
                <h2>Existing branches</h2>
              </div>
              <Row>
                <Col md={12} className="d-flex  justify-content-end">
                  <Link to={"/add-branch"}>
                    <FaPlus /> Add Branch
                  </Link>
                </Col>
              </Row>

              <table className="table table-bordered table-hover">
                <thead>
                  <tr className="text-center">
                    <th className="">ID</th>
                    <th className="">name</th>

                    <th className="">Actions</th>
                  </tr>
                </thead>

                <tbody>
                  {branches.map((branch) => (
                    <tr key={branch.id} className="text-center">
                      <td>{branch.id}</td>
                      <td>{branch.name}</td>

                      <td className="gap-2">
                        <Link to={`/edit-branch/${branch.id}`}>
                          <span className="btn btn-warning btn-sm">
                            <FaEdit />
                          </span>
                        </Link>

                        <button
                          className="btn btn-danger btn-sm"
                          onClick={() => {
                            handleDeleteBranchById(branch.id);
                          }}
                        >
                          <FaTrashAlt />
                        </button>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </section>
          </>
        )}
      </>
    </div>
  );
};

export default ExistingBranch;
