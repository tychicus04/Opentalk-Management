import React from "react";

import {
  CDBSidebar,
  CDBSidebarContent,
  CDBSidebarFooter,
  CDBSidebarHeader,
  CDBSidebarMenu,
  CDBSidebarMenuItem,
} from "cdbreact";
import { NavLink } from "react-router-dom";

const Sidebar = () => {
  return (
    <div
    // style={{ display: "flex", height: "100vh", overflow: "scroll initial" }}
    >
      <CDBSidebar style={{ background: "rgb(145, 69, 186)" }}>
        <CDBSidebarHeader prefix={<i className="fa fa-bars fa-large"></i>}>
          <a
            href="/"
            className="text-decoration-none main-color"
            style={{ color: "white" }}
          >
            ADMIN
          </a>
        </CDBSidebarHeader>

        <CDBSidebarContent className="sidebar-content main-color ">
          <CDBSidebarMenu>
            <CDBSidebarMenuItem>
              <div>
                <div className="d-flex justify-content-center align-items-center">
                  <img
                    alt="profile-user"
                    width="70"
                    height="70"
                    src={`../../src/assets/images/tychicus.jpg`}
                    style={{ cursor: "pointer", borderRadius: "50%" }}
                  />
                </div>
                <div className="text-center">
                  <h2 className="fw-bold" style={{ margin: "10px 0 0 0" }}>
                    Tychicus
                  </h2>
                </div>
              </div>
            </CDBSidebarMenuItem>
            <NavLink exact to="/" activeClassName="activeClicked">
              <CDBSidebarMenuItem icon="columns">Home</CDBSidebarMenuItem>
            </NavLink>
            <NavLink exact to="/company-branch" activeClassName="activeClicked">
              <CDBSidebarMenuItem icon="table">Companies</CDBSidebarMenuItem>
            </NavLink>
            <NavLink
              exact
              to="/existing-employees"
              activeClassName="activeClicked"
            >
              <CDBSidebarMenuItem icon="user">Employees</CDBSidebarMenuItem>
            </NavLink>
            <NavLink
              exact
              to="/mangage-open-talk"
              activeClassName="activeClicked"
            >
              <CDBSidebarMenuItem icon="chart-line">
                Open Talks
              </CDBSidebarMenuItem>
            </NavLink>

            <NavLink
              exact
              to="/hero404"
              target="_blank"
              activeClassName="activeClicked"
            >
              <CDBSidebarMenuItem icon="exclamation-circle">
                404 page
              </CDBSidebarMenuItem>
            </NavLink>
          </CDBSidebarMenu>
        </CDBSidebarContent>
        <CDBSidebarFooter style={{ textAlign: "center" }}>
          <div
            style={{
              padding: "20px 5px",
            }}
          >
            Sidebar Footer
          </div>
        </CDBSidebarFooter>
      </CDBSidebar>
    </div>
  );
};

export default Sidebar;
