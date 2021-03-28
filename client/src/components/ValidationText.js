import React, {useEffect, useState} from "react";
import {backend} from "../ServerApi";

export default function ValidationText()  {

    const [text, setText] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            const result = await backend.get("sessions/1/validation")
            setText(result.data);
        };

        fetchData();
    }, []);

    debugger
    return text

}