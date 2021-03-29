import React from "react";
import { Button, Form, Input, List, Space } from "antd";
import { MinusCircleOutlined } from "@ant-design/icons";

export default class RangeSelector extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            ranges: [],
        }
    }

    newId = () => {
        let maxId = 0;
        for (let i = 0; i < this.state.ranges.length; i++) {
            if (maxId < this.state.ranges[i].id) {
                maxId = this.state.ranges[i].id;
            }
        }
        return maxId + 1;
    }

    onFinish = (values) => {
        this.setState(state => {
            if (isNaN(values.rangeBegin) || isNaN(values.rangeEnd)) {
                alert("Введите число");
                return {
                    ranges: state.ranges
                }
            }
            for (let i = 0; i < this.state.ranges.length; i++) {
                if (values.rangeBegin === this.state.ranges[i].begin.toString() &&
                    values.rangeEnd === this.state.ranges[i].end.toString()) {
                    alert("Диапазон уже добавлен");
                    return {
                        ranges: state.ranges
                    };
                }
            }
            const newRanges = state.ranges.concat({
                id: this.newId(),
                begin: values.rangeBegin,
                end: values.rangeEnd
            })
            return {
                ranges: newRanges
            };
        });
    };

    onFinishFailed = (errorInfo) => {
        console.log(errorInfo);
    };

    handleDelete = (id) => {
        this.setState(state => {
            const newRanges = state.ranges.filter(item => item.id !== id)
            return {
                ranges: newRanges
            };
        });
    }

    next = () => {
        let output = [];
        for (let i = 0; i < this.state.ranges.length; i++) {
            output.push(this.state.ranges[i].begin.toString() + ' - ' + this.state.ranges[i].end.toString() )
        }
        return output;
    }

    render() {
        return (
            <div>

                <Space size={150} align={"Form"}>
                    <div>
                        <p>Новый диапазон:</p>
                        <Form
                            initialValues={{begin: 1, end: 1}}
                            onFinish={this.onFinish}
                            onFinishFailed={this.onFinishFailed}
                        >
                            <Form.Item
                                label={"Начало"}
                                name={"rangeBegin"}
                                rules={[
                                    {
                                        required: true,
                                        message: "Введите начало диапазона",
                                    }
                                ]}
                            >
                                <Input />
                            </Form.Item>

                            <Form.Item
                                label={"Конец"}
                                name={"rangeEnd"}
                                rules={[
                                    {
                                        required: true,
                                        message: "Введите конец диапазона",
                                    }
                                ]}
                            >
                                <Input />
                            </Form.Item>

                            <Form.Item>
                                <Button type={"secondary"} htmlType={"submit"}>
                                    Добавить
                                </Button>
                            </Form.Item>
                        </Form>
                    </div>

                    <div>
                        <p>Добавленные диапазоны</p>
                        <List
                            bordered
                            size="small"
                            dataSource={this.state.ranges}
                            renderItem={item => (
                                <List.Item actions={[
                                    <MinusCircleOutlined onClick={() => {this.handleDelete(item.id)}} />
                                ]}>
                                    {item.begin.toString() + ' - ' + item.end.toString()}
                                </List.Item>
                            )}
                        />
                    </div>
                </Space>
            </div>
        )
    }
}
