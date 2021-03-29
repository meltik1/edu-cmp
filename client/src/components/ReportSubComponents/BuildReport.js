export default function BuildReport(reportJson, col){
    let result = []
    for (let i = 0 , j = 0; i < reportJson.length; i++, j += 2) {
        result[j] = {
            key: `'` + i + `'`,
            number:  i ,
            name:   reportJson[i][col] === '' ?  'Не указано' :  reportJson[i][col] ,
            channel: reportJson[i]["Email"] === '' ?  'Email не указан' :  reportJson[i]["Email"] ,
            status: reportJson[i]["Email status"]
        }
        result[j + 1] = {
            key:   i ,
            number: '',
            name: '',
            channel: reportJson[i]["Telegram"] === '' ?  'Telegram не указан' :  reportJson[i]["Telegram"],
            status: reportJson[i]["Telegram status"]
        }
    }

    return result;
}