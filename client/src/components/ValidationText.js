import React, {useEffect, useState} from "react";
import {backend} from "../ServerApi";

export default function ValidationText()  {

    const [text, setText] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            // нужно изменить как только появится url с id сессий
            const result = await backend.get("sessions/1/validation")
            setText(result.data);
        };

        fetchData();
    }, []);


    return text

}