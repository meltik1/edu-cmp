import BaseService from "./BaseService";


class UserService extends BaseService {
    async sendToUser(template, theme, userId) {
        const body = {"template" : template, "theme" : theme}
        const headers = {
            'Content-Type': 'application/json'
        }
        await this.repository.post(`user/sendToUser/${userId}`, body, {
            headers: headers
        })
    }

    async saveUsersToDataBase(sessionId) {
        await this.repository.get(`/sessions/${sessionId}/save-to-db`)
    }

    async saveAttributeForUser(userId, content) {
        const headers = {
            'Content-Type': 'application/json'
        }
        await this.repository.post(`/user/saveForUser/${userId}`, content, {
            headers: headers
        })
    }

    async getAllUsers() {
        return await this.repository.get(`/user/getAll`)
    }

    async getUserAttributes(userId) {
        return await this.repository.get(`/user/get/${userId}`)
    }

    async deleteUserAttribute(userId, body) {
        const headers = {
            'Content-Type': 'text/html; charset=UTF-8'
        }
        await this.repository.post(`/user/deleteAttribute/${userId}`, body, {
            headers: headers
        })
    }
}

export default UserService; 