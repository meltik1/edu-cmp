import React from "react";
import MySteps from "./MySteps";
import { Upload } from 'antd';
import { Content } from "antd/es/layout/layout";
import { InboxOutlined } from '@ant-design/icons';

export default function PickFile() {

    const { Dragger } = Upload;

    const props = {
        name: 'file',
        multiple: false,
    }

    return (
        <div>
            <MySteps current = {0}/>
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                <Dragger {...props}>
                    <p className={"ant-upload-drag-icon"}>
                       <InboxOutlined />
                    </p>
                    <p className="ant-upload-text">
                        Нажмите или перетащите файл в эту область для загрузки
                    </p>
                </Dragger>
                </div>
            </Content>
        </div>
    )
}