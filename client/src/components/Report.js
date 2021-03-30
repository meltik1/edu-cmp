import React, {useState} from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import {Button, Select, Table, Tag} from "antd";
import {useParams} from "react-router";
import {Link} from "react-router-dom";
import {ArrowLeftOutlined, HomeOutlined} from "@ant-design/icons";
import SelectAtrributes from "./ReportSubComponents/SelectAtrributes";
import BuildReport from "./ReportSubComponents/BuildReport";
import InitializeData from "./ReportSubComponents/InitializeData";
const { Option } = Select;



export default function Report() {

    const [selectedColumn, setColumn] = useState("Email")
    const [reportInfo, setReportInfo] = useState({});
    const [attributes, setAttributes] = useState([]);

    InitializeData('sessions/1/attributes' , setAttributes);
    InitializeData("sessions/1/report", setReportInfo);

    const sessionId = useParams().d;

    const columns = [
        {
            title: '№',
            dataIndex: 'number',
            key: 'number',
            width: 20,
        },
        {
            title: selectedColumn,
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Канал нотификации',
            dataIndex: 'channel',
            key: 'channel',
        },
        {
            title: 'Статус',
            dataIndex: 'status',
            key: 'status',
            render: status => {
                let color = 'green';
                if (status !== 'ok') {
                    color = 'volcano';
                }
                return (
                    <Tag color={color} key={status}>{status}</Tag>
                )
            },
        }
    ];

    const data = BuildReport(reportInfo, selectedColumn)

    function handler(val) {
        setColumn(val)
    }


    const { Option } = Select;

    let params = ["fio", "email", "phone"];

    function handleChange(value) {
        console.log('selected '+ value);
    }

    return (
        <div>
            <MySteps current = {4} />
            <Content style={{ padding: '40px 50px 0' }}>
                <SelectAtrributes change={handler} attributesList={attributes} />
                <div className="site-layout-content">
                    <Select defaultValue={"fio"} onChange={handleChange} style={{width: 120}}>
                        {params.map(value => {
                            return <Option value={value}>{value}</Option>
                        })}
                    </Select>
                    <Table
                        columns={columns}
                        dataSource={data}
                        size="small"
                    />
                </div>
            </Content>
            <div className={"buttons"}>
                <Button type={"secondary"}>
                    <Link to={`/${sessionId}/validation`}> <ArrowLeftOutlined /> Назад </Link>
                </Button>
                <Button type={"primary"}>
                    <Link to={"/sessions"}> Домой <HomeOutlined /> </Link>
                </Button>
            </div>
        </div>
    )
}
