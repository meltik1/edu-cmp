import axios from "axios";
import Settings from "./backend.settings.json"

export const backend = axios.create(
    {
        baseURL: Settings.backend.url,
        headers: {
            "Authorization": Settings.backend.auth
        }
    }
)