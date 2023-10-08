# ktor-chat-server
Example app uses ktor server, mongo db as the backend

For android app uses this server visit this [repo](https://github.com/Hussienfahmy/Ktor_Chat_App)

# Features
- Create Rooms
- Send Messages in real time to room members with `Websockets`

# Environments
the project uses environment variables from the system to access mongo db
Please make sure they are present before running the app.

environments variable are:
- DATABASE_USERNAME
- DATABASE_PASSWORD
- DATABASE_HOST
- DATABASE_PORT

# Endpoints
| Endpoint  | Method | Description |
| ------------- | ------------- | ------------- |
| /create-room/{roomName}  | POST  | Create a new room |
| /rooms  | GET  | Get all available rooms |
| /messages/{roomId} | GET | Get all messages of the room with that ID |
| /chat-socket | WS | Esablish a websocket connection to send & receive messages in real time |


# The project is Dockerized!!

Image available on [dockerhub](https://hub.docker.com/r/hussienfahmy/ktor-chat-server)

```yml
version: "3"
services:
  chat-server:
    container_name: chat-server
    restart: unless-stopped
    image: hussienfahmy/ktor-chat-server
    depends_on:
      - chat-db
    ports:
      - "86:8080"
    environment:
      - DATABASE_USERNAME=admin
      - DATABASE_PASSWORD=admin
      - DATABASE_HOST=chat-db
      - DATABASE_PORT=27017

  chat-db:
    image: mongo
    restart: unless-stopped
    volumes:
      - /my/own/datadir:/data/db
    environment:
      - MONGO_INITDB_ROOT_USERNAME=admin
      - MONGO_INITDB_ROOT_PASSWORD=admin

  mongo-express:
    image: mongo-express
    restart: unless-stopped
    depends_on:
      - chat-db
    ports:
      - "85:8081"
    environment:
      - ME_CONFIG_MONGODB_ADMINUSERNAME=admin
      - ME_CONFIG_MONGODB_ADMINPASSWORD=admin
      - ME_CONFIG_MONGODB_URL=mongodb://admin:admin@chat-db:27017/
```
