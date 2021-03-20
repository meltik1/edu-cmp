import React from "react";

import {
    BrowserRouter as Router,
    Switch,
    Route,
    Link,
} from "react-router-dom";

import {
    Layout,
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
import logo from "./img/logo_dark_removebg_try.png";

const {Header, Footer} = Layout;

export default function App() {

    const steps = [
        {
            title: "Выбор файла",
            description: "Загузка таблицы",
        },
        {
            title: "Маппинг",
            description: "Выбор получателей",
        },
        {
            title: "Шаблон",
            description: "Набор сообщения",
        },
        {
            title: "Валидация",
            description: "Проверка",
        },
        {
            title: "Отчёт",
            description: "Результат",
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
