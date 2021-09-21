import React from "react";

import {
    BrowserRouter as Router,
    Switch,
    Route,
    Redirect,
} from "react-router-dom";

import {Button, Layout} from "antd";
import 'antd/dist/antd.css';

import GetSessions from "./components/Sessions";
import PickFile from "./components/PickFile";
import Mapping from "./components/Mapping";
import Template from "./components/Template";
import Validation from "./components/Validation";
import Report from "./components/Report";
import Login from "./components/Login";
import "./App.css";
import logo from "./img/logo_dark_removebg_try.png";
import TokenStorage from "./TokenStorage";
import {useHistory} from "react-router";
import {UserPage} from "./components/UsersPage";
import UserList from "./components/UserList";
import SendToUser from "./components/SendToUser";

const {Header, Footer} = Layout;




export default function App() {

    return (
        <Router>
            <div>
                <Layout className="layout">
                    <Header>

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
