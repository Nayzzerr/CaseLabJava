# CaseLabJava

Этот проект представляет собой микросервис, управляющий файлами и их атрибутами, реализованный с использованием Java и Spring Boot. Микросервис предоставляет HTTP API для создания файла, получения файла и получения списка файлов. Данные хранятся в базе данных PostgreSQL. Микросервис упакован в Docker-контейнеры для удобства развертывания.

## Технологический стек

- Java 17
- Spring Boot
- PostgreSQL
- Docker

## Запуск приложения

### Предварительные требования

- Установленный Docker
- Установленный Docker Compose

### Запуск

Выполните следующие команды в корне проекта:

- docker-compose build
- docker-compose up

Это создаст и запустит контейнеры для приложения и базы данных.

### Остановка приложения

Для остановки приложения выполните:

- docker-compose down

## API Методы

### Создание файла

POST http://localhost:8080/api/files/create

Тело запроса:
``` 
{
    "title": "First file",
    "creationDate": "2024-09-01T12:00:00",
    "description": "Just Example."
}
```
Ответ:
```
1
```

### Получение файла

GET http://localhost:8080/api/files/{id}, где *id* - идентификатор файла

Пример запроса:
GET http://localhost:8080/api/files/1

Ответ:
```
{
    "id": 1,
    "title": "First file",
    "creationDate": "2024-09-01T12:00:00",
    "description": "Just Example."
}
```
### Получение списка файлов
GET http://localhost:8080/api/files

Параметры запроса:
```
page - номер страницы (по умолчанию 0)
size - количество элементов на странице (по умолчанию 10)
sortBy - поле для сортировки (по умолчанию creationDate)
sortDirection - направление сортировки (asc или desc, по умолчанию asc)
```
Пример запроса:

GET http://localhost:8080/api/files?page=0&size=10&sortBy=creationDate&sortDirection=asc

Ответ:
```
{
    "content": [
    {
        "id": 1,
        "title": "First file",
        "creationDate": "2024-09-01T12:00:00",
        "description": "Just Example."
    },
    {
        "id": 2,
        "title": "Sample 2",
        "creationDate": "2024-09-01T12:00:00",
        "description": "kitten."
    }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 10,
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "first": true,
    "numberOfElements": 2,
    "empty": false
}
```
## Примечания

- Для тестирования API можно использовать Postman или аналогичные инструменты.


- Развернутый ответ с пагинацией включает полную информацию о текущей странице данных. Это упрощает навигацию и понимание структуры данных, позволяя клиенту API эффективно управлять страницами, отображать данные правильно и улучшает пользовательский интерфейс. Кроме того, такая информация полезна для диагностики и отладки, обеспечивая четкую картину текущего состояния данных.
