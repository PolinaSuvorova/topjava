Java Enterprise Online Project 
===============================
cURL requests for test MealRestController:

get all meal for user test
curl http://localhost:8080/topjava/rest/meals

Create meal test
curl -X POST http://localhost:8080/topjava/rest/meals -H "Content-Type: application/json" -d '{"dateTime": "2020-01-31T08:00:00","description": "Ланч","calories": 100}'

get meal 
curl http://localhost:8080/topjava/rest/meals/100008

delete meal
curl -X DELETE http://localhost:8080/topjava/rest/meals/100008

update meal
curl -X PUT 'http://localhost:8080/topjava/rest/meals/100012' -H 'Content-Type: application/json' --data-raw '{"dateTime": "2020-01-31T05:00:00","description": "Ужин","calories": 100}'

get meal with filter
curl GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-30&endTime=12:00'