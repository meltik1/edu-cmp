import React from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import {Table, Select, Button} from "antd";
import "./Mapping.css";
import RangeSelector from "./RangeSelector";
import {Link} from "react-router-dom";
import {ArrowLeftOutlined, ArrowRightOutlined} from "@ant-design/icons";

export default function Mapping() {
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
            title: 'E-mail',
            dataIndex: 'email',
            key: 'email',
        },
        {
            title: 'Телефон',
            dataIndex: 'phone',
            key: 'phone',
        },
        {
            title: 'Telegram',
            dataIndex: 'telegram',
            key: 'telegram',
        }
    ];

    const data = [
        {
            key: '1',
            number: '1',
            name: 'Aaron Aldenburg',
            email: 'aa@mail.ru',
            phone: '111-111',
            telegram: '@aa'
        },
        {
            key: '2',
            number: '2',
            name: 'Berthold Blake',
            email: 'bb@mail.ru',
            phone: '222-222',
            telegram: '@bb'
        },
        {
            key: '3',
            number: '3',
            name: 'Carl Clopp',
            email: 'cc@mail.ru',
            phone: '333-333',
            telegram: '@cc'
        },
        {
            key: '4',
            number: '4',
            name: 'Dick Donovan',
            email: 'dd@mail.ru',
            phone: '444-444',
            telegram: '@dd'
        },
        {
            key: '5',
            number: '5',
            name: 'Emanuel East',
            email: 'ee@mail.ru',
            phone: '555-555',
            telegram: '@ee'
        },
    ];

    let params = ["fio", "email", "phone"];

    const { Option } = Select;

    function handleChange(value) {
        console.log('selected ' + value);
    }

    return (
        <div>
            <MySteps current = {1} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <Select
                        mode={"multiple"}
                        defaultValue={["fio", "email"]}
                        onChange={handleChange}
                        style={{width: '30%'}}
                    >
                        {params.map(value => {
                            return <Option value={value}>{value}</Option>
                        })}
                    </Select>
                    <Table
                        columns={columns}
                        dataSource={data}
                        size={"small"}
                    />
                    <RangeSelector />
                </div>
            </Content>
            <div className={"buttons"}>
                <Button type={"secondary"}>
                    <Link to={"/pick-file"}> <ArrowLeftOutlined /> Назад </Link>
                </Button>
                <Button type={"primary"}>
                    <Link to={"/template"}> Далее <ArrowRightOutlined /> </Link>
                </Button>
            </div>
        </div>
    )
}
