// import moment from "moment";
// import React, { useState } from "react";
// import { getAvailableRoom } from "../utils/ApiFunction";
// import { Col, Container, Form, Row, Button } from "react-bootstrap";
// import RoomTypeSelector from "./RoomTypeSelector";
// import RoomSearchResult from "./RoomSearchResult";

// const RoomSearch = () => {
//   const [searchQuery, setSearchQuery] = useState({
//     checkInDate: "",
//     checkOutDate: "",
//     roomType: "",
//   });
//   const [errorMessage, setErrorMessage] = useState("");
//   const [availableRooms, setAvailableRooms] = useState([]);
//   const [isLoading, setIsLoading] = useState(false);

//   const handleSearch = (e) => {
//     e.preventDefault();
//     const checkIn = moment(searchQuery.checkInDate);
//     const checkOut = moment(searchQuery.checkOutDate);
//     if (!checkIn.isValid() || !checkOut.isValid()) {
//       setErrorMessage("Please , enter valid date range");
//       return;
//     }
//     if (!checkOut.isSameOrAfter(checkIn)) {
//       setErrorMessage("Check-In date must come before Check-Out date");
//       return;
//     }
//     setIsLoading(true);
//     getAvailableRoom(
//       searchQuery.checkInDate,
//       searchQuery.checkOutDate,
//       searchQuery.roomType
//     )
//       .then((response) => {
//         setAvailableRooms(response.data);
//         setTimeout(() => {
//           setIsLoading(false);
//         }, 2000);
//       })
//       .catch((error) => {
//         console.error(error);
//       })
//       .finally(() => {
//         setIsLoading(false);
//       });
//   };

//   const handleInputChange = (e) => {
//     const { name, value } = e.target;
//     setSearchQuery({ ...searchQuery, [name]: value });
//     const checkInDate = moment(searchQuery.checkInDate);
//     const checkOutDate = moment(searchQuery.checkOutDate);
//     if (checkInDate.isValid() && checkOutDate.isValid()) {
//       setErrorMessage("");
//     }
//   };

//   const handleClearSearch = () => {
//     setSearchQuery({
//       checkInDate: "",
//       checkOutDate: "",
//       roomType: "",
//     });
//     setAvailableRooms([]);
//   };

//   return (
//     <>
//       <Container className="mt-5 mb-5 py-5 shadow">
//         <Form onSubmit={handleSearch}>
//           <Row className="justify-content-center">
//             <Col xs={12} md={3}>
//               <Form.Group controlId="checkInDate">
//                 <Form.Label>Check-In date</Form.Label>
//                 <Form.Control
//                   type="date"
//                   name="checkInDate"
//                   value={searchQuery.checkInDate}
//                   onChange={handleInputChange}
//                   min={moment().format("YYYY-MM-DD")}
//                 />
//               </Form.Group>
//             </Col>

//             <Col xs={12} md={3}>
//               <Form.Group controlId="checkOutDate">
//                 <Form.Label>Check-out Date</Form.Label>
//                 <Form.Control
//                   type="date"
//                   name="checkOutDate"
//                   value={searchQuery.checkOutDate}
//                   onChange={handleInputChange}
//                   min={moment().format("YYYY-MM-DD")}
//                 />
//               </Form.Group>
//             </Col>
//             <Col xs={12} md={3}>
//               <Form.Group controlId="roomType">
//                 <Form.Label>Room Type</Form.Label>
//                 <div className="d-flex">
//                   <RoomTypeSelector
//                     handleRoomInputChange={handleInputChange}
//                     newRoom={searchQuery}
//                   />
//                   <Button variant="secondary" type="submit" className="ms-2">
//                     Search
//                   </Button>
//                 </div>
//               </Form.Group>
//             </Col>
//           </Row>
//         </Form>

//         {isLoading ? (
//           <p className="mt-4">Finding availble rooms....</p>
//         ) : availableRooms ? (
//           <RoomSearchResult
//             results={availableRooms}
//             onClearSearch={handleClearSearch}
//           />
//         ) : (
//           <p className="mt-4">
//             No rooms available for the selected dates and room type.
//           </p>
//         )}
//         {errorMessage && <p className="text-danger">{errorMessage}</p>}
//       </Container>
//     </>
//   );
// };

// export default RoomSearch;
