import React from "react";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";
import {Col, Row, Input, List, Button} from "antd";
import {Link} from "react-router-dom";
import "./Template.css";
import {ArrowLeftOutlined, ArrowRightOutlined} from "@ant-design/icons";

export default function Template() {

    const listHeader = ['Макросы'];

    let macros = ['fio', 'login', 'password'];

    const { TextArea } = Input;

    return (
        <div>
            <MySteps current = {2} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <Row gutter={16}>
                        <Col span={20}>
                            <Input placeholder={'Тема'} style={{ marginBottom: '12px' }} />
                            <TextArea placeholder={'Текст сообщения'} rows={14} />
                        </Col>
                        <Col span={4}>
                            <List
                                size={"small"}
                                bordered
                                dataSource={listHeader}
                                renderItem={item => (
                                    <List.Item><b>{item}</b></List.Item>
                            )}
                            />
                            <List
                                size={"small"}
                                bordered
                                dataSource={macros}
                                renderItem={item => (
                                    <List.Item>{item}</List.Item>
                                )}
                            />
                        </Col>
                    </Row>
                </div>
            </Content>
            <div className={"buttons"}>
                <Button type={"secondary"}>
                    <Link to={"/mapping"}> <ArrowLeftOutlined /> Назад </Link>
                </Button>
                <Button type={"primary"}>
                    <Link to={"/validation"}> Далее <ArrowRightOutlined /> </Link>
                </Button>
            </div>
        </div>
    )
}
