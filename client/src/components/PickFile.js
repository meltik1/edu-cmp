import React from "react";
import MySteps from "./MySteps";
import {Button, Upload} from 'antd';
import { Content } from "antd/es/layout/layout";
import { ArrowLeftOutlined, ArrowRightOutlined, InboxOutlined } from '@ant-design/icons';
import "./PickFile.css";
import {Link} from "react-router-dom";

export default function PickFile() {

    const { Dragger } = Upload;

    const props = {
        name: 'file',
        multiple: false,
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
