import React from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";

export default function Report() {
    return (
        <div>
            <MySteps current = {5} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content"> Report </div>
            </Content>
        </div>
    )
}
