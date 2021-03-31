import React, {useState, useEffect} from "react";
import MySteps from "./MySteps";
import { Content } from "antd/es/layout/layout";
import {Table, Button, List, Input} from "antd";
import "./Mapping.css";
import RangeSelector from "./RangeSelector";
import {useHistory, useParams} from "react-router";
import {Link} from "react-router-dom";
import {ArrowLeftOutlined, ArrowRightOutlined} from "@ant-design/icons";
import {backend} from "../ServerApi";

export default function Mapping() {

    const [, rerender] = useState();

    const sessionId = useParams().id;
    let maxNumber = 0;

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

        // parse data
        const mapped = data.map(x => {
            return x.rawFileData;
        }).map((x, index) => {
            maxNumber = Object.keys(x).length;
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

    const [attributes, setAttributes] = useState(['email', 'tg']);
    const [tempAttribute, setTempAttribute] = useState("");
    const [columns, setColumns] = useState([]);
    for (let i = 0; i < maxNumber; i++) {
        columns.push("");
    }

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

    const [ranges, setRanges] = useState([]);

    function alert(rangesNew) {
        rangesNew.map((range) => {
            let start = range.begin;
            let endNew = range.end;
            console.log("start: " + start + "; end: " + endNew);
            return ("start: " + start + "; end: " + endNew);
        });
        setRanges(rangesNew);
    }

    const history = useHistory();

    const next = async () => {

        let response = {
            mapping: {},
            range: [],
        }
        for (let i = 0; i < columns.length; i++) {
            response.mapping[i] = columns[i];
        }
        ranges.map((range) => {
            let start = range.begin;
            let end = range.end;
            response.range.push({"start": parseInt(start, 10), "end": parseInt(end, 10)});
            return ({"start": parseInt(start, 10), "end": parseInt(end, 10)});
        });

        console.log(JSON.stringify(response));

        const responseJSON = JSON.stringify(response);

        backend.post(`sessions/${sessionId}/save-columns-mapping`, responseJSON)
            .catch(console.log);

        history.push({
            pathname: "template",
        });
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
                    next();
                }}>
                    Далее
                    <ArrowRightOutlined />
                </Button>
            </div>
        </div>
    )
}
