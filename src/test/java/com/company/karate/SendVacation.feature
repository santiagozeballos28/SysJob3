Feature: karate 'Send Vacation'
Scenario: Testing valid POST endpoint
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2017-03-06&endDate=2019-03-12'
And request {}
When method POST
Then status 400

Scenario: Testing the exact response of a POST endpoint
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2017-03-06&endDate=2019-03-12&reason=Travel%20to%20the%20USA%20for%20Java%20specialization'
And request {}
When method POST
Then status 400
And match $ == {status: "BAD_REQUEST",errors:[{keyError: 109, message: "The start date (2017-03-06) has to be greater than the current date [2018-02-26]."},{keyError: 105,message: "The end date (2019-03-12) must be in the present year [2018]."}]}

Scenario: Testing the exact response of a POST endpoint send Vacation acepted
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-06&endDate=2018-03-12&reason=Travel%20to%20the%20USA%20for%20Java%20specialization'
And request {}
When method POST
Then status 201
And match $.quantityDay == 5

Scenario: Testing the exact response of a POST endpoint send Vacation 
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-12&endDate=2018-03-15'
And request {}
When method POST
Then status 400
And match $.errors[0].message == "There is already a vacation period from (2018-03-06) to (2018-03-12)."
And match $.errors[1].message == "Vacations must have a separation greater than or equal to 10 days."

Scenario: Testing the exact response of a POST endpoint send Vacation error separation days and remaining
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-19&endDate=2018-03-28'
And request {}
When method POST
Then status 400
And match $.errors[0].message == "Vacations must have a separation greater than or equal to 10 days."
And match $.errors[1].message == "You are requesting 6 days, and it can only take 5 days."

Scenario: Testing the exact response of a POST endpoint send Vacation error separatio day
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-28&endDate=2018-03-28'
And request {}
When method POST
Then status 400
And match $.errors[0].message == "Vacations must have a separation greater than or equal to 10 days."

Scenario: Testing the exact response of a POST endpoint send Vacation corrected
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-29&endDate=2018-03-30'
And request {}
When method POST
Then status 201
And match $.quantityDay == 2
