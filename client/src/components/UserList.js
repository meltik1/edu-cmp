import {backend} from "../ServerApi";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";
import {Button, Table} from "antd";
import RangeSelector from "./RangeSelector";
import {useHistory, useParams} from "react-router";
import {Link} from "react-router-dom";
import {ArrowLeftOutlined, ArrowRightOutlined} from "@ant-design/icons";
import React, {useState, useEffect} from "react";

export default function UserList() {


    let maxNumber = 0;
    const [tableData, setTableData] = useState(
        {
            generated: false,
            dataset: []
        }
    );

    const [data, setData] = useState();
    const [columns, setColumns] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            const result = await backend.get(`/user/getAll`)
            setData(result.data);
        };

        fetchData();
    }, []);

    if (data !== undefined && !tableData.generated) {

        // parse data
        const mapped = data.map((x, index) => {
            maxNumber = Object.keys(x).length;
            setColumns(Object.keys(x));
            let newX = x;
            newX.key = index + 1;
            newX.number = index + 1;

            return newX;
        });

        console.log(mapped)
        setTableData(
            {
                generated: true,
                dataset: mapped
            }
        )

    }

    const history = useHistory();

    const generateColumns = (names) => {

        const result = [];

        // add rownum column
        result.push({
            title: '№',
            dataIndex: 'number',
            key: 'number',
            render: (text, record) => <a onClick={() => {history.push("sendToUser/" + record.userName)} }>{text}</a>,
            width: 20,
        })

        for (let i = 0; i < names.length; i++) {
            result.push(
                {
                    title: names[i],
                    dataIndex: names[i],
                    key: names[i],
                }
            )
        }

        return result;
    }

    return (
        <div>
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <Table
                        columns={generateColumns(columns)}
                        dataSource={tableData.dataset}
                        size={"small"}
                    />
                </div>
            </Content>
        </div>
    )
}