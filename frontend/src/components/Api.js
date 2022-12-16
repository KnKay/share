import {applyAuthTokenInterceptor, clearAuthTokens, setAuthTokens} from 'axios-jwt';
import axios from 'axios';

const BASE_URL = '/api/v1'

export const axiosInstance = axios.create({baseURL: BASE_URL})

const requestRefresh = (refresh) => {
    // Notice that this is the global axios instance, not the axiosInstance!  <-- important
    return axios.post(`${BASE_URL}/token/refresh/`, {refresh})
        .then(response => response.data.access)
};

applyAuthTokenInterceptor(axiosInstance, {
    header: 'Authorization',
    headerPrefix: 'Bearer ',
    requestRefresh,
});

export const login = async (username, password) => {
    const response = await axiosInstance.post('/token/', {
        username: username,
        password: password
    })

    setAuthTokens({
        accessToken: response.data.access,
        refreshToken: response.data.refresh
    })
}

export const logout = () => {
    console.log("logout")
    clearAuthTokens()
    window.location.replace('/')
    window.location.reload()
}
