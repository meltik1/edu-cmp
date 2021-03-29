export default function BuildReport(reportJson, col){
   let result = []
    debugger
    for (let i=0 , j = 0; i < reportJson.length;i++, j+=2) {
        result[j] = {
            key: `'` + i + `'`,
            number:  i ,
            name:   reportJson[i][col] === '' ?  'Не указано' :  reportJson[i][col] ,
            channel: reportJson[i]["Email"] === '' ?  'Email не указан' :  reportJson[i]["Email"] ,
            status: reportJson[i]["Email status"]
        }
        result[j+1] = {
            key:   i ,
            number: '',
            name: '',
            channel: reportJson[i]["Telegram"] === '' ?  'Telegram не указан' :  reportJson[i]["Telegram"],
            status: reportJson[i]["Telegram status"]
        }
    }

    return result;
}

/* const data = [
        {
            key: '1',
            number: '1',
            name: 'Aaron Aldenburg',
            channel: 'E-mail: aa@mail.ru',
            status: 'Ok'
        },
        {
            key: '2',
            // number: '1',
            // name: 'Aaron Aldenburg',
            number: '',
            name: '',
            channel: 'tg: @aa',
            status: 'Ошибка'
        },
        {
            key: '3',
            number: '2',
            name: 'Berthold Blake',
            channel: 'email: bb@mail.ru',
            status: 'Успешно'
        },
        {
            key: '4',
            number: '3',
            name: 'Carl Clopp',
            channel: 'email: cc@mail.ru',
            status: 'Успешно'
        },
        {
            key: '5',
            // number: '3',
            // name: 'Carl Clopp',
            number: '',
            name: '',
            channel: 'telegram: @cc',
            status: 'Ошибка'
        },
        {
            key: '6',
            number: '4',
            name: 'Dick Donovan',
            channel: 'email: dd@mail.ru',
            status: 'Ошибка'
        },
        {
            key: '7',
            number: '5',
            name: 'Emanuel East',
            channel: 'email: ee@mail.ru',
            status: 'Успешно'
        },
    ]*/