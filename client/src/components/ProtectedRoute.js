import {Component} from "react";
import {Redirect, Route} from "react-router";
import TokenStorageService from "../services/TokenStorage";

class ProtectedRoute extends Component {
    render() {
        const { component: Component, ...props } = this.props
        console.log("token storage " + TokenStorageService.getIsAuthenticated())
        console.log("type "  + typeof TokenStorageService.getIsAuthenticated())
        console.log(TokenStorageService.getIsAuthenticated() === "true")
        return (
            <Route
                {...props}
                render={props => (
                     TokenStorageService.getIsAuthenticated() === "true" ?
                        <Component {...props} /> :
                        <Redirect to='/login' />
                )}
            />
        )
    }
}
export default ProtectedRoute;