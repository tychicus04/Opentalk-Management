import React, { useState } from "react";
import { Card, Col } from "react-bootstrap";
import { joinOpenTalk, getEmployeeByEmail } from "../utils/ApiFunction";

const OpentalkCard = ({ opentalk }) => {
  const now = new Date();
  const startTime = new Date(opentalk?.startTime);
  const [employee, setEmployee] = useState({});

  const isInterestedButtonEnabled = now <= startTime;

  const userId = localStorage.getItem("userId");

  const findEmployeeByEmail = async () => {
    const response = await getEmployeeByEmail(userId);
    console.log(response);
    setEmployee(response);
  };

  const handleInterested = async () => {
    findEmployeeByEmail();
    console.log(employee.id, opentalk.id);
    const response = await joinOpenTalk(employee.id, opentalk.id);
    console.log(response);
  };

  return (
    <Col className="mb-4" xs={12} key={opentalk.id}>
      <Card>
        <Card.Body className="d-flex flex-wrap align-items-center">
          <div className="flex-grow-1 ml-3 px-5">
            <Card.Title className="main-color">{opentalk.topic}</Card.Title>
            <Card.Title className="Employee-price mb-3">
              Host: {opentalk?.host?.name}
            </Card.Title>
            <Card.Subtitle className="mb-2">
              StartTime: {opentalk?.startTime}
            </Card.Subtitle>
            <Card.Subtitle className="mb-3">
              EndTime: {opentalk?.endTime}
            </Card.Subtitle>
            <Card.Text>
              Some Open Talk information goes here for the guest to read through
            </Card.Text>
          </div>

          <div className="flex-shrink-0 mt-3">
            {/* <Link
              to={`/interested-in-opentalk/${opentalk.id}`}
              className={`btn btn-hotel btn-sm ${
                isInterestedButtonEnabled ? "" : "disabled"
              }`}
            >
              Interested
            </Link> */}
            <button
              className={`btn btn-hotel btn-sm ${
                isInterestedButtonEnabled ? "" : "disabled"
              }`}
              onClick={handleInterested}
            >
              Interested
            </button>
          </div>
        </Card.Body>
      </Card>
    </Col>
  );
};

export default OpentalkCard;
