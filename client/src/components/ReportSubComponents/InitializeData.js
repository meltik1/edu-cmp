import React, { useEffect } from "react";
import BaseService from "../../services/BaseService";


export default function InitializeData(url, setData) {
    const t = new BaseService()

    useEffect(() => {
        const fetchData = async () => {
            const result = await t.repository.get(url)
            setData(result.data);
        };

        fetchData();
    }, []);
}