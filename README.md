# READ ME
## What is this project?
This project is main of being as line bot message server, has echo and saving function, and get all messages function.

### add line bot as friend
It will send welcome message to the user.
```text
哈囉，歡迎加入好友。
可以使用'查詢訊息'關鍵字查詢已送出的訊息。
```

### echo and saving function
Using `@EventMapping` maps TextMessage Event. If user types any words excepts "查詢訊息", then service will keep text and return same message to the user.

### get all messages function
Returns all user sent messages and shows.

## Before clone and usage
1. Should change `channel-token` and `channel-secret` in `application.yml`, you can copy from Line Developers console.
    ```yml
    line.bot:
        channel-token: <your channel token>
        channel-secret: <your channel secret>
        handler:
        path: /callback
        enabled: true
    ```
2. Change database connection uri in `application.yml`. If you have login account and password, both should be set, too.
    ```yml
    spring: 
      data:
        mongodb:
        uri: mongodb://localhost:27017/<your db name>
        authentication-database: admin
    ```

## Wait for optimization
### 1. Use WebExceptionHandler
### 2. Add Weather Assistance

