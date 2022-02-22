import React, { useState } from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import { Col, Row, Input, List, Button } from "antd";
import { useHistory, useParams } from "react-router"
import "../static/styles/Template.css";
import InitializeData from "./ReportSubComponents/InitializeData";
import UserService from "../services/UserService";


export default function SendToUser() {

    const userId = useParams().id;
    const { TextArea } = Input;
    const [attributes, setAttributes] = useState({});
    const [text, setText] = useState('');
    const [theme, setTheme] = useState('');
    const listHeader = ['Макросы'];
    let macros = attributes;

    InitializeData(`/user/getAttributes/${userId}` , setAttributes);

    const goForward = () => {
        saveTemplate();
    }

    const goBack = () => {
        history.push({
            pathname: `/users`
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
        'Content-Type': 'application/json'
    }

    const userService = new UserService();

    const saveTemplate = async () => {
        const body = {"template" : text, "theme" : theme}
        await userService.sendToUser(text, theme, userId);
        // await backend.post(`/sessions/${sessionId}/save-template-theme`, theme, {
        //     headers: headers
        // })
        //     .catch(console.log)
        history.push({
            pathname: `/users`
        })
    }

    return (
        <div>
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
                                dataSource={Object.keys(macros)}
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
