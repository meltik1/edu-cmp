import {Content} from "antd/es/layout/layout";
import {Button, Table} from "antd";
import {useHistory, useParams} from "react-router";
import React, {useState, useEffect} from "react";
import UserService from "../services/UserService";

export default function UserList() {

    const userService = new UserService();

    const goHome = () => {
        history.push({
            pathname: `/sessions`
        })
    }

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
            const result =  await userService.getAllUsers()
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


        result.push(
            {
                    title: 'имя пользователя',
                    dataIndex: names[0],
                    key: names[0],
                }
            )
        result.push(
            {
                title: 'ФИО',
                dataIndex: names[1],
                key: names[1],
            }
        )
        result.push({
            title: 'Отправить сообщение',
            dataIndex: 'Отправить сообщение',
            render: (_, record) => <a onClick={() => {history.push("sendToUser/" + record.userName)} }>Перейти</a>,
        })

        result.push({
            title: 'К личной странице',
            dataIndex: 'К личной транице',
            render: (_, record) => <a onClick={() => {history.push("user/" + record.userName)} }>На страницу</a>,
        })

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
                <Button type={"primary"} onClick={goHome}>
                    К сессиям
                </Button>
            </Content>
        </div>
    )
}