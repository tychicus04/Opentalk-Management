import React from "react";

const UserPaginator = ({ currentPage, totalPages, onPageChange }) => {
  return (
    <nav>
      <ul className="pagination">
        <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
          <button className="page-link" onClick={() => onPageChange("prev")}>
            Previous
          </button>
        </li>
        <li
          className={`page-item ${
            currentPage === totalPages ? "disabled" : ""
          }`}
        >
          <button className="page-link" onClick={() => onPageChange("next")}>
            Next
          </button>
        </li>
      </ul>
    </nav>
  );
};

export default UserPaginator;
