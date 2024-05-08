import React, { useContext, useState } from "react";
import { Link, NavLink } from "react-router-dom";
import Logout from "../auth/Logout";
import { AuthContext } from "../auth/AuthProvider";

const NavBar = () => {
  const [showAccount, setShowAccount] = useState(false);
  const { user } = useContext(AuthContext);
  const handleAccountClick = () => {
    setShowAccount(!showAccount);
  };
  const isLoggedIn = user !== null;
  let userRoles = localStorage.getItem("userRole");
  userRoles = JSON.parse(userRoles); // Parse the stringified array
  let isAdmin = false;
  // Iterate over the array and check each role
  if (userRoles !== null && userRoles !== undefined) {
    for (let i = 0; i < userRoles.length; i++) {
      console.log(userRoles[i]);
      if (userRoles[i] === "ROLE_ADMIN") {
        isAdmin = true;
        break;
      }
    }
  } else {
    console.log("Object is null or undefined");
  }

  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary shadow sticky-top">
      <div className="container-fluid">
        <Link to={"/"} className="navbar-brand">
          <span className="main-color">TECH TALKS</span>
        </Link>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarTogglerDemo01"
          aria-controls="#navbarTogglerDemo01"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarTogglerDemo01">
          <ul className="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll">
            <li className="nav-item">
              <NavLink
                className="nav-link"
                aria-current="page"
                to={"/browse-all-opentalks"}
              >
                Browse all open talks
              </NavLink>
            </li>

            {isLoggedIn && isAdmin && (
              <li className="nav-item">
                <NavLink className="nav-link" aria-current="page" to={"/admin"}>
                  Admin
                </NavLink>
              </li>
            )}
          </ul>

          {/* <ul className="d-flex navbar-nav mx-auto ">
            <form className="d-flex">
              <input
                className="form-control mr-sm-2"
                type="search"
                placeholder="Search"
                aria-label="Search"
              />
              <button className="btn btn-hotel my-2 my-sm-0 " type="submit">
                Search
              </button>
            </form>
          </ul> */}

          <ul className="d-flex navbar-nav">
            <li className="nav-item">
              <NavLink className="nav-link" to={"/create-opentalk"}>
                Create My OpenTalk
              </NavLink>
            </li>

            <li className="nav-item dropdown">
              <a
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
                onClick={handleAccountClick}
                className={`nav-link dropdown-toggle ${
                  showAccount ? "show" : ""
                }`}
              >
                {" "}
                Account
              </a>

              <ul
                className={`dropdown-menu ${showAccount ? "show" : ""}`}
                aria-labelledby="navbarDropdown"
              >
                {isLoggedIn ? (
                  <Logout />
                ) : (
                  <li>
                    <Link to={"/login"} className="dropdown-item">
                      Login
                    </Link>
                  </li>
                )}
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
