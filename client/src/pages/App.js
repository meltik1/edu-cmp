import React from "react";

import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect,
} from "react-router-dom";

import {Button, Layout} from "antd";
import 'antd/dist/antd.css';
import { Menu, Breadcrumb } from 'antd';

import GetSessions from "../components/Sessions";
import PickFile from "../components/PickFile";
import Mapping from "../components/Mapping";
import Template from "../components/Template";
import Validation from "../components/Validation";
import Report from "../components/Report";
import Login from "../components/Login";
import "../static/styles/App.css";
import TokenStorage from "../services/TokenStorage";
import {UserPage} from "../components/UsersPage";
import UserList from "../components/UserList";
import SendToUser from "../components/SendToUser";

const {Header, Footer} = Layout;




export default function App() {
    function logout() {
        TokenStorage.clearToken()
        TokenStorage.setIsAuthenticated(false)
        console.log(TokenStorage.getAccessToken())
        console.log(TokenStorage.getIsAuthenticated())
    }

    return (
        <Router>
            <div>
                <Layout className="layout">
                    <Header>
                        <div className="logo" />
                        <Menu theme="dark" mode="horizontal">
                            <Menu.Item key="1" onClick={logout}><a href={"/login"}>Выйти</a></Menu.Item>
                            <Menu.Item key="2" ><a href={"/users"}>К пользователям</a></Menu.Item>
                            <Menu.Item key="3" ><a href={"/sessions"}>К сессиям</a></Menu.Item>
                        </Menu>
                    </Header>
                    <Switch>
                        <Route  exact path="/">
                            <Redirect to="/login" />
                        </Route>
                        <Route path = "/login">
                            <Login/>
                        </Route>
                        <Route path="/sessions" render={ props => TokenStorage.getIsAuthenticated() ?
                            (<GetSessions/>):(<Redirect to="/login" />)
                        }>
                        </Route>
                        <Route path="/:id/pick-file" >
                            <PickFile />
                        </Route>
                        <Route path="/:id/mapping">
                            <Mapping />
                        </Route>
                        <Route path="/:id/template">
                            <Template />
                        </Route>
                        <Route path="/:id/validation">
                            <Validation />
                        </Route>
                        <Route path="/:id/report">
                            <Report />
                        </Route>
                        <Route path="/user/:id">
                            <UserPage/>
                        </Route>
                        <Route path="/users">
                            <UserList/>
                        </Route>
                        <Route path="/sendToUser/:id">
                            <SendToUser/>
                        </Route>
                    </Switch>

                    <Footer style={{ textAlign: 'center' }}></Footer>

                </Layout>

            </div>
        </Router>
    );
}
