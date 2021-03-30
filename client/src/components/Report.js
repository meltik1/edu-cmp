import React from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import {Button, Select, Table, Tag} from "antd";
import {useParams} from "react-router";
import {Link} from "react-router-dom";
import {ArrowLeftOutlined, HomeOutlined} from "@ant-design/icons";

export default function Report() {

    const sessionId = useParams().d;

    const columns = [
        {
            title: '№',
            dataIndex: 'number',
            key: 'number',
            width: 20,
        },
        {
            title: 'ФИО',
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
                if (status === 'Ошибка') {
                    color = 'volcano';
                }
                return (
                    <Tag color={color} key={status}>{status}</Tag>
                )
            },
        }
    ];

    const data = [
        {
            key: '1',
            number: '1',
            name: 'Aaron Aldenburg',
            channel: 'E-mail: aa@mail.ru',
            status: 'Успешно'
        },
        {
            key: '2',
            // number: '1',
            // name: 'Aaron Aldenburg',
            number: '',
            name: '',
            channel: 'tg: @aa',
            status: 'Ошибка'
        },
        {
            key: '3',
            number: '2',
            name: 'Berthold Blake',
            channel: 'email: bb@mail.ru',
            status: 'Успешно'
        },
        {
            key: '4',
            number: '3',
            name: 'Carl Clopp',
            channel: 'email: cc@mail.ru',
            status: 'Успешно'
        },
        {
            key: '5',
            // number: '3',
            // name: 'Carl Clopp',
            number: '',
            name: '',
            channel: 'telegram: @cc',
            status: 'Ошибка'
        },
        {
            key: '6',
            number: '4',
            name: 'Dick Donovan',
            channel: 'email: dd@mail.ru',
            status: 'Ошибка'
        },
        {
            key: '7',
            number: '5',
            name: 'Emanuel East',
            channel: 'email: ee@mail.ru',
            status: 'Успешно'
        },
    ]

    const { Option } = Select;

    let params = ["fio", "email", "phone"];

    function handleChange(value) {
        console.log('selected '+ value);
    }

    return (
        <div>
            <MySteps current = {4} />
            <Content style={{ padding: '40px 50px 0' }}>
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
