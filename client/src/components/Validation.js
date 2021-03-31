import React, { useState } from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import {Button, Divider} from "antd";
import './Validation.css';
import InitializeData from "./ReportSubComponents/InitializeData";
import { useHistory, useParams } from "react-router";
import { backend } from "../ServerApi";

export default function Validation() {

    const sessionId = useParams().id
    const [theme, setTheme] =  useState("");
    const [text, setText] =  useState("");

    InitializeData(`sessions/${sessionId}/validation`, setText);
    InitializeData(`sessions/${sessionId}/get-theme`, setTheme);

    const history = useHistory();

    const goBack = () => {
        history.push({
            pathname: `/${sessionId}/template`
        })
    }

    const goForward = async () => {
        await backend.get(`/sessions/${sessionId}/send`)
            .catch(console.log)
        history.push({
            pathname: `/${sessionId}/report`,
        })
    }

    return (
        <div>
            <MySteps current = {3} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className={"site-layout-content"}>
                    <p><b> {theme} </b></p>
                    <Divider />
                    <div className={"text"}>{text}</div>
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
