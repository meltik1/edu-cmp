import { Select } from "antd";
import React from "react";

const { Option } = Select

export default function SelectAttributes({ attributesList, change }) {

    return (<Select defaultValue="Email" onChange={change} style={{ width: 120 }} >
        {attributesList.map(item => (
            <Option value={ item }>{ item }</Option>
        ))}
        }
    </Select>)
}