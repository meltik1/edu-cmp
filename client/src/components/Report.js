import React, {useState} from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import {Popconfirm, Button, Table, Tag } from "antd";
import { useHistory, useParams } from "react-router";
import SelectAtrributes from "./ReportSubComponents/SelectAtrributes";
import BuildReport from "./ReportSubComponents/BuildReport";
import InitializeData from "./ReportSubComponents/InitializeData";
import UserService from "../services/UserService";

export default function Report() {

    const sessionId = useParams().id;
    const [selectedColumn, setColumn] = useState("Email")
    const [reportInfo, setReportInfo] = useState({});
    const [attributes, setAttributes] = useState([]);

    InitializeData(`sessions/${sessionId}/attributes` , setAttributes);
    InitializeData(`sessions/${sessionId}/report`, setReportInfo);

    const history = useHistory();
    const userService = new UserService()

    const confirm =  async () => {
        await userService.saveUsersToDataBase(sessionId);
    }

    const goBack = () => {
        history.push({
            pathname: `/${sessionId}/validation`
        })
    }

    const goHome = () => {
        history.push({
            pathname: `/sessions`
        })
    }

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

    return (
        <div>
            <MySteps current = {4} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <SelectAtrributes change={handler} attributesList={attributes} />
                    <Table
                        columns={columns}
                        dataSource={data}
                        size="small"
                    />
                </div>
            </Content>
            <div className={"buttons"}>
                <Popconfirm placement="topLeft" title="Хотите создать аккаунты для студентов?" onConfirm={confirm} okText="Да" cancelText="Нет">
                <Button type={"secondary"}>
                    Сохранить В базу
                </Button>
                </Popconfirm>
                <Button type={"primary"} onClick={goHome}>
                    Домой
                </Button>
            </div>
        </div>
    )
}
