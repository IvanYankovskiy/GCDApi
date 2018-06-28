# GCDApi
REST API for GCD service
Приложение REST API позволяет принимать запросы на вычисление НОД для двух целых положительных чисел.
Приложение возможно использовать со следующими СУБД:
- MySQL 5.7.16 Community Server
- PostgreSQL 9.6.9
Версия RabbitMQ 3.7.6 
Версия Erlang 20.2

<H1>1. Начало работы</H1>

Скачайте исходные файлы, откройте pom.xml проекта в IDE, выполните сборку проекта.
Перейдите в папку проекта <i>GcdApi/target</i> , где будут расположены следующие архивы:


api-distribution.tar - если вы желаете запустить приложение в Linux-системе; 
api-distribution.zip - если вы желаете запустить приложение в Windows-системе;

Далее, переместите архив в удобное для вас расположение и распакуйте его.
- В linux-системе tar –xvf api-distribution.tar

В распакованной папке перейдите в
- <i>api-distribution/config</i>

и откройте файл настроек application.properties.


<H1>2. Настройка БД</H1>


<H2>2.1. Настройка приложения для работы с PostgreSQL</H2>
Для работы приложения с СУБД PostgreSQL необходимо создать пользователя и базу данных. Это можно сделать из графической оболочки или из командной строки, выполнив следующие выражения:
Создание пользователя БД:


```SQL
        
CREATE USER gcdapi WITH
	LOGIN
	NOSUPERUSER
	CREATEDB
	NOCREATEROLE
	INHERIT
	NOREPLICATION
	CONNECTION LIMIT -1
	PASSWORD '12345*gcdapi';
```
Создание БД:

```SQL
      
CREATE DATABASE gcd
    WITH 
    OWNER = gcdapi
    TEMPLATE = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

После создания базы данных и пользователя, откройте файл application.properties, расположенный в папке 

В распакованных папках архива из, рассмотренном в пункте <b>1</b> перейдите в <i>api-distribution/config</i> где будет расположен файл настроек <i>application.properties</i> и проверьте настройки. Если вы указали свои настройки, отличающиеся от тех, что по умолчанию, такие как имя БД, имя пользователя или пароль, то измените на соответствующие следующие свойства:
  
```Java

spring.datasource.url = jdbc:postgresql://localhost:5432/gcd
spring.datasource.username = gcdapi
spring.datasource.password = 12345*gcdapi
```

Проверьте наличие свойства:
  
```Java

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL94Dialect
```
<H2>2.2. Настройка приложения для работы с MySQL</H2>


Для работы с MySQL вам необходимо лишь проверить следующие настройки, если экземпляр вашей базы данных запущен на локальной машине:
  
```Java

spring.datasource.url = jdbc:mysql://localhost:3306/gcd?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username = gcdapi
spring.datasource.password = 12345*gcdapi
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
```
Вы можете указать свои настройки, затем сохраните файл и перейдите к пунтку <b>3</b>.

Если выжелаете создать базу данных самостоятельно, то ниже приведен пример, аналогичный пункту <b>2.1</b>:
Для работы с MySQL вам необходимо создать пользователя
  
```SQL

CREATE DATABASE `gcd` /*!40100 DEFAULT CHARACTER SET latin1 */;
```
Создать пользователя gcdapi с паролем 12345*gcdapi, дав ему права доступа: 
  
```SQL

'GRANT ALL PRIVILEGES ON `gcd`.* TO \'gcdapi\'@\'%\''
```

В распакованных папках архива пункта <b>1</b> перейдите в <i>api-distribution/config</i> где будет расположен файл настроек application.properties и проверьте настройки. Если вы указали свои настройки, отличающиеся от тех, что по умолчанию, такие как имя БД, имя пользователя или пароль, то измените на соответствующие следующие свойства:
  
```Java

spring.datasource.url = jdbc:mysql://localhost:3306/gcd?createDatabaseIfNotExist=true&useSSL=false
spring.datasource.username = gcdapi
spring.datasource.password = 12345*gcdapi
```

Проверьте наличие свойства и его значение:
  
```Java

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
```
<H1>3. Настройки RabbitMQ</H1>
Если вы планируете использовать RabbitMQ, установленный на локальной машине, то проверьте настройки доступа с имененем пользователя и паролем. В приложении
используется пользователь по умолчанию:
  
```Java
messaging.rabbit.host.user = guest
messaging.rabbit.host.password = guest
```
Если у вас создан другой пользователь, то замените свойства выше на свои значения.
Расположение сервера с RabbitMQ так же используется по умолчанию:
  
```Java
messaging.rabbit.host.url = localhost
messaging.rabbit.host.port = 5672
```
Теперь перейдём к запуску приложения.

<H1>4. Запуск приложения</H1>
Перед запуском приложения api должны быть запущены СУБД и RabbitMQ, настроенные в пунктах <b>2</b> и <b>3</b>, а так же приложение вычислитель, которое вы можете скачать из репозитория по ссылке
<href>https://github.com/IvanYankovskiy/GCDCalculator.git<href>. Там же вы найдете инструкции по настройке и запуску приложения вычислителя.

Для запуска приложения перейдите в распакованную на шаге <b>1</b> папку с приложением и перейдите в <i>api-distribution/bin</i>
Приложение доступно на порту 8080 по умолчанию. Вы можете заменить его на свой порт изменив свойство файла настроек:
  
```Java

server.port = 8080
```
Для запуска приложения в ОС Windows запустите <i>start.bat</i>.
Для запуска приложения приложения в ОС Linux запустите <i>start.sh</i>.
При этом должны быть запущены СУБД и RabbitMQ, настроенные в пунктах <b>2</b> и <b>3</b>, а так же приложение вычислитель.

<H1>5. Работа с API</H1>
Для отправки задания на поиск НОД для двух чисел выполните POST запрос с указанием в header запроса <i>Content-Type:application/json
</i>

<i>POST {host}/calculate-gcd</i>
  
```Json

{
"first" : <long>,
"second" : <long>
}
```
при если в запросе будут отсутсвовать хотя бы одно из полей "first" или "second", то api вернет сообщение об ошибке.
Значение этих полей должно быть строго больше 0, то есть все положительные числа.
При успешной отправке задание, приложение вернёт id задания. Чтобы проверить состояние задания, выполните следующий запрос:

<i>GET {host}/get-result/{id}</i> 
