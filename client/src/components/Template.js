import React from "react";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";
import { Input } from "antd";

export default function Template() {

    const { TextArea } = Input;

    return (
        <div>
            <MySteps current = {2} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <Input placeholder={'Тема'} style={{ marginBottom: '12px' }} />
                    <TextArea placeholder={'Текст сообщения'} rows={14} />
                </div>
            </Content>
        </div>
    )
}
