import React, { useState } from "react";
import { Button, Input, Modal, Table, Tag } from "antd";
import { PlusOutlined } from '@ant-design/icons';
import { Content } from "antd/es/layout/layout";

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

    const [isModalVisible, setIsModalVisible] = useState(false);

    const showModal = () => {
        setIsModalVisible(true);
    };

    const handleOk = () => {
        setIsModalVisible(false);
    };

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    return (
        <Content style={{ padding: '40px 50px 0' }}>
            <div >
                <Button style={{ marginBottom: 20 }}
                        type="primary"
                        shape="round"
                        onClick={showModal}
                        icon={<PlusOutlined />}>
                    Новая сессия
                </Button>
                <Modal title="Новая сессия" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
                    <Input placeholder={"Название"} />
                </Modal>
                <div>
                    <Table dataSource={dataSource} columns={columns} />
                </div>
            </div>
        </Content>
    );
}