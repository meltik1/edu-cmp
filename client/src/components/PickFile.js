import React from "react";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";

export default function PickFile() {
    const [current, setCurrent] = React.useState(0);
    return (
        <div>
            <MySteps current = {1}/>
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content"> Pick file </div>
            </Content>
        </div>
    )
}