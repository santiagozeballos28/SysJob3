Feature: karate 'Send Vacation'

#SCENARIO 1
Scenario: Test delete history of employee by identifier employee
Given url 'http://localhost:8181/SysJob/historyVacation/deleteByIdEmployee/6'
When method DELETE
Then status 200

#SCENARIO 2
Scenario: Test delete day vacation by year
Given url 'http://localhost:8181/SysJob/daysVacation/delteByYear/2018'
When method DELETE
Then status 200

#SCENARIO 3
Scenario: Test create day vacation 
Given url 'http://localhost:8181/SysJob/daysVacation'
And request {}
When method POST
Then status 201

#SCENARIO 4
Scenario: Test of dates that are from the same year
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2017-03-06&endDate=2019-03-12'
And request {}
When method POST
Then status 400

#SCENARIO 5
Scenario: Test of dates that are from the same year with response of two messages
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2017-03-06&endDate=2019-03-12&reason=Travel%20to%20the%20USA%20for%20Java%20specialization'
And request {}
When method POST
Then status 400
And match $ == {status: "BAD_REQUEST",errors:[{keyError: 109, message: "The start date (2017-03-06) has to be greater than the current date [2018-02-27]."},{keyError: 105,message: "The end date (2019-03-12) must be in the present year [2018]."}]}

#SCENARIO 6
Scenario: Testing the exact response of a POST endpoint send Vacation acepted
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-07&endDate=2018-03-13&reason=Travel%20to%20the%20USA%20for%20Java%20specialization'
And request {}
When method POST
Then status 201
And match $ == {idHistoryVacation: '#notnull', idEmployee: 6, startDate: "2018-03-07", endDate: "2018-03-13", reason: "Travel to the USA for Java specialization", quantityDay: 5}
 
#SCENARIO 7
Scenario: Testing of an existing vacation and 10-day separation
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-13&endDate=2018-03-16'
And request {}
When method POST
Then status 400
And match $.errors[0].message == "There is already a vacation period from (2018-03-07) to (2018-03-13)."
And match $.errors[1].message == "Vacations must have a separation greater than or equal to 10 days."

#SCENARIO 8
Scenario: Testing 10 day separation  and the number of days exceeded
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-19&endDate=2018-04-04'
And request {}
When method POST
Then status 400
And match $.errors[0].message == "You are requesting 11 days, and it can only take 10 days."
And match $.errors[1].message == "Vacations must have a separation greater than or equal to 10 days."

#SCENARIO 9
Scenario: Testing 10 day separation (Limit)
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-28&endDate=2018-03-28'
And request {}
When method POST
Then status 400
And match $.errors[0].message == "Vacations must have a separation greater than or equal to 10 days."

#SCENARIO 10
Scenario: Testing the exact response of a POST endpoint send Vacation corrected
Given url 'http://localhost:8181/SysJob/historyVacation/6/sendVacation?startDate=2018-03-30&endDate=2018-03-31'
And request {}
When method POST
Then status 201
And match $.quantityDay == 1
