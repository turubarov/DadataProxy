# Информация о базе данных
- Имя базы данных: suggestions
- Пользователь: postgres
- Пароль: 12345
- скрипт на создание таблиц create_tables.sql

# Формат запросов
## suggest
Запрос на выдачу подсказок для автозаполнения адреса
### Параметры
- query
Строка с неполным адресом
###  Пример запроса
*/dadata/suggest?query=новосибирск николаева*

## search
Запрос на поиск по адресам, уже хранящимся в базе данных 
### Параметры
- type
Задаёт параметр для поиска. 
Допускаются следующие значения
*city* - поиск по названию города
*street* - поиск по названию улицы
*region* - поиск по названию региона
*settlement* - поиск по названию посёлка
- query
Строка для поиска
###  Пример запроса
*/dadata/search?type=city&query=новосибирск*