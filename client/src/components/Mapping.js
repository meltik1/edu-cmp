import React from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import { Button, Table, InputNumber, Space, List } from "antd";
import "./Mapping.css";
import RangeSelector from "./RangeSelector";

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

    const selectedRanges = [
        {
            begin: 0,
            end: 2,
        },
        {
            begin: 4,
            end: 5,
        },
    ]

    const rowSelection = {
        onChange: (selectedRowKeys, selectedRows) => {
            console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
        },
        getCheckboxProps: (record) => ({
            name: record.name,
        }),
    };

    return (
        <div>
            <MySteps current = {1} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <Table
                        rowSelection={{
                            type: "checkbox",
                            ...rowSelection,
                        }}
                        columns={columns}
                        dataSource={data}
                        size={"small"}
                    />
                    {/*<div>*/}
                    {/*    <Space size={150}>*/}
                    {/*        <Space direction="vertical" size={20}>*/}
                    {/*            <Space size={20}>*/}
                    {/*                <div>*/}
                    {/*                    <p>Начало диапазона</p>*/}
                    {/*                    <InputNumber className={"input-box"} defaultValue={0}/>*/}
                    {/*                </div>*/}
                    {/*                <div>*/}
                    {/*                    <p>Конец диапазона</p>*/}
                    {/*                    <InputNumber className={"input-box"} defaultValue={1}/>*/}
                    {/*                </div>*/}
                    {/*            </Space>*/}
                    {/*            <Button type={"primary"}>Добавить</Button>*/}
                    {/*        </Space>*/}
                    {/*        <div>*/}
                    {/*            <p>Добавленные диапазоны</p>*/}
                    {/*            <List*/}
                    {/*                bordered*/}
                    {/*                size="small"*/}
                    {/*                dataSource={selectedRanges}*/}
                    {/*                renderItem={item => <List.Item>{item.begin.toString() + ' - ' + item.end.toString()}</List.Item>}*/}
                    {/*            />*/}
                    {/*        </div>*/}
                    {/*    </Space>*/}
                    {/*</div>*/}
                    <RangeSelector begin={1} end={2} storage={[{begin: 0, end: 1}, {begin: 3, end: 4}]}>
                    </RangeSelector>
                </div>

            </Content>
        </div>
    )
}
