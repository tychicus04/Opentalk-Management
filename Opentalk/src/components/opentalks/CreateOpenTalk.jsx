import React, { useState } from "react";
import { addOpenTalk, uploadFile } from "../utils/ApiFunction";
import CompanyBranchSelector from "../common/CompanyBranchSelector";
import { FormControl } from "react-bootstrap";
import { Link } from "react-router-dom";

const CreateOpenTalk = () => {
  const employeeName = localStorage.getItem("userId");
  const employeeId = localStorage.getItem("employeeId");
  console.log(employeeId);
  const [selectedImage, setSelectedImage] = useState();

  const [newOpentalk, setNewOpentalk] = useState({
    topic: "",
    startTime: "",
    endTime: "",
    linkMeeting: "",
    slide: "",
    host: {
      id: employeeId,
      name: employeeName,
    },
    companyBranchName: "",
    participants: [],
  });

  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");

  const handleInputChange = (e) => {
    const name = e.target.name;
    let value = e.target.value;
    setNewOpentalk({ ...newOpentalk, [name]: value });
  };

  const handleFileChange = (e) => {
    setSelectedImage(e.target.files[0]);
    // setImagePreview(URL.createObjectURL(selectedImage));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    console.log(newOpentalk);

    try {
      // First, handle the file upload
      const upload = await uploadFile(selectedImage);
      console.log(upload);

      if (upload) {
        // If the upload is successful, update the newOpentalk state with the uploaded file URL
        setNewOpentalk({ ...newOpentalk, slide: upload.url });
        console.log(newOpentalk.slide);

        // Then, add the new open talk
        const success = await addOpenTalk(newOpentalk);

        if (success) {
          // If the open talk is successfully added, reset the newOpentalk state and clear any error messages
          setSuccessMessage("A new open talk was added to the database");
          setNewOpentalk({
            topic: "",
            startTime: "",
            endTime: "",
            linkMeeting: "",
            slide: "",
            host: {
              id: employeeId,
              name: employeeName,
            },
            companyBranchName: "",
            participants: [],
          });

          setErrorMessage("");
        } else {
          // If there's an error adding the open talk, set the error message
          setErrorMessage("Error adding employee");
        }
      } else {
        // If there's an error uploading the file, set the error message
        setErrorMessage("Error uploading file");
      }
    } catch (error) {
      // If there's any other error, set the error message
      setErrorMessage(error.message);
    }

    // Clear success and error messages after 3 seconds
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
            <h2 className="mt-5 mb-2">Add a New Open talk</h2>

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
                <label htmlFor="companyBranchName" className="form-label">
                  Company Branch
                </label>
                <CompanyBranchSelector
                  handleEmployeeInputChange={handleInputChange}
                  newEmployee={newOpentalk}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="topic" className="form-label">
                  Topic
                </label>
                <input
                  className="form-control"
                  required
                  id="topic"
                  type="text"
                  name="topic"
                  value={newOpentalk.topic}
                  onChange={handleInputChange}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="photo" className="form-label">
                  Slide
                </label>
                <input
                  id="photo"
                  name="photo"
                  type="file"
                  className="form-control"
                  onChange={handleFileChange}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="startTime" className="form-label">
                  Start Time:
                </label>
                <FormControl
                  type="datetime-local"
                  name="startTime"
                  value={newOpentalk?.startTime}
                  min={new Date().toISOString().slice(0, 16)}
                  onChange={handleInputChange}
                  format={"yyyy-MM-dd HH:mm:ss"}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="endTime" className="form-label">
                  Start Time:
                </label>
                <FormControl
                  type="datetime-local"
                  name="endTime"
                  value={newOpentalk?.endTime}
                  min={new Date().toISOString().slice(0, 16)}
                  onChange={handleInputChange}
                  format={"yyyy-MM-dd HH:mm:ss"}
                />
              </div>

              <div className="mb-3">
                <label htmlFor="linkMeeting" className="form-label">
                  Link Meeting
                </label>
                <input
                  className="form-control"
                  required
                  id="linkMeeting"
                  type="text"
                  name="linkMeeting"
                  value={newOpentalk.linkMeeting}
                  onChange={handleInputChange}
                />
              </div>

              <div className="d-grid d-md-flex mt-2 ">
                <Link
                  to={"/existing-opentalks"}
                  className="btn btn-outline-info"
                >
                  Back
                </Link>
                <button className="btn btn-outline-primary ms-3">
                  Save open talk
                </button>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
};

export default CreateOpenTalk;
