import React from "react";

import { Link } from "react-router-dom";

const Admin = () => {
  return (
    <div>
      {/* <Sidebar> */}
      <section className="container">
        <h2>Welcome to Admin Panel</h2>
        <hr />
        <Link to={"/existing-employees"}>Mannage Employees</Link>
        <hr />
        <Link to={"/existing-opentalks"}>Mannage Open Talks</Link>
        <hr />
        <Link to={"/existing-branch"}>Mannage Companies</Link>
        <hr />
        <Link to={"/sync-user"}>Sync User From HRM Tool</Link>
      </section>
      {/* </Sidebar> */}
    </div>
  );
};

export default Admin;
