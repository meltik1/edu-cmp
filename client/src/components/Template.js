import React from "react";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";

export default function Template() {
    return (
        <div>
            <MySteps current = {2}/>
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content"> Template </div>
            </Content>
        </div>
    )
}