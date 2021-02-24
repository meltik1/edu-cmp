import React from "react";

import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
    Redirect
} from "react-router-dom";

import {
    Layout,
    Steps,
    Button
} from "antd";
import 'antd/dist/antd.css';

import GetSessions from "./components/Sessions";
import PickFile from "./components/PickFile";
import Mapping from "./components/Mapping";
import Template from "./components/Template";
import Validation from "./components/Validation";
import Report from "./components/Report";
import "./App.css";
import logo from "./img/logo_dark_removebg.png";
import MySteps from "./components/MySteps";

const {Header, Content, Footer} = Layout;

const { Step } = Steps;

export default function App() {

    const steps = [
        {
            title: "Выбор файла",
            description: "Загузка таблицы",
            // content: 'First-content',
        },
        {
            title: "Маппинг",
            description: "Выбор получателей",
            // content: 'Second-content',
        },
        {
            title: "Шаблон",
            description: "Набор сообщения",
            // content: 'Last-content',
        },
        {
            title: "Валидация",
            description: "Проверка",
            // content: 'Last-content',
        },
        {
            title: "Отчёт",
            description: "Результат",
            // content: 'Last-content',
        },
    ];

    const links = ["/", "/pick-file", "/mapping", "/template", "/validation", "/report"]

    const [current, setCurrent] = React.useState(0);

    const next = () => {
        setCurrent(current + 1);
    };

    const prev = () => {
        setCurrent(current - 1);
    };

    return (
        <Router>
            <div>
                <Layout className="layout">
                    <Header>
                        <div className={"logo"}><img src={logo} height={50} alt={""} /></div>
                        {/*<div className="logo" />*/}
                    </Header>

                    <Switch>
                        <Route exact path="/">
                            <GetSessions />
                        </Route>
                        <Route path="/pick-file">
                            <PickFile />
                        </Route>
                        <Route path="/mapping">
                            <Mapping />
                        </Route>
                        <Route path="/template">
                            <Template />
                        </Route>
                        <Route path="/validation">
                            <Validation />
                        </Route>
                        <Route path="/report">
                            <Report />
                        </Route>
                    </Switch>

                    {/*<MySteps current = {current}/>*/}

                    {/*<Content style={{ padding: '40px 50px 0' }}>*/}
                    {/*    <div className="site-layout-content">*/}
                    {/*        <Switch>*/}
                    {/*            <Route path="/pick-file">*/}
                    {/*                <PickFile />*/}
                    {/*            </Route>*/}
                    {/*            <Route path="/mapping">*/}
                    {/*                <Mapping />*/}
                    {/*            </Route>*/}
                    {/*            <Route path="/template">*/}
                    {/*                <Template />*/}
                    {/*            </Route>*/}
                    {/*            <Route path="/validation">*/}
                    {/*                <Validation />*/}
                    {/*            </Route>*/}
                    {/*            <Route path="/report">*/}
                    {/*                <Report />*/}
                    {/*            </Route>*/}
                    {/*            <Route path="/">*/}
                    {/*                <GetSessions />*/}
                    {/*            </Route>*/}
                    {/*            /!*<Route path="/">*!/*/}
                    {/*            /!*    <Redirect to="/pick-file" />*!/*/}
                    {/*            /!*</Route>*!/*/}
                    {/*        </Switch>*/}
                    {/*    </div>*/}
                    {/*</Content>*/}

                    <div className="steps-action" style={{ padding: '20px 50px 0' }}>

                        {current < steps.length && (
                            <Button type="primary" onClick={() => next()}>
                                <Link to={links[current + 1]}> Далее </Link>
                            </Button>
                        )}
                        {current === steps.length && (
                            <Button type="primary">
                                Готово
                            </Button>
                        )}
                        {current > 0 && (
                            <Button style={{ margin: '0 8px' }} onClick={() => prev()}>
                                <Link to={links[current - 1]}> Назад </Link>
                            </Button>
                        )}
                    </div>

                    <Footer style={{ textAlign: 'center' }}>(c) Netcracker, 2021</Footer>

                </Layout>

            </div>
        </Router>
    );
}