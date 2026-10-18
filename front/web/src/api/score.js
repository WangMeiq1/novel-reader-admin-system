import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/score';

export const getScorePage = (pageNum, pageSize, keyword) => {
    return axios.get(`${BASE_URL}/page`, {
        params: { pageNum, pageSize, keyword }
    });
};

export const addScore = (data) => {
    return axios.post(`${BASE_URL}/add`, data);
};

export const updateScore = (data) => {
    return axios.put(`${BASE_URL}/update`, data);
};

export const deleteScore = (scoreId) => {
    return axios.delete(`${BASE_URL}/delete/${scoreId}`);
};

export const getStatistics = (courseId) => {
    return axios.get(`${BASE_URL}/statistics`, {
        params: { courseId }
    });
};