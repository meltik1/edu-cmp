import BaseService from "./BaseService";
import axios from "axios";
import Settings from "./backend.settings.json";


class LoginService extends BaseService {
    async login(username, password) {
        let s = await axios.post(Settings.backend.url + '/auth/login', {
            "email" : username,
            "password": password
        })
        return s
    }

    async logout() {
        await this.repository.post('/auth/logout')
    }

 }

 export default LoginService;