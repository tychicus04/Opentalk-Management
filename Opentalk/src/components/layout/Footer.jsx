import React from "react";
import { Col, Container, Row } from "react-bootstrap";

const Footer = () => {
  let today = new Date();
  return (
    <div>
      <footer className="by-dark text-light py-3 footer mt-log-5">
        <Container>
          <Row>
            <Col xs={12} md={12} className="text-center">
              <p className="mb-0">
                &copy; {today.getFullYear()} WestLake Hotel
              </p>
            </Col>
          </Row>
        </Container>
      </footer>
    </div>
  );
};

export default Footer;
