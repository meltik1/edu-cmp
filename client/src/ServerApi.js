import axios from "axios";
import Settings from "./backend.settings.json"
import TokenStorage from "./TokenStorage";
import {useHistory, useParams} from "react-router";
export const backend = axios.create(
    {
        baseURL: Settings.backend.url
    }
)

backend.interceptors.request.use(
    config =>
    {
        const token = TokenStorage.getAccessToken()
        console.log(token)
        if (token) {
            config.headers['Authorization'] = token;
        }
        console.log(config)
        return config
    },
    error => {
        Promise.reject(error)
    });

backend.interceptors.response.use((response) => {
        return response
    },
    function (error) {
        const originalRequest = error.config;
        console.log(error.response.data)
        if (error.response.status === 401 && !originalRequest._retry) {

            originalRequest._retry = true;
            return axios.post('/auth/login',
                {
                    "email": Settings.backend.login,
                    "password": Settings.backend.password
                })
                .then(res => {
                    if (res.status === 200) {
                        // 1) put token to LocalStorage
                        TokenStorage.setToken(res.data);

                        // 2) Change Authorization header
                        axios.defaults.headers['Authorization'] = TokenStorage.getAccessToken();

                        // 3) return originalRequest object with Axios.
                        return axios(originalRequest);
                    }
                })
        }

        // return Error object with Promise
        return Promise.reject(error);
    });

