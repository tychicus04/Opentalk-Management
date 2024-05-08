import React, { useEffect, useState } from "react";
import Header from "../common/Header";
import { cancelOpenTalk, getAllOpenTalks } from "../utils/ApiFunction";
import OpentalksTable from "./OpentalksTable";
import { Col, Row } from "react-bootstrap";
import { Link } from "react-router-dom";
import { FaPlus } from "react-icons/fa";

const Opentalks = () => {
  const [openTalkInfo, setOpenTalkInfo] = useState([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    fetchOpenTalks();
  }, []);

  const fetchOpenTalks = async () => {
    setIsLoading(true);
    try {
      const result = await getAllOpenTalks();
      console.log("Result from API:", result);
      setOpenTalkInfo(result);
      setIsLoading(false);
    } catch (error) {
      setError(error.message);
    }
  };

  const handleOpenTalkCancellation = async (openTalkId) => {
    try {
      await cancelOpenTalk(openTalkId);
      const data = await getAllOpenTalks();
      setOpenTalkInfo(data);
    } catch (error) {
      setError(error.message);
    }
  };
  return (
    <section className="container" style={{ background: "whitesmoke" }}>
      <Header title={"Existing Open talks"} />
      <Row>
        <Col md={2} className="btn d-flex justify-content-end mb-2">
          <Link to={"/add-opentalk"}>
            <FaPlus /> Add open talk
          </Link>
        </Col>
      </Row>
      {error && <div className="text-danger">{error}</div>}
      {isLoading ? (
        <div>Loading Existing open talks.....</div>
      ) : (
        openTalkInfo.length > 0 && (
          <OpentalksTable
            opentalkInfo={openTalkInfo}
            handleOpenTalkCancellation={handleOpenTalkCancellation}
          />
        )
      )}
    </section>
  );
};

export default Opentalks;
