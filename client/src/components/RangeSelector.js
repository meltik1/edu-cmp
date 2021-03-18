import React from "react";
import {Button, InputNumber, List, Space} from "antd";

export default class RangeSelector extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            begin: 0,
            end: 0,
            storage: [],
        };

        this.handleChangeBegin = this.handleChangeBegin.bind(this);
        this.handleChangeEnd = this.handleChangeEnd.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    // handleChange(event) {
    //     let target = event.target;
    //     let beginInput = target.begin;
    //     let endInput = target.end;
    //     let storageValue = this.storage;
    //     this.setState({
    //         begin: beginInput,
    //         end: endInput,
    //         storage: storageValue,
    //     })
    // }

    handleChangeBegin(event) {
        let endValue = this.end;
        let storageValue = this.storage;
        this.setState({
            begin: event.target,
            end: this.end,
            storage: this.storage,
        });
    }

    handleChangeEnd(event) {
        let beginValue = this.begin;
        let storageValue = this.storage;
        this.setState({
            begin: beginValue,
            end: event.target,
            storage: storageValue,
        })
    }

    handleSubmit(event) {
        alert('Диапазон: ' + this.state.begin + ' - ' + this.state.end);
    }

    render() {
        return (
            <div>
                <Space size={150}>
                    <Space direction="vertical" size={20}>
                        <Space size={20}>
                            <div>
                                <p>Начало диапазона</p>
                                <InputNumber
                                    className={"input-box"}
                                    defaultValue={0}
                                    value={this.state.begin}
                                    onChange={this.handleChangeBegin}
                                />
                            </div>
                            <div>
                                <p>Конец диапазона</p>
                                <InputNumber
                                    className={"input-box"}
                                    defaultValue={1}
                                    value={this.state.end}
                                    onChange={this.handleChangeEnd}
                                />
                            </div>
                        </Space>
                        <Button type={"primary"} onClick={() => this.handleSubmit()}>Добавить</Button>
                    </Space>
                    {/*<div>*/}
                    {/*    <p>Добавленные диапазоны</p>*/}
                    {/*    <List*/}
                    {/*        bordered*/}
                    {/*        size="small"*/}
                    {/*        dataSource={rangesSelected}*/}
                    {/*        renderItem={item => <List.Item>{item.begin.toString() + ' - ' + item.end.toString()}</List.Item>}*/}
                    {/*    />*/}
                    {/*</div>*/}
                </Space>
            </div>
        )
    }
}