import React, {useState, useEffect} from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import {Table, Button, List, Input} from "antd";
import "./Mapping.css";
import RangeSelector from "./RangeSelector";
import {useParams} from "react-router";
import {Link} from "react-router-dom";
import {ArrowLeftOutlined, ArrowRightOutlined} from "@ant-design/icons";
import {backend} from "../ServerApi";

export default function Mapping() {

    const [, rerender] = useState();

    const [attributes, setAttributes] = useState(['email', 'tg']);
    const [tempAttribute, setTempAttribute] = useState("");
    const [columns, setColumns] = useState(new Array(5));// TODO: as max column number from sever


    const getMapper = (i) => (
        <div>
            <Input
                placeholder="Введите новый макрос"
                value={tempAttribute}
                onChange={(e) => {setTempAttribute(e.target.value)}}
                onPressEnter={(e) => {
                    attributes.push(tempAttribute)
                    setTempAttribute("")
                    setAttributes(attributes)
                    rerender({});
                }}/>
            <List
                size="small"
                bordered
                dataSource={attributes}
                renderItem={item => <List.Item onClick={() => {
                    columns[i] = item;
                    setColumns(columns)
                    rerender({})
                }}>{item}</List.Item>}
            />
        </div>
    )

    const generateColumns = (names) => {

        const result = [];

        // add rownum column
        result.push({
            title: '№',
            dataIndex: 'number',
            key: 'number',
            width: 20,
        })

        for (let i = 0; i < names.length; i++) {
            result.push(
                {
                    title: names[i],
                    dataIndex: i,
                    key: i,
                    filterDropdown: getMapper(i)
                }
            )
        }

        return result;
    }

    const sessionId = useParams().id;

    const [data, setData] = useState();
    const [tableData, setTableData] = useState(
        {
            generated: false,
            dataset: []
        }
    );


    useEffect(() => {
        const fetchData = async () => {
            const result = await backend.get(`/sessions/${sessionId}/map-columns`)
            setData(result.data);
        };

        fetchData();
    }, []);

    if (data !== undefined && !tableData.generated) {
        console.log(data);

        let maxIter = 0;

        // parse data
        const mapped = data.map(x => {
            return x.rawFileData;
        }).map((x, index) => {

            let newX = x;
            newX.key = index + 1;
            newX.number = index + 1;

            return newX;
        });

        setTableData(
            {
                generated: true,
                dataset: mapped
            }
        )

    }

    const [ranges, setRanges] = useState([]);


    function alert(rangesNew) {
        console.log(rangesNew);
        setRanges(rangesNew);
    }

    return (
        <div>
            <MySteps current = {1} />
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <Table
                        columns={generateColumns(columns)}
                        dataSource={tableData.dataset}
                        size={"small"}
                    />
                    <RangeSelector alert={alert} />
                </div>
            </Content>
            <div className={"buttons"}>
                <Button type={"secondary"}>
                    <Link to={`/${sessionId}/pick-file`}> <ArrowLeftOutlined /> Назад </Link>
                </Button>
                <Button type={"primary"} onClick={() => {
                    console.log(columns)
                    console.log(ranges)
                }}>
                    Далее
                    <ArrowRightOutlined />
                </Button>
            </div>
        </div>
    )
}
