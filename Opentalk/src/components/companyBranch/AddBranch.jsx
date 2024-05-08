/* eslint-disable no-unused-vars */
import React, { useState } from "react";
import { addBranch } from "../utils/ApiFunction";
import { Link } from "react-router-dom";

const AddBranch = () => {
  const [newBranch, setNewBranch] = useState({
    name: "",
    openTalks: [],
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleBranchInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;
    setNewBranch({ ...newBranch, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const success = await addBranch(newBranch);
      if (success !== undefined) {
        setSuccessMessage("A new Branch was added to the database");
        setNewBranch({
          name: "",
        });

        setErrorMessage("");
      } else {
        setErrorMessage("Error adding company branch");
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
            <h2 className="mt-5 mb-2">Add a New Branch</h2>

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
                <label htmlFor="name" className="form-label">
                  Name
                </label>
                <input
                  className="form-control"
                  required
                  id="name"
                  type="text"
                  name="name"
                  value={newBranch.name}
                  onChange={handleBranchInputChange}
                />
              </div>

              <div className="d-grid d-md-flex mt-2 ">
                <Link to={"/existing-branch"} className="btn btn-outline-info">
                  Back
                </Link>
                <button className="btn btn-outline-primary ms-3">
                  Save Branch
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
};

export default AddBranch;
