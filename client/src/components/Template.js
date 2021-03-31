import React, { useState } from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import { Col, Row, Input, List, Button } from "antd";
import { useHistory, useParams } from "react-router"
import "./Template.css";
import InitializeData from "./ReportSubComponents/InitializeData";
import { backend } from "../ServerApi";

export default function Template() {

    const sessionId = useParams().id;
    const { TextArea } = Input;
    const [attributes, setAttributes] = useState([]);
    const [text, setText] = useState('');
    const [theme, setTheme] = useState('');
    const listHeader = ['Макросы'];
    let macros = attributes;

    InitializeData(`sessions/${sessionId}/attributes` , setAttributes);

    const goForward = () => {
        saveTemplate();
    }

    const goBack = () => {
        history.push({
            pathname: `/${sessionId}/mapping`
        })
    }

    const onChange = e => {
        setText(e.target.value);
    };

    const onChangeTheme = e => {
        setTheme(e.target.value);
    };

    const history = useHistory();

    const headers = {
        'Content-Type': 'text/html; charset=UTF-8'
    }

    const saveTemplate = async () => {

        await backend.post(`/sessions/${sessionId}/save-template`, text, {
            headers: headers
        })
            .catch(console.log)
        await backend.post(`/sessions/${sessionId}/save-template-theme`, theme, {
            headers: headers
        })
            .catch(console.log)
        history.push({
            pathname: `/${sessionId}/validation`
        })
    }

    return (
        <div>
            <MySteps current={2}/>
            <Content style={{padding: '40px 50px 0'}}>
                <div className="site-layout-content">
                    <Row gutter={16}>
                        <Col span={20}>
                            <Input placeholder={'Тема'} style={{ marginBottom: '12px' }} onChange={onChangeTheme} />
                            <TextArea placeholder={'Текст сообщения'} rows={14} onChange={onChange} />
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
                <Button type={"secondary"} onClick={goBack}>
                    Назад
                </Button>
                <Button type={"primary"} onClick={goForward}>
                    Далее
                </Button>
            </div>
        </div>
    )
}
