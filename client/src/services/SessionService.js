import BaseService from "./BaseService";


class SessionService extends BaseService {
    async getAllSessions() {
        return await this.repository.get('sessions')
    }

    async saveTemplate(sessionId, text) {
        const headers = {
            'Content-Type': 'text/html; charset=UTF-8'
        }

        await this.repository.post(`/sessions/${sessionId}/save-template`, text, {
            headers: headers
        })
    }

    async saveTheme(sessionId, theme) {
        const headers = {
            'Content-Type': 'text/html; charset=UTF-8'
        }

        await this.repository.post(`/sessions/${sessionId}/save-template-theme`, theme, {
            headers: headers
        })
    }

    async getColumns(sessionId) {
        return  await this.repository.get(`/sessions/${sessionId}/map-columns`)
    }

    async getSession(sessionId) {
        return  await this.repository.get(`/sessions/${sessionId}`)
    }

    async send(sessionId) {
        return await this.repository.get(`/sessions/${sessionId}/send`)
    }

    async createSession(name) {
        await this.repository.post(`/sessions/?name="${name}"`)
    }

    async saveMapping(sessionId, mappingJSON) {
        await this.repository.post(`sessions/${sessionId}/save-columns-mapping`,
            mappingJSON,
            {
                headers: {
                    "Content-Type":"application/json"
                }
            }
        )
    }
}

export default SessionService