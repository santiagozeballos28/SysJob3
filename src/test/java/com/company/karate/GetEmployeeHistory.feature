Feature: karate 'Employee History' example
Scenario: Testing valid GET endpoint
Given url 'http://localhost:8181/SysJob/employee/1/historyVacations'
When method GET
Then status 200

Scenario: TPrueba de la respuesta exacta de un punto final GET 
Given url 'http://localhost:8181/SysJob/employee/1/historyVacations'
When method GET
Then status 200
And match $ == {idEmployee: 1,lastName: "Vidal",firstName: "Lizeth",email: "lezeth@gmail.com",dateOfHire: "2017-12-04",historyVacations:[{idHistoryVacation: 2, idEmployee: 1, startDate: "2016-10-24", endDate: "2016-10-24",reason:"To travel to Rusia",quantityDay:1}, {idHistoryVacation: 1, idEmployee: 1, startDate: "2017-02-10", endDate: "2017-02-13",reason:"To travel to Silicon Valley",quantityDay:2}]}

Scenario: Testing valid GET endpoint last name 
Given url 'http://localhost:8181/SysJob/employee/1/historyVacations'
When method GET
Then status 200
And match $.lastName == "Vidal"

Scenario: Testing valid GET endpoint first name
Given url 'http://localhost:8181/SysJob/employee/1/historyVacations'
When method GET
Then status 200
And match $.firstName == "Lizeth"