import React from "react";
import MySteps from "./MySteps";
import {Content} from "antd/es/layout/layout";
import {Card, Row, Col, Divider} from "antd";

const theme = 'Обучение в Учебном Центре Netсracker 2020'
const text = 'Поздравляем, вы приняты в Учебный Центр компании Netcracker\n' +
    '\n'+
    'Всем привет! Мы рады сообщить, что вы прошли отбор на обучение от компании Netсracker.\n' +
    'Обучение состоит из двух частей: онлайн-курса и очного проекта с куратором.\n' +
    'С сегодняшнего дня у вас открыт доступ к онлайн части.\n' +
    'Курс проходит в двух системах: Java Basics и Java & SQL Skill Bench.\n' +
    'Ваш логин и пароль для входа в системы – это левая часть адреса email до знака @  (почта которую вы указывали в анкете на поступление).\n' +
    'Пароль следует сменить сразу после первого логина в обеих системах.\n' +
    'Более подробно об интерфейсе Skill Bench можно узнать из пошаговой инструкции к Java Skill Bench.\n' +
    'Java Basics содержит учебные материалы, рекомендации по освоению курса и тесты.\n' +
    'Решить Java-задачи и применить теорию на практике вы сможете в Skill Bench,  где вам доступны все задания, но решать их лучше последовательно.\n' +
    'Для обсуждения текущего прогресса и задач создано сообщество в Вконтакте.\n' +
    'Ответы на популярные вопросы вы найдете в FAQ.\n' +
    'По другим вопросам можно писать нам на почту EduCenter@NetCracker.com.\n' +
    'С уважением,\n' +
    'Команда Учебного Центра'

export default function Validation() {
    return (
        <div>
            <MySteps current = {3}/>
            <Content style={{ padding: '40px 50px 0' }}>
                <div className="site-layout-content">
                    <Row gutter={16}>
                        <Col span={12}>
                            <Card title={"Email"} >
                                <p>{theme}</p>
                                <Divider />
                                <div>{text}</div>
                            </Card>
                        </Col>
                        <Col span={12}>
                            <Card title={"Telegram"} >
                                <p>{text}</p>
                            </Card>
                        </Col>
                    </Row>

                </div>
            </Content>
        </div>
    )
}