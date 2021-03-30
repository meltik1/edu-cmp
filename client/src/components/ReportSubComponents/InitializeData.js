import React, { useEffect } from "react";
import { backend } from "../../ServerApi";


export default function InitializeData(url, setData) {
    useEffect(() => {
        const fetchData = async () => {
            const result = await backend.get(url)
            setData(result.data);
        };

        fetchData();
    }, []);
}