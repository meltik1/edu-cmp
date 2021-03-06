import React, { useState, useEffect} from 'react';
import { Button} from 'antd';
import {Content} from "antd/es/layout/layout";
import {useHistory, useParams} from "react-router";
import UserService from "../services/UserService";
import EditableTable from "./UserPageSubComponents/EditableTable";
const EditableContext = React.createContext(null);

export  function UserPage() {

    const goHome = () => {
        history.push("/users")
    }

    const [tableData, setTableData] = useState(
        {
            generated: false,
            dataset: []
        }
    );
    const userId = useParams().id;
    const [data, setData] = useState();
    const [columns, setColumns] = useState([]);
    const userService = new UserService();


    useEffect(() => {
        const fetchData = async () => {
            const result = await userService.getUserAttributes(userId)
            console.log(result.data)
            setData(result.data);
        };

        fetchData();
    }, []);


    if (data !== undefined && !tableData.generated) {

        const  mapped = []
        let i = 0
        for (const property in data) {
            let x = {}
            x.key = i
            x.attributes = property
            x.values = data[property]
            x.number = i+1
            i++
            console.log(x)
            mapped.push(x)
        }
        console.log("mapped" + mapped)
        let tableDataNew = {}
        tableDataNew.generated = true
        tableDataNew.dataset = mapped
        setTableData(
            tableDataNew
        )
    }

    const history = useHistory();

    const generateColumns = () => {

        const result = [];

        // add rownum column
        result.push({
            title: '№',
            dataIndex: 'number',
            key: 'number',
            width: 20,
        })


        result.push(
            {
                title: 'атрибуты',
                dataIndex: 'attributes',
                key: 'attributes',
                editable: true,
            })

        result.push(
            {
                title: 'значения',
                dataIndex: 'values',
                key: 'values',
                editable: true,
            })

        return result;
    }

    return (
        <div>
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <EditableTable
                        columns={generateColumns(columns)}
                        dataSource={tableData.dataset}
                        userId={userId}
                    />
                </div>
                <Button type={"primary"} onClick={goHome}>
                    К списку пользователей
                </Button>
            </Content>
        </div>
    )
}



