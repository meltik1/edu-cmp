import React, { useState } from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import { Input, List } from "antd";
import InitializeData from "./ReportSubComponents/InitializeData";

export default function Template() {

    const { TextArea } = Input;
    const [attributes, setAttributes] = useState([]);

    InitializeData('sessions/1/attributes' , setAttributes);

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
                    <Input placeholder={'Тема'} style={{marginBottom: '12px'}}/>
                    <TextArea placeholder={'Текст сообщения'} rows={14}/>
                    
                </div>
            </Content>
        </div>
    )
}
