import React, {useEffect, useState} from "react";
import {backend} from "../ServerApi";


export default function Attributes() {

    const [data, setData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await backend.get("sessions/1/attributes")
            setData(result.data);
        };

        fetchData();
    }, []);


    return data

}