import jwt_decode from "jwt-decode";
import React, { createContext, useContext, useState } from "react";
import { getEmployeeByUsername as getEmployeeByUsernameAPI } from "../utils/ApiFunction";

export const AuthContext = createContext({
  user: null,
  handleLogin: (token) => {},
  handleLogout: () => {},
});

const getEmployeeByUsername = async (username) => {
  try {
    const result = await getEmployeeByUsernameAPI(username);
    return result;
  } catch (error) {
    console.error(error);
  }
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);

  const handleLogin = async (token) => {
    const decodedUser = jwt_decode(token);
    let rolesArray = decodedUser.roles.map((role) => role.authority);
    let rolesString = JSON.stringify(rolesArray);

    localStorage.setItem("userId", decodedUser.sub);
    localStorage.setItem("userRole", rolesString);
    localStorage.setItem("token", token);

    const employee = await getEmployeeByUsername(decodedUser.sub);
    let id = employee.id;
    localStorage.setItem("employeeId", id);

    setUser({ ...decodedUser });
  };

  const handleLogout = () => {
    localStorage.removeItem("userId");
    localStorage.removeItem("userRole");
    localStorage.removeItem("token");
    localStorage.removeItem("employeeId");
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, handleLogin, handleLogout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  return useContext(AuthContext);
};
