# subscriber-list-service
REST service for processing subscriber lists. Apache Ignite, JAX-RS, Docker.

1. Add association Cell - Phone:
POST /rest/msisdn
{
"cellId"	:	1, 
"ctn"     :	12345678
}

2. Add profile:
POST /rest/profile
{
"ctn"			      :	12345678,
"name"			    :	"Willson",
"email"			    :	"willson@example.com",
"activateDate"	:	"2017-02-24 12:45:00"
}

3. Get subscribers:
GET /rest/subscribers?cellId=1

