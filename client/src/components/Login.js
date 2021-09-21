import { Form, Input, Button, Checkbox } from 'antd';
import {backend} from "../ServerApi";
import {useHistory, useParams} from "react-router";
import axios from "axios";
import Settings from "../backend.settings.json"
import TokenStorageService from "../TokenStorage";

export  default function  Login()  {
    const onFinish = async (values) => {
        console.log(values.username, values.password)

        const s = await axios.post(Settings.backend.url +"/auth/login", {"email": values.username, "password": values.password})
        console.log(s)
        if (s.status != 200) {
            alert("Invalid login or password")
        }
        else {
            Settings.backend.login = values.username
            Settings.backend.password = values.password
            console.log(s.data.token)
            TokenStorageService.setToken(s.data.token)
            TokenStorageService.setIsAuthenticated(true)
            history.push("/sessions")
        }
        console.log('Success:', values);
    };



    const onFinishFailed = (errorInfo) => {
        alert("Auth failed")
        console.log('Failed:', errorInfo);
    };

    const history = useHistory()


    return (
        <Form
            name="basic"
            labelCol={{
                span: 8,
            }}
            wrapperCol={{
                span: 16,
            }}
            initialValues={{
                remember: true,
            }}
            onFinish={onFinish}
            onFinishFailed={onFinishFailed}
            autoComplete="off"
            style={{ padding: '180px 50px 0' }}
        >
            <Form.Item
                label="Username"
                name="username"
                rules={[
                    {
                        required: true,
                        message: 'Please input your username!',
                    },
                ]}

            >
                <Input />
            </Form.Item>

            <Form.Item
                label="Password"
                name="password"
                rules={[
                    {
                        required: true,
                        message: 'Please input your password!',
                    },
                ]}
            >
                <Input.Password />
            </Form.Item>

            <Form.Item
                name="remember"
                valuePropName="checked"
                wrapperCol={{
                    offset: 8,
                    span: 16,
                }}
            >
                <Checkbox>Remember me</Checkbox>
            </Form.Item>

            <Form.Item
                wrapperCol={{
                    offset: 8,
                    span: 16,
                }}
            >
                <Button  type="primary" htmlType="submit">
                    Submit
                </Button>
            </Form.Item>
        </Form>
    );
};