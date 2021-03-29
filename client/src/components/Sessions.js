import React, {useEffect, useState} from "react";
import { Button, Input, Modal, Table, Tag } from "antd";
import { PlusOutlined } from '@ant-design/icons';
import { Content } from "antd/es/layout/layout";
import "../App.css";
import "../ServerApi.js"
import { backend } from "../ServerApi";
import { useHistory } from "react-router";

export default function GetSessions() {
    const sessionStatus = new Map([
        ['FILESELECTION', 'Загрузка файла'],
        ['MAPPING', 'Маппинг параметров'],
        ['TEMPLATE', 'Заполнение шаблона'],
        ['VALIDATION', 'Валидация'],
        ['REPORT', 'Отчет'],
    ]);

    let [dataSource, setDataSource] = useState([]);

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
        createSession();
        return dataSource;
    };

    const createSession = () => {
        backend.post('sessions/create?name="' + input +'"')
            .then(getAllSessions)
            .catch(console.log)
    }

    const getAllSessions = () => {
        backend.get('sessions')
            .then((res) => {
                const results = res.data.map(row => ({
                    key: row.id,
                    name: row.name.replaceAll('"', ""),
                    date: row.date,
                    status: sessionStatus.get(row.status)
                }));
                setDataSource(results)
            })
            .catch(console.log)
        return dataSource;
    }

    const history = useHistory();

    const onClickRow = (record) => {
        return {
            onClick: () => {
                history.push({
                    pathname:  record.key + "/pick-file",
                });
            },
        };

    }

    const [input, setInput] = useState('');
    const nameChange = (input) => {
        setInput(input.target.value)
    }

    useEffect( () => {
        getAllSessions();
    }, []);

    const handleCancel = () => {
        setIsModalVisible(false);
    };

    const callServer = async () => {
        const resp = await backend.get(
            "sessions",
        )
        console.log(resp.data);
    }

    return (
        <Content style={{ padding: '40px 50px 0' }}>
            <div className={"site-layout-content-sessions"}>
                <Button style={{ marginBottom: 20 }}
                        type="primary"
                        shape="round"
                        onClick={showModal}
                        icon={<PlusOutlined />}>
                    Новая сессия
                </Button>
                <Modal title="Новая сессия" visible={isModalVisible} onOk={handleOk} onCancel={handleCancel}>
                    <Input placeholder={"Название"} onChange={nameChange}/>
                </Modal>
                <div>
                    <Table dataSource={dataSource} columns={columns} onRow={onClickRow} />
                </div>
            </div>
            <Button onClick={() => callServer()}>Test</Button>
        </Content>
    );
}