import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/course';

export const getCoursePage = (pageNum, pageSize, keyword) => {
    return axios.get(`${BASE_URL}/page`, {
        params: { pageNum, pageSize, keyword }
    });
};

export const addCourse = (data) => {
    return axios.post(`${BASE_URL}/add`, data);
};

export const updateCourse = (data) => {
    return axios.put(`${BASE_URL}/update`, data);
};

export const deleteCourse = (courseId) => {
    return axios.delete(`${BASE_URL}/delete/${courseId}`);
};