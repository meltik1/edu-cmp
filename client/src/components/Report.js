import React, {useState} from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import  {Select, Table, Tag} from "antd";
import SelectAtrributes from "./ReportSubComponents/SelectAtrributes";
import AttributesList from "./AttributesList";
import ReportInfo from "./ReportSubComponents/ReportInfo";
import BuildReport from "./ReportSubComponents/BuildReport";
const { Option } = Select;



export default function Report() {

    const [selectedColumn, setColumn] = useState("Email")

    const reportInfo = ReportInfo()

    const columns = [
        {
            title: '№',
            dataIndex: 'number',
            key: 'number',
            width: 20,
        },
        {
            title: selectedColumn,
            dataIndex: 'name',
            key: 'name',
        },
        {
            title: 'Канал нотификации',
            dataIndex: 'channel',
            key: 'channel',
        },
        {
            title: 'Статус',
            dataIndex: 'status',
            key: 'status',
            render: status => {
                let color = 'green';
                if (status !== 'ok') {
                    color = 'volcano';
                }
                return (
                    <Tag color={color} key={status}>{status}</Tag>
                )
            },
        }
    ];

    const data = BuildReport(reportInfo, selectedColumn)

    function handler(val) {
        setColumn(val)
    }


    return (
        <div>
            <MySteps current = {4} />
            <Content style={{ padding: '40px 50px 0' }}>
                <SelectAtrributes change={handler} attributesList={AttributesList()} />
                <div className="site-layout-content">
                    <Table
                        columns={columns}
                        dataSource={data}
                        size="small"
                    />
                </div>
            </Content>
        </div>
    )
}
