import React from "react";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";

export default function Validation() {
    return (
        <div>
            <MySteps current = {4}/>
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content"> Validation </div>
            </Content>
        </div>
    );
}