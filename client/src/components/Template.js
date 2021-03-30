import React, { useState } from "react";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";
import {Col, Row, Input, List, Button} from "antd";
import {useParams} from "react-router"
import {Link} from "react-router-dom";
import "./Template.css";
import {ArrowLeftOutlined, ArrowRightOutlined} from "@ant-design/icons";
import InitializeData from "./ReportSubComponents/InitializeData";

export default function Template() {

    const sessionId = useParams().id;

    const listHeader = ['Макросы'];

    let macros = ['fio', 'login', 'password'];

    const { TextArea } = Input;
    const [attributes, setAttributes] = useState([]);

    InitializeData(`sessions/${sessionId}/attributes` , setAttributes);

    return (
        <div>
            <MySteps current={2}/>
            <Content style={{padding: '40px 50px 0'}}>
                <List
                    size="large"
                    header={<div>Аттрибуты для шаблона</div>}
                    bordered
                    dataSource={attributes}
                    renderItem={item => <List.Item>{item}</List.Item>}
                />
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
                    <Link to={`/${sessionId}/mapping`}> <ArrowLeftOutlined /> Назад </Link>
                </Button>
                <Button type={"primary"}>
                    <Link to={`/${sessionId}/validation`}> Далее <ArrowRightOutlined /> </Link>
                </Button>
            </div>
        </div>
    )
}
