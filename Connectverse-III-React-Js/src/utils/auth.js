export const getToken = () => (
    localStorage.getItem("Authorization")
    // "GMC eyJhbGciOiJIUzM4NCJ9.eyJpc3MiOiJHTUMiLCJleHAiOjE2NjE5MzQ2ODcsInN1YiI6InJhamluaUBzdXBlcnN0YXIuY29tIn0.Hwb0isnPrgdN_q-LyYgLqDj8tCfKKovQ1eWYkYKYxZ3tjLSinEJ6klxbyd8zXs14"
);

export const getRefreshToken = () => (
    localStorage.getItem("Refresh_Token")
);

export const getUserId = () => {

    if(localStorage.getItem("Employee_Id") != "undefined") {
        return localStorage.getItem("Employee_Id");
    } else if(localStorage.getItem("Employer_Id") != "undefined") {
        return localStorage.getItem("Employer_Id");
    } else {
       return null;
    }
    
    // "0dfbd498-4bc4-472b-a322-a28847d16e34"
    // return "00ddc37e-6c39-4701-924f-34c67295f080"
    // "0f72ca9b-17a4-4a6b-88d8-33086f4ebf4c"
    // "6a71f065-b2a3-4dc5-a577-3ac12566dd3b"
    // "36a2f340-ec69-41cb-9815-a24fcfd40995" // employer Id
};

export const getEmployerId = () => (
    localStorage.getItem("Employer_Id") != "" ? localStorage.getItem("Employer_Id") : null
);

export const getEmployeeId = () => (
    localStorage.getItem("Employee_Id") != "" ? localStorage.getItem("Employee_Id") : null
);

export const handleLogin = (headers) => {
    console.log(headers);
    const { employer_id, employee_id, authorization, refresh_token } = headers;
    console.log(headers);
    localStorage.setItem("Authorization", authorization);
    localStorage.setItem("Refresh_Token", refresh_token);
    localStorage.setItem("Employee_Id", employee_id);
    localStorage.setItem("Employer_Id", employer_id);
    // window.location.reload();
};

export const handleLogout = () => {
    localStorage.removeItem("Authorization");
    localStorage.removeItem("Refresh_Token");
    localStorage.removeItem("Employee_Id");
    localStorage.removeItem("Employer_Id");
    window.location.reload();
}
 