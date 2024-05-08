// /* eslint-disable no-unused-vars */
// import React, { useState } from "react";
// import moment from "moment";
// import {
//   cancelBooking,
//   getBookingConfirmationCode,
// } from "../utils/ApiFunction";

// const FindBooking = () => {
//   const [confirmationCode, setConfirmationCode] = useState("");
//   const [error, setError] = useState(null);
//   const [successMessage, setSuccessMessage] = useState("");
//   const [isLoading, setIsLoading] = useState(false);
//   const [bookingInfo, setBookingInfo] = useState({
//     id: "",
//     bookingConfirmationCode: "",
//     room: { id: "", roomType: "" },
//     roomNumber: "",
//     checkInDate: "",
//     checkOutDate: "",
//     guestName: "",
//     guestEmail: "",
//     numOfAdults: "",
//     numOfChildren: "",
//     totalNumberOfGuests: "",
//   });
//   const [isDeleted, setIsDeleted] = useState(false);

//   const clearBookingInfo = {
//     id: "",
//     bookingConfirmationCode: "",
//     room: { id: "", roomType: "" },
//     roomNumber: "",
//     checkInDate: "",
//     checkOutDate: "",
//     guestName: "",
//     guestEmail: "",
//     numOfAdults: "",
//     numOfChildren: "",
//     totalNumberOfGuests: "",
//   };

//   const handleInputChange = (e) => {
//     setConfirmationCode(e.target.value);
//   };
//   const handleFormSubmit = async (e) => {
//     e.preventDefault();
//     setIsLoading(true);

//     try {
//       const data = await getBookingConfirmationCode(confirmationCode);
//       setBookingInfo(data);

//       setError(null);
//     } catch (error) {
//       setBookingInfo(clearBookingInfo);
//       if (error.response && error.response.status === 404) {
//         setError(error.response.data.message);
//       } else {
//         setError(error.message);
//       }
//     }

//     setTimeout(() => {
//       setIsLoading(false);
//     }, 2000);
//   };

//   const handleBookingCancellation = async (bookingId) => {
//     try {
//       await cancelBooking(bookingInfo.id);
//       setIsDeleted(true);

//       setBookingInfo(clearBookingInfo);
//       setSuccessMessage("Booking has been cancelled successfully!");
//       setConfirmationCode("");
//       setError(null);
//     } catch (error) {
//       setIsDeleted(false);
//       setError(error.message);
//     }
//     setTimeout(() => {
//       setSuccessMessage("");
//       setIsDeleted(false);
//     }, 2000);
//   };

//   return (
//     <>
//       <div className="container mt-5 d-flex flex-column justify-content-center align-items-center">
//         <h2 className="text-center mb-4">Find My Booking</h2>
//         <form onSubmit={handleFormSubmit} className="col-md-6">
//           <div className="input-group mb-3">
//             <input
//               className="form-control"
//               type="text"
//               id="confirmationCode"
//               name="confirmationCode"
//               value={confirmationCode}
//               onChange={handleInputChange}
//               placeholder="Enter the booking confirmation code"
//             />
//             <button type="submit" className="btn btn-hotel input-group-text">
//               Find booking
//             </button>
//           </div>
//         </form>

//         {isLoading ? (
//           <div>Finding your booking.....</div>
//         ) : error ? (
//           <div className="text-danger">Error: {error}</div>
//         ) : bookingInfo.bookingConfirmationCode ? (
//           <div className="col-md-6 mt-4 mb-5">
//             <h3>Booking Infomation</h3>
//             <p>
//               Booking Confirmation Code: {bookingInfo.bookingConfirmationCode}
//             </p>
//             <p>Booking ID: {bookingInfo.id}</p>
//             <p>Room Number: {bookingInfo.room.id}</p>
//             <p>Room Type: {bookingInfo.room.roomType}</p>
//             <p>
//               Check-in Date:{" "}
//               {moment(bookingInfo.checkInDate)
//                 .subtract(1, "month")
//                 .format("MMM Do, YYYY")}
//             </p>
//             <p>
//               Check-out Date:{" "}
//               {moment(bookingInfo.checkInDate)
//                 .subtract(1, "month")
//                 .format("MMM Do, YYYY")}
//             </p>
//             <p>Full Name: {bookingInfo.guestName}</p>
//             <p>Email Address: {bookingInfo.guestEmail}</p>
//             <p>Adults: {bookingInfo.numOfAdults}</p>
//             <p>Children: {bookingInfo.numOfChildren}</p>
//             <p>Total Guest: {bookingInfo.totalNumberOfGuests}</p>

//             {!isDeleted && (
//               <button
//                 onClick={() => handleBookingCancellation(bookingInfo.id)}
//                 className="btn btn-danger"
//               >
//                 Cancel Booking
//               </button>
//             )}
//           </div>
//         ) : (
//           <div>find booking...</div>
//         )}

//         {isDeleted && (
//           <div className="alert alert-success mt-3 fade show">
//             {successMessage}
//           </div>
//         )}
//       </div>
//     </>
//   );
// };

// export default FindBooking;
