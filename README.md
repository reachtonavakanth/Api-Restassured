# rest-assured

## Introduction

In this POC below rest apis are automated. 

https://wiremock.org/ used for API mocking.


Given the following endpoints:
1. GET /location/get/all
2. POST /location/post

### /location/get/all
● Get all items and validate that LON, MAN, CAM and LCS were returned in the
response

**Response :**

{
"items":[
{
"id": "1842347-1560779940",
"cost": "£829.99",
"location": "LON"
},
{
"id": "1936481-1560779940",
"cost": "£150.29",
"location": "MAN"
},
{
"id": "1936504-1560779940",
"cost": "£1164.74",
"location": "CAM"
},
{
"id": "1936527-1560779940",
"cost": "£279.99",
"location": "LCS"
}]}

### /location/post
Validate new item addition using POST and the response returns all the items.

Payload:

{
"id": "1842347-1560779940",
"cost": "£829.99",
"location": "PAC"
}

Response 

{
"id": "1842347-1560779940",
"cost": "£829.99",
"location": "PAC"
}