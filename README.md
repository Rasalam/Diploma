# Дипломный проект по специальности "Тестировщик ПО" 

Проект представляет собой автоматизацию тестирования приложения "Путешествие дня", реализованного в форме веб-сервиса.

Приложение предлагает купить тур с помощью двух способов:

- Обычная оплата по дебетовой карте
- Уникальная технология: выдача кредита по данным банковской карты

Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:

- сервису платежей
- кредитному сервису

Приложение сохраняет в собственной СУБД информацию о том, каким способом был совершён платёж и успешно ли он был совершён (данные карт не сохраняются).

---

## Предварительные шаги.

ПО необходимое для запуска автотестов:

- Git;
- Java JDK 11 или более поздняя;
- Google Chrome (или другой популярный браузер);
- IntelliJ IDEA;
- Docker Desktop;

Предварительно необходимо клонировать репозиторий проекта на локальный компьютер, ссылка на репозиторий **https://github.com/Rasalam/Diploma.git**. 

---

## Установка и запуск SUT.

#### 1. Запустить Docker Desktop. Для запуска SUT потребуются образы: mysql (latest), postgres (latest), node-app 1.0.  

#### 2. Открыть проект в IntelliJ IDEA, дождаться завершения конфигурирования/подготовки проекта к сборке сборщиком Gradle.   

#### 3. В терминале IntelliJ IDEA, находясь в корневом каталоге запустить контейнеры в Docker Desktop, используя команду   

```
dcocker compose up -d
```
#### 4. Дождаться окончания процесса запуска контейнеров. Все три контейнера должны иметь статус "UP". Просмотр статуса контейнера возможен командой 

```
docker-compose ps
```

#### 5. В новой вкладке терминала запустить SUT командой:
- Если в качестве СУБД выбрана MySQL:
```
java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar ./artifacts/aqa-shop.jar
```
* Если в качестве СУБД выбрана Postgres:
```
java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar ./artifacts/aqa-shop.jar
```
При успешнои запуске SUT в окне терминала появится сообщение вида:

```
date time INFO XXXXX --- [           main] ru.netology.shop.ShopApplication         : Started ShopApplication in 13.725 seconds (JVM running for 15.136)
```
Адрес сервиса в браузере: http://localhost:8080/

При необходиомсти перезапуска SUT для смены СУБД, необходимо закрыть в терминале текущий экземпляр SUT используя комбинацию клавиш Ctrl+C.

#### 5. В новой вкладке терминала запустить выполнение автотестов:
- Если в качестве СУБД выбрана MySQL:
```
./gradlew clean test "-Datasource.url=jdbc:mysql://localhost:3306/app"
```
* Если в качестве СУБД выбрана Postgres:
```
./gradlew clean test "-Datasource.url=jdbc:postgresql://localhost:5432/app"
```
#### 6. Генерация отчетов осуществляется с помощью Allure. Запуск генерации отчетов осуществляется командой в терминале:

```
./gradlew allureServe
```

По завершении генерации отчет автоматически открывается в браузере.

После завершения работы с отчетами сервис allureServe должен быть остановлен сомбинацией клавиш Ctrl+C.

---

## Документация проекта

- [План автоматизации тестирования](https://github.com/Rasalam/Diploma/blob/cdada8454a795ac8cceab86b2522f6a09663cdf5/documents/Plan.md)
- [Отчёт по итогам автоматизированного тестирования](https://github.com/Rasalam/Diploma/blob/cdada8454a795ac8cceab86b2522f6a09663cdf5/documents/Report.md)
- [Отчёт по итогам автоматизации](https://github.com/Rasalam/Diploma/blob/cdada8454a795ac8cceab86b2522f6a09663cdf5/documents/Summary.md)
