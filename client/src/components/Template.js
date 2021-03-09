import React from "react";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";
import { Input, Card, Row, Col } from "antd";

export default function Template() {

    const { TextArea } = Input;

    return (
        <div>
            <MySteps current = {2} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    {/*<p>Тема</p>*/}
                    <Row gutter={16}>
                        <Col span={12}>
                            <Card title={"Email"}>
                                <Input placeholder={'Тема'} style={{ marginBottom: '12px' }} />
                                <TextArea placeholder={'Текст сообщения'} rows={4} />
                            </Card>
                        </Col>
                        <Col span={12}>
                            <Card title={"Telegram"}>
                                <TextArea placeholder={'Текст сообщения'} rows={6} />
                            </Card>
                        </Col>
                    </Row>

                </div>
            </Content>
        </div>
    )
}
