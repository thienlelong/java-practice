# Todo-app practice

## Requirement

#### Techs:

- Spring Boot
- RESTful API
- MySQL
- Docker
- Rancher
- JWT
- Jenkins CI

#### Time: 5 days

#### Requirement:

1. Create APIs for todo-list app
2. Features
  - Register, Login & Logout
  - Create a todo
  - Delete a todo or list todos
  - List all todos
  - Unit test for all APIs

## Building for develop

- cd todo-app
- run: docker-compose -f devops/docker-compose/dev.yml up

## Building for production

To achieve this, first build a docker image of your app by running:
  - cd todo-app
  - ./mvnw package -DskipTests docker:build

Then run:
  - docker-compose -f devops/docker-compose/prod.yml up -d

## Testing
  - docker-compose -f devops/docker-compose/test.yml up -d
