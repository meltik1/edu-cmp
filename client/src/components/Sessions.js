import React from "react";
import {Button, Input, Popover, Table, Tag} from "antd";
import {PlusOutlined} from '@ant-design/icons';
import {Content} from "antd/es/layout/layout";

export default function GetSessions() {
    const dataSource = [
        {
            key: '1',
            name: 'Приглашение на онлайн-этап обучения',
            date: '2020.07.05',
            status: 'Завершено успешно'
        },
        {
            key: '2',
            name: 'Приглашение на очный этап обучения',
            date: '2020.09.07',
            status: 'Завершено с ошибками'
        },
        {
            key: '3',
            name: 'Ещё одна сессия',
            date: '2021.02.23',
            status: 'Заполнение шаблона'
        }
    ];

    const columns = [
        {
            title: 'Название',
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Дата',
            dataIndex: 'date',
            key: 'date',
            defaultSortOrder: 'descend',
            sorter: (a, b) => Math.abs(Date.parse(a.date) - Date.parse(b.date)),
        },
        {
            title: 'Статус',
            dataIndex: 'status',
            key: 'status',
            render: status => {
                let color = 'green';
                if (status === 'Завершено с ошибками') {
                    color = 'volcano';
                }
                if (status === 'Заполнение шаблона') {
                    color = 'blue';
                }
                return (
                    <Tag color={color} key={status}>{status}</Tag>
                )
            },
        },

    ]

    return (
        <Content style={{ padding: '40px 50px 0' }}>
            <div >
                <Popover content={<div><Input addonAfter={<a>Далее</a>} placeholder="Basic usage" /></div>}
                         title="Title"
                         trigger="click"
                         placement="right">
                    <Button style={{ marginBottom: 20 }}
                            type="primary"
                            shape="round"
                        // onClick={() => }
                            icon={<PlusOutlined />}>
                        Новая сессия
                    </Button>
                </Popover>
                <div>
                    <Table dataSource={dataSource} columns={columns} />
                </div>
            </div>
        </Content>
    );
}