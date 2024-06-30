## App launch
Для того запуска приложение потребуется Docker. Войдите в корневую папку приложения и введите команду в консоли "docker build ."<br>
После сборки приложения введите "docker images" появится образы контейнеров. Скопируйте последний созданный IMAGE ID и введите команду "docker run <скопированный image id> -p <порт>:8080"<br>
После указанных действий приложение будет доступно по адресу http://localhost:<порт>

## API features
#### GET /api/v1/exchangeRates?date=2024-06-30
Возвращает список обменных курсов белорусского рубля ко всем валютам на дату
```json
[
    {
        "currency": {
            "id": 464,
            "code": "SEK",
            "scale": 10,
            "name": "Шведских крон"
        },
        "date": "2024-06-30",
        "rate": 2.986
    },
    {
        "currency": {
            "id": 462,
            "code": "CNY",
            "scale": 10,
            "name": "Китайских юаней"
        },
        "date": "2024-06-30",
        "rate": 4.3172
    },
    {
        "currency": {
            "id": 460,
            "code": "TRY",
            "scale": 10,
            "name": "Турецких лир"
        },
        "date": "2024-06-30",
        "rate": 0.9618
    },
    ....
]
```

#### GET /api/v1/exchangeRates/USD?date=2024-06-30
Возвращает обменный курс белорусского рубля к валюте на дату
```json
{
  "currency": {
    "id": 431,
    "code": "USD",
    "scale": 1,
    "name": "Доллар США"
  },
  "date": "2024-06-30",
  "rate": 3.1624
}
```

## Testing
в файле HttpTest.http приведены тесты эндпоинтов 
пример с неверно указанной датой:
#### GET /api/v1/exchangeRates?date=2024-06-33
```json
{
    "errorCode": 400,
    "message": "Invalid value [2024-06-33] for parameter date"
}
```

пример с неверно указанным кодом валюты
#### GET /api/v1/exchangeRates/FFF?date=2024-06-30
```json
{
  "errorCode": 400,
  "message": "Issues calling api with date: 2024-06-30 and currency: FFF"
}
```