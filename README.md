# subscriber-list-service
REST service for processing subscriber lists. Apache Ignite, JAX-RS, Docker.

Link to docker: https://hub.docker.com/r/avoronov/public/

How to start:
1. docker pull avoronov/public
2. docker run avoronov/public

Example requests:

1. Add association Cell - Phone:
POST 
/service/rest/msisdn
{
"cellId"	:	1, 
"ctn"     :	12345678
}

2. Add profile:
POST 
/service/rest/profile
{
"ctn"			      :	12345678,
"name"			    :	"Willson",
"email"			    :	"willson@example.com",
"activateDate"	:	"2017-02-24 12:45:00"
}

3. Get subscribers:
GET 
/service/rest/subscribers?cellId=1

