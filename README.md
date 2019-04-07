# Flight Search API

Spring MVC based RESTful API to search over available flights.

# Tools
* ##### Spring Boot 2.1.4
* ##### Java 8

# Installation

#### 1. Install  Eclipse IDE
#### Linux :
```sh
$ sudo apt install default-jre
$ sudo snap install --classic eclipse
```
#### Windows : 
Download installation [here](https://www.eclipse.org/downloads/)

#### 2. Install  Postman
#### Linux :
```sh
$ sudo apt install snapd snapd-xdg-open
```
#### Windows : 
Download installation [here](https://www.getpostman.com/downloads/)

# How to Use
* Run the code and wait until tomcat run on http://localhost:8080/
* If you want to change the port number, go to src\main\resources\application.properties and change servfer.port value
* to use API you can access it via postman or browser by writing this URL : http://localhost:8080/flight/search
* Enter your parameter key and value, for example : http://localhost:8080/flight/search?departure=Reconquista
* It will give you output like this :
```JSON
[ {
  "id" : "18ac80f2-2c73-4cf6-b7cf-0ce1b2e6f3b0",
  "departure" : "Reconquista",
  "arrival" : "Caballito",
  "arrivalTime" : "2019-04-07 02:57:24",
  "departureTime" : "2019-04-07 02:09:29"
} ]
```

# Parameters
| Paramater Key       | Parameter Value | Brief Explanation    |
| :-------------------: |:----------------------------:| :-----------------|
| departure            | (String) , ex : "Reconquista"  | Show by departure location name |
| arrival           | (String) , ex : "Perez" |   Show by arrival location name |
| departureTime|(String), ex : "2019-04-07 04:57:06"|Show all that has departure time less than or equal with departureTime |
| arrivalTime| (String), ex : "2019-04-07 04:57:06"|    Show all that has Arrival time less than or equal with arrivalTime  |
| pagination | (integer), ex : 1,2,... |Show the page and each page has maximum 10 datas|
| sortColumn | (String), ex : "arrival"   | Option to sort with specific column name |
| sortOrder | (String), ex : "asc", "desc" | Option to sort by ascending or descending |
| filter | (String), ex : "cheap", "business" | Option to filter data by the source (Cheap, Business) |

# Notes
* This is the source to get the real-time data : [cheap](https://obscure-caverns-79008.herokuapp.com/cheap) and [business](https://obscure-caverns-79008.herokuapp.com/business). However, for training purpose I used the json that can be accessed in [cheap](https://testnlearn.000webhostapp.com/cheap.json) and [business](https://testnlearn.000webhostapp.com/business.json). Another reason is the data in real-time source is changing fast, such that sometime you will get empty response when you give specific key as the parameter.
* To change the source, you can change it in src\main\java\com\sbfs\api\model\FlightAggregation.java and change the value of "URL_CHEAP" and "URL_BUSINESS".

