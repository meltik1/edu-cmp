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
    Menu
} from "antd";
import PickFile from "./PickFile";
import Mapping from "./Mapping";
import Template from "./Template";
import Validation from "./Validation";
import Report from "./Report";
import "./App.css";
import 'antd/dist/antd.css';

const {Header, Content, Footer} = Layout;

export default function App() {
    return (
        <Router>
            <div>
                <Layout className="layout">
                    <Header>
                        <div className="logo" />
                        <Menu theme="dark" mode="horizontal" defaultSelectedKeys={['1']}>
                            <Menu.Item key="1"><Link to="/pick-file">Pick file</Link></Menu.Item>
                            <Menu.Item key="2"><Link to="/mapping">Mapping</Link></Menu.Item>
                            <Menu.Item key="3"><Link to="/template">Template</Link></Menu.Item>
                            <Menu.Item key="4"><Link to="/validation">Validation</Link></Menu.Item>
                            <Menu.Item key="5"><Link to="/report">Report</Link></Menu.Item>
                        </Menu>
                    </Header>
                    <Content style={{ padding: '50px 50px 0' }}>
                        <div className="site-layout-content">
                            <Switch>
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
                                <Route path="/">
                                    <Redirect to="/pick-file" />
                                </Route>
                            </Switch>
                        </div>

                    </Content>
                    <Footer style={{ textAlign: 'center' }}>(c) Netcracker, 2021</Footer>
                </Layout>
                {/*<nav>*/}
                {/*  <ul>*/}
                {/*    <li>*/}
                {/*      <Link to="/pick-file">Pick file</Link>*/}
                {/*    </li>*/}
                {/*    <li>*/}
                {/*      <Link to="/mapping">Mapping</Link>*/}
                {/*    </li>*/}
                {/*    <li>*/}
                {/*      <Link to="/template">Template</Link>*/}
                {/*    </li>*/}
                {/*    <li>*/}
                {/*      <Link to="/validation">Validation</Link>*/}
                {/*    </li>*/}
                {/*    <li>*/}
                {/*      <Link to="/report">Report</Link>*/}
                {/*    </li>*/}
                {/*  </ul>*/}
                {/*</nav>*/}

            </div>
        </Router>
    );
}