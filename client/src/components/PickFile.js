import React from "react";
import MySteps from "./MySteps";
import { message, Button, Upload } from 'antd';
import { Content } from "antd/es/layout/layout";
import { ArrowLeftOutlined, ArrowRightOutlined, InboxOutlined } from '@ant-design/icons';
import "./PickFile.css";
import {Link} from "react-router-dom";
import { useParams } from "react-router";
import "../ServerApi.js"
import Settings from "../backend.settings.json"

export default function PickFile() {

    const sessionId = useParams().id;

    const { Dragger } = Upload;

    const props = {
        name: 'file',
        multiple: false,
        action :  Settings.backend.url + '/sessions/'+ sessionId + '/pick-file',
        onChange(info) {
            const { status } = info.file;
            if (status === 'done') {
                message.success(`${info.file.name} file uploaded successfully.`);
            } else if (status === 'error') {
                message.error(`${info.file.name} file upload failed.`);
            }
        },
    }

    return (
        <div>
            <MySteps current = {0} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                <Dragger className={"dragger"} {...props}>
                    <p className={"ant-upload-drag-icon"}>
                       <InboxOutlined />
                    </p>
                    <p className="ant-upload-text">
                        Нажмите или перетащите файл в эту область для загрузки
                    </p>
                </Dragger>
                </div>
            </Content>
            <div className={"buttons"}>
                <Button type={"secondary"}>
                    <Link to={"/"}> <ArrowLeftOutlined /> Назад </Link>
                </Button>
                <Button type={"primary"}>
                    <Link to={"/mapping"}> Далее <ArrowRightOutlined /> </Link>
                </Button>
            </div>
        </div>
    )
}
