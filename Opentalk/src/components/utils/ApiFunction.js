/* eslint-disable no-useless-catch */
/* eslint-disable no-unused-vars */
import axios from "axios";
import { error } from "jquery";

export const api = axios.create({
  baseURL: import.meta.env.VITE_BASE_URL,
  // baseURL: "http://localhost:9192",
});

export const getHeader = () => {
  const token = localStorage.getItem("token");
  console.log(token);
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

export const getHeaderFile = () => {
  const token = localStorage.getItem("token");
  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "multipart/form-data",
  };
};

/* This function add a new employee to the database*/
export async function addEmployee(employee) {
  const response = await api.post("/api/employee/admin/add", employee, {
    headers: getHeader(),
  });
  if (response.status === 201) {
    console.log(response);
    return true;
  } else {
    console.log("error");
    return false;
  }
}

/* This function gets all employees from the database */
export async function getAllEmployees(currentPage, branch) {
  try {
    const result = await api.get(
      `/api/employee/filter?pageNo=${currentPage}&pageSize=&username=&enabled=true&branch=${branch}`,
      {
        headers: getHeader(),
      }
    );
    console.log(result.data);
    return result.data;
  } catch (error) {
    throw new Error("Error fetching employees");
  }
}

/* This  function will delete a employee by the Id*/
export async function deleteEmployee(employeeId) {
  try {
    const result = await api.delete(`/api/employee/delete/${employeeId}`);
    return result.data;
  } catch (error) {
    throw new Error(`Error deleting room ${employeeId}`);
  }
}

/* This function will update employee */
export async function updateEmployee(employeeData) {
  const response = await api.put(`/api/employee/update`, employeeData);
  if (response.status === 201) {
    console.log(response);
    return true;
  } else {
    console.log("error");
    return false;
  }
}

/* This function will get a employee by Id */
export async function getEmployeeById(employeeId) {
  try {
    const result = await api.get(`api/employee/${employeeId}`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error(`Error fetching room ${error.message}`);
  }
}

// get employee by username
export async function getEmployeeByUsername(username) {
  try {
    const result = await api.get(`/api/employee/username/${username}`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error(`Error fetching employee by username ${username}`);
  }
}

/* This function login a registered user */
export async function loginUser(login) {
  try {
    const response = await api.post("/api/auth/login", login);
    if (response.status >= 200 && response.status < 300) {
      return response.data;
    } else {
      return null;
    }
  } catch (error) {
    console.error(error);
    return null;
  }
}

// get all branches
export async function getAllBranches() {
  try {
    const result = await api.get("/api/branch/all", {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error("Error fetching branches");
  }
}

// delete branch by id
export async function deleteBranch(branchId) {
  try {
    const result = await api.delete(`/api/branch/delete/${branchId}`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error(`Error deleting branch ${branchId}`);
  }
}

// add branch
export async function addBranch(branch) {
  const response = await api.post("/api/branch/add", branch, {
    headers: getHeader(),
  });
  if (response.status === 201) {
    return true;
  } else {
    return false;
  }
}

// update branch
export async function updateBranch(branchData) {
  const response = await api.put(`/api/branch/add`, branchData, {
    headers: getHeader(),
  });

  return response;
}

// get all open talk
export async function getAllOpenTalks() {
  try {
    const result = await api.get("/api/open-talk/all-open-talk", {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error("Error fetching open talks");
  }
}

// cancel open talk
export async function cancelOpenTalk(opentalkId) {
  try {
    const result = await api.delete(`/api/open-talk/delete/${opentalkId}`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error(`Error cancelling open talk ${opentalkId}`);
  }
}

// add open talk
export async function addOpenTalk(opentalk) {
  try {
    const response = await api.post("/api/open-talk/add", opentalk, {
      headers: getHeader(),
    });
    if (response.status === 201) {
      return true;
    } else {
      return false;
    }
  } catch (error) {
    console.error(error);
  }
}

// random host
export async function randomHost() {
  try {
    const result = await api.get("/api/employee/admin/random-host", {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error("Error fetching random host");
  }
}

// join open talk
export async function joinOpenTalk(employeeId, opentalkId) {
  const response = await api.put(
    `/api/open-talk/register-join?employeeId=${employeeId}&opentalkId=${opentalkId}`,
    {
      headers: getHeader(),
    }
  );
  return response.data;
}

// get employee by email
export async function getEmployeeByEmail(email) {
  try {
    const result = await api.get(`/api/employee/admin/email/${email}`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error(`Error fetching employee by email ${email}`);
  }
}

// sync account from hrm tool
export async function syncAccount() {
  try {
    const result = await api.get("/api/employee/account", {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error("Error syncing account");
  }
}

// sync employee from hrm tool
export async function syncEmployee() {
  try {
    const result = await api.get("api/employee/admin/accounts", {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error("Error syncing employee");
  }
}

// get open talk by employee id
export async function getOpentalkByEmployeeId(employeeId) {
  try {
    const result = await api.get(`/api/open-talk/employee/${employeeId}`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error("Error fetching open talks");
  }
}

// upload file
export async function uploadFile(file) {
  console.log(file);
  // new from data
  const formData = new FormData();
  formData.append("file", file);
  console.log(formData);
  try {
    const result = await api.post(`/api/files/upload/slide`, formData, {
      headers: getHeaderFile(),
    });
    return result.data;
  } catch (error) {
    console.log(error);
    throw new Error(error);
  }
}

// get urlfile by folder
export async function getUrlFileByFolder(opentalkTopic) {
  try {
    const result = await api.get(`/api/files/get-url/${opentalkTopic}`, {
      headers: getHeader(),
    });
    return result.data;
  } catch (error) {
    throw new Error("Error fetching url file");
  }
}
