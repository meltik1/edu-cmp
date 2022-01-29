import axios from "axios";
import Settings from "./backend.settings.json"
import TokenStorage from "./TokenStorage";

class BaseService {
    constructor() {
        this.repository = axios.create({
                withCredentials: true,
                baseURL : Settings.backend.url
            }
        )

        this.repository.interceptors.request.use(
            config =>
            {
                const token = TokenStorage.getAccessToken()
                console.log(token)
                if (token) {
                    config.headers['Authorization'] = 'Bearer ' + token;
                }
                console.log(config)
                return config
            },
            error => {
                Promise.reject(error)
            })

        this.repository.interceptors.response.use((response) => {
                return response
            },
            function (error) {
                const originalRequest = error.config;
                console.log(error.response.data)
                if (error.response.status === 401 && !originalRequest._retry) {

                    originalRequest._retry = true;
                    return axios.post('/auth/refresh',
                        {
                            "refreshToken": TokenStorage.getRefreshToken()
                        })
                        .then(res => {
                            if (res.status === 200) {
                                TokenStorage.setToken(res.data["accessToken"]);

                                axios.defaults.headers['Authorization'] = 'Bearer ' + TokenStorage.getAccessToken();

                                return axios(originalRequest);
                            }
                        })
                }

                return Promise.reject(error);
            });

    }
}

export default BaseService