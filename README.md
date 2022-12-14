# Дипломный проект по специальности "Тестировщик ПО" 

Проект представляет собой автоматизацию тестирования приложения "Путешествие дня", работающего через веб-интеррфейс.

---

## Документация проекта

- [Дипломное задание](https://github.com/netology-code/qa-diploma.git)
- [План автоматизации тестирования](https://github.com/Rasalam/Diploma/blob/12d947825f334a4e08695b8ff2d933d732cd82c2/documents/Plan.md)
- [Отчёт по итогам автоматизированного тестирования](https://github.com/Rasalam/Diploma/blob/12d947825f334a4e08695b8ff2d933d732cd82c2/documents/Report.md)
- [Отчёт по итогам автоматизации](https://github.com/Rasalam/Diploma/blob/6eeffc026c6af0e5880ff3fd000823ebd53fa0dc/documents/Summary.md)

## Предварительные шаги.

Необходимо клонировать репозиторий проекта на локальный компьютер, ссылка на репозиторий: https://github.com/Rasalam/Diploma.git.

ПО необходимое для запуска автотестов:

- Git;
- Java JDK 11;
- Google Chrome (или другой популярный браузер);
- IntelliJ IDEA;
- Docker Desktop (с установленными образами mysql (latest), postgres (latest), node-app 1.0);

---

## Запуск SUT.

#### 1. Запустить Docker Desktop.

#### 2. Открыть проект в IntelliJ IDEA, дождаться завершения конфигурирования/подготовки проекта сборщиком Gradle.

#### 3. В терминале IntelliJ IDEA, запустить контейнеры в Docker Desktop командой:

```
docker compose up -d
```
#### 4. Дождаться окончания процесса запуска контейнеров. Все три контейнера должны иметь статус "UP". Статус контейнеров отображает команда:

```
docker-compose ps
```

#### 5. В новой вкладке терминала запустить SUT.
Если в качестве СУБД выбрана MySQL, командой:
```
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar
```
Если в качестве СУБД выбрана Postgres, командой:
```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar
```
При успешнои запуске SUT в окне терминала появится сообщение вида:

```
date time INFO XXXXX --- [           main] ru.netology.shop.ShopApplication         : Started ShopApplication in 13.725 seconds (JVM running for 15.136)
```
Веб-сервис становится доступен в браузере по адресу: http://localhost:8080/

При необходиомсти перезапуска SUT для смены СУБД, необходимо закрыть в терминале текущий экземпляр SUT используя комбинацию клавиш Ctrl+C.

## Запуск автотестов.

Если в качестве СУБД выбрана MySQL, автотесты запускаются в новой вкладке терминала командой:
```
./gradlew clean test "-Ddatasource.url=jdbc:mysql://localhost:3306/app"
```
Если в качестве СУБД выбрана Postgres, автотесты запускаются в новой вкладке терминала командой:
```
./gradlew clean test "-Ddatasource.url=jdbc:postgresql://localhost:5432/app"
```
## Генерация отчетов.

Генерация отчетов осуществляется с помощью Allure. Запуск генерации отчетов осуществляется командой в терминале:

```
./gradlew allureServe
```

По завершении генерации отчет автоматически открывается в браузере.

После завершения работы с отчетами сервис allureServe должен быть остановлен сомбинацией клавиш Ctrl+C.

---
