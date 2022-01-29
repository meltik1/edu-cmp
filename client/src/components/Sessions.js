import React, { useEffect, useState } from "react";
import { Button, Input, Modal, Table, Tag } from "antd";
import { PlusOutlined } from '@ant-design/icons';
import {Content, Header} from "antd/es/layout/layout";
import "../static/styles/App.css";
import { useHistory } from "react-router";
import TokenStorage from "../services/TokenStorage";
import SessionService from "../services/SessionService";

export default function GetSessions() {
    const sessionStatus = new Map([
        ['FILESELECTION', 'Загрузка файла'],
        ['MAPPING', 'Маппинг параметров'],
        ['TEMPLATE', 'Заполнение шаблона'],
        ['VALIDATION', 'Валидация'],
        ['REPORT', 'Отчет'],
    ]);


    const sessionStatusToPath = new Map([
        ['Загрузка файла', '/pick-file'],
        ['Маппинг параметров', '/mapping'],
        ['Заполнение шаблона', '/template'],
        ['Валидация', '/validation'],
        ['Отчет', '/report'],
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

    const sessionService = new SessionService();

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
        sessionService.createSession(input)
            .then(getAllSessions)
            .catch(console.log)
    }

    const getAllSessions = () => {
        sessionService.getAllSessions()
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
                    pathname:  record.key + sessionStatusToPath.get(record.status)
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

    function logout() {
        TokenStorage.clearToken()
        TokenStorage.setIsAuthenticated(false)
        history.push("/login")
    }

    function toUserList() {
        history.push("/users")
    }

    return (

        <Content style={{ padding: '40px 50px 0' }}>
            <div className={"site-layout-content-sessions"}>
                <Button onClick ={logout} style={{marginBottom:20, marginLeft:50, marginRight: 500}} shape="round" type="primary">Выйти</Button>
                <Button onClick ={toUserList} style={{marginBottom:20, marginLeft:300}} shape="round" type="primary">К списку пользователей</Button>
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
        </Content>
    );
}
