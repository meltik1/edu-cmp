import React, { useState } from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import { Divider } from "antd";
import './Validation.css';
import InitializeData from "./ReportSubComponents/InitializeData";



export default function Validation() {
    const theme = 'Обучение в Учебном Центре Netсracker 2020'
    const [text, setText] =  useState("");

    InitializeData("sessions/1/validation", setText)

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
        </div>
    )
}
