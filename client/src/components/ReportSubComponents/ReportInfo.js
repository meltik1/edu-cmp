import {backend} from "../../ServerApi";
import {useEffect, useState} from "react";

export default function ReportInfo() {

    const [report, setReport] = useState({});



    useEffect(() => {
        const fetchData = async () => {
            const result = await backend.get("sessions/1/report")
            const tmp = result.data

            setReport(tmp);
        };

        fetchData();
    }, []);
    
    return report
}