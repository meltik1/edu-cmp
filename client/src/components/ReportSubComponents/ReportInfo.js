import {backend} from "../../ServerApi";
import {useEffect, useState} from "react";

export default function ReportInfo() {

    const [report, setReport] = useState({});



    useEffect(() => {
        const fetchData = async () => {
            // нужно изменить как только появится url с id сессий
            const result = await backend.get("sessions/1/report")
            const tmp = result.data

            setReport(tmp);
        };

        fetchData();
    }, []);

    return report
}