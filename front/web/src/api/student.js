import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/student';

export const getStudentPage = (pageNum, pageSize, keyword) => {
    return axios.get(`${BASE_URL}/page`, {
        params: { pageNum, pageSize, keyword }
    });
};

export const addStudent = (data) => {
    return axios.post(`${BASE_URL}/add`, data);
};

export const updateStudent = (data) => {
    return axios.put(`${BASE_URL}/update`, data);
};

export const deleteStudent = (studentId) => {
    return axios.delete(`${BASE_URL}/delete/${studentId}`);
};

export const getStudentById = (studentId) => {
    return axios.get(`${BASE_URL}/get/${studentId}`);
};