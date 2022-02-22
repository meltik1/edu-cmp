import {Button, Form, Popconfirm, Table} from "antd";
import React from "react";
import UserService from "../../services/UserService";
import EditableRow from "./EditableRow";
import EditableCell from "./EditableCell";

class EditableTable extends React.Component {


    constructor(props) {
        super(props);
        this.columns = props.columns
        this.userId = props.userId
        this.userService = new UserService()
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
        this.dataSource = props.dataSource
        this.count = props.dataSource.length
        this.state = {
            dataSource: this.dataSource,
            count: this.count
        };

    }



    handleDelete = async (record) => {
        let body =   record.attributes
        await this.userService.deleteUserAttribute(this.userId, body)
        const dataSource = [...this.state.dataSource];

        this.setState({
            dataSource: dataSource.filter((item) => item.key !== record.key),
        });
    };

    handleSaveToDb = async (record) => {
        const key = record.attributes
        let body =   {[key] : record.values }
        await this.userService.saveAttributeForUser(this.userId, body)
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
        console.log("rerender called")
        console.log("Rerender props ``" + this.props.dataSource)
        this.state.dataSource = { ...this.props.dataSource, ...this.state.dataSource}
        this.state.length = this.props.dataSource.length
        const { dataSource } = this.state;
        console.log(dataSource)
        const {dt} = {
            dataSource: this.props.dataSource,
            length: this.props.dataSource.length
        }

        console.log(dt)
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

export default EditableTable;
