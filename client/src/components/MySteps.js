import {Steps} from "antd";

export default function MySteps({current}) {

    const { Step } = Steps;

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

    return (
        <Steps current={current} style={{ padding: '50px 80px 0' }}>
            {steps.map((item) => (
                <Step
                    key={item.title}
                    title={item.title}
                    description={item.description} />
            ))}
        </Steps>
    )
}
