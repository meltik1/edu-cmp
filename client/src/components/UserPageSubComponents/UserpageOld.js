import React, { useContext, useState, useEffect, useRef } from 'react';
import { Table, Input, Button, Popconfirm, Form } from 'antd';
import ReactDOM from "react-dom";
import {Content} from "antd/es/layout/layout";
import {useHistory, useParams} from "react-router";
import UserService from "../../services/UserService";
const EditableContext = React.createContext(null);

export function UserPageOld() {
    var service = new UserService();
    const EditableRow = ({ index, ...props }) => {
        const [form] = Form.useForm();
        return (
            <Form form={form} component={false}>
                <EditableContext.Provider value={form}>
                    <tr {...props} />
                </EditableContext.Provider>
            </Form>
        );
    };

    const EditableCell = ({
                              title,
                              editable,
                              children,
                              dataIndex,
                              record,
                              handleSave,
                              ...restProps
                          }) => {
        const [editing, setEditing] = useState(false);
        const inputRef = useRef(null);
        const form = useContext(EditableContext);
        useEffect(() => {
            if (editing) {
                inputRef.current.focus();
            }
        }, [editing]);

        const toggleEdit = () => {
            setEditing(!editing);
            form.setFieldsValue({
                [dataIndex]: record[dataIndex],
            });
        };

        const save = async () => {
            try {
                const values = await form.validateFields();
                toggleEdit();
                handleSave({ ...record, ...values });
            } catch (errInfo) {
                console.log('Save failed:', errInfo);
            }
        };

        let childNode = children;

        if (editable) {
            childNode = editing ? (
                <Form.Item
                    style={{
                        margin: 0,
                    }}
                    name={dataIndex}
                    rules={[
                        {
                            required: true,
                            message: `${title} is required.`,
                        },
                    ]}
                >
                    <Input ref={inputRef} onPressEnter={save} onBlur={save} />
                </Form.Item>
            ) : (
                <div
                    className="editable-cell-value-wrap"
                    style={{
                        paddingRight: 24,
                    }}
                    onClick={toggleEdit}
                >
                    {children}
                </div>
            );
        }

        return <td {...restProps}>{childNode}</td>;
    };

    class EditableTable extends React.Component {
        constructor(props) {
            super(props);
            debugger
            this.columns = props.columns
            console.log("constructor worked")
            this.columns.push(
                {
                    title: 'operation',
                    dataIndex: 'operation',
                    render: (_, record) =>
                        this.state.dataSource.length >= 1 ? (
                            <Popconfirm title="Sure to delete?" onConfirm={() => this.handleDelete(record)}>
                                <a>Delete</a>
                            </Popconfirm>
                        ) : null,
                }
            )
            this.columns.push(
                {
                    title: 'operation create',
                    dataIndex: 'operation create',
                    render: (_, record) =>
                        (
                            <Popconfirm title="Хотите сохранить?" onConfirm={() => this.handleSaveToDb(record)}>
                                <a>Save</a>
                            </Popconfirm>
                        )
                }
            )
            console.log("length " + props.dataSource.length)
            this.state = {
                dataSource: props.dataSource,
                count: props.dataSource.length
            };
        }


        handleDelete = async (record) => {
            let body =   record.attributes
            const headers = {
                'Content-Type': 'text/html; charset=UTF-8'
            }
            await service.deleteUserAttribute(userId,body)
            const dataSource = [...this.state.dataSource];
            this.setState({
                dataSource: dataSource.filter((item) => item.key !== record.key),
            });
        };
        handleSaveToDb = async (record) => {
            const key = record.attributes
            let body =   {[key] : record.values }
            const headers = {
                'Content-Type': 'application/json'
            }
            await service.saveAttributeForUser(userId, body)
        }
        handleAdd = () => {
            const { count, dataSource } = this.state;
            console.log(count)
            const newData = {
                key: count,
                number: count+1,
                attributes: `Новый атрибут ${count}`,
                values: `значение нового атрибута ${count}`,
            };
            this.setState({
                dataSource: [...dataSource, newData],
                count: count + 1,
            });
        };
        handleSave = (row) => {
            const newData = [...this.state.dataSource];
            const index = newData.findIndex((item) => row.key === item.key);
            const item = newData[index];
            newData.splice(index, 1, { ...item, ...row });
            this.setState({
                dataSource: newData,
            });
        };

        render() {
            const { dataSource } = this.state;
            const components = {
                body: {
                    row: EditableRow,
                    cell: EditableCell,
                },
            };
            const columns = this.columns.map((col) => {
                if (!col.editable) {
                    return col;
                }

                return {
                    ...col,
                    onCell: (record) => ({
                        record,
                        editable: col.editable,
                        dataIndex: col.dataIndex,
                        title: col.title,
                        handleSave: this.handleSave,
                    }),
                };
            });
            return (
                <div>
                    <Button
                        onClick={this.handleAdd}
                        type="primary"
                        style={{
                            marginBottom: 16,
                        }}
                    >
                        Добавить строку
                    </Button>
                    <Table
                        components={components}
                        rowClassName={() => 'editable-row'}
                        bordered
                        dataSource={dataSource}
                        columns={columns}
                    />
                </div>
            );
        }
    }

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

    useEffect(() => {
        const fetchData = async () => {
            const result = await service.getUserAttributes(userId)
            console.log(result.data)
            setData(result.data);
        };

        fetchData();
    }, []);

    if (data !== undefined && !tableData.generated) {

        // parse data
        // const mapped = data.map((x, index) => {
        //     maxNumber = Object.keys(x).length;
        //     setColumns(Object.keys(x));
        //     let newX = x;
        //     newX.key = index + 1;
        //     newX.number = index + 1;
        //
        //     return newX;
        // });
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
        setTableData(
            {
                generated: true,
                dataset: mapped
            }
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
                    />
                </div>
            </Content>
        </div>
    )
}
