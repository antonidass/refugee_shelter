# Без балансировки

akrik~/Desktop/refugee_shelter $ ab -c 10 -n 10000 -H "Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha3JpayIsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiaXNzIjoiL2FwaS92MS9sb2dpbiIsImV4cCI6MTY2NzgyODEzNn0.-7IVkJPJC3xnsvXNyu-ZWbZruNljfZ9CG6fjYp3h8oM" http://127.0.0.1:8080/api/v1/users  
This is ApacheBench, Version 2.3 <$Revision: 1879490 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests

Server Software:  
Server Hostname: 127.0.0.1
Server Port: 8080

Document Path: /api/v1/users
Document Length: 721 bytes

Concurrency Level: 10
Time taken for tests: 45.343 seconds
Complete requests: 10000
Failed requests: 0
Total transferred: 8650000 bytes
HTML transferred: 7210000 bytes
Requests per second: 220.54 [#/sec] (mean)
Time per request: 45.343 [ms] (mean)
Time per request: 4.534 [ms] (mean, across all concurrent requests)
Transfer rate: 186.30 [Kbytes/sec] received

Connection Times (ms)
min mean[+/-sd] median max
Connect: 0 0 0.0 0 2
Processing: 5 45 29.2 37 346
Waiting: 4 43 29.1 35 345
Total: 5 45 29.2 37 346

Percentage of the requests served within a certain time (ms)
50% 37
66% 49
75% 57
80% 63
90% 81
95% 101
98% 129
99% 150
100% 346 (longest request)

# С балансировкой

akrik~/Desktop/refugee_shelter $ ab -c 10 -n 10000 -H "Authorization:Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJha3JpayIsInJvbGVzIjpbIlJPTEVfQURNSU4iLCJST0xFX1VTRVIiXSwiaXNzIjoiL2FwaS92MS9sb2dpbiIsImV4cCI6MTY2NzgyODEzNn0.-7IVkJPJC3xnsvXNyu-ZWbZruNljfZ9CG6fjYp3h8oM" http://127.0.0.1:8080/api/v1/users
This is ApacheBench, Version 2.3 <$Revision: 1879490 $>
Copyright 1996 Adam Twiss, Zeus Technology Ltd, http://www.zeustech.net/
Licensed to The Apache Software Foundation, http://www.apache.org/

Benchmarking 127.0.0.1 (be patient)
Completed 1000 requests
Completed 2000 requests
Completed 3000 requests
Completed 4000 requests
Completed 5000 requests
Completed 6000 requests
Completed 7000 requests
Completed 8000 requests
Completed 9000 requests
Completed 10000 requests
Finished 10000 requests

Server Software:  
Server Hostname: 127.0.0.1
Server Port: 8080

Document Path: /api/v1/users
Document Length: 721 bytes

Concurrency Level: 10
Time taken for tests: 33.659 seconds
Complete requests: 10000
Failed requests: 0
Total transferred: 8650000 bytes
HTML transferred: 7210000 bytes
Requests per second: 297.09 [#/sec] (mean)
Time per request: 33.659 [ms] (mean)
Time per request: 3.366 [ms] (mean, across all concurrent requests)
Transfer rate: 250.96 [Kbytes/sec] received

Connection Times (ms)
min mean[+/-sd] median max
Connect: 0 0 0.0 0 1
Processing: 5 33 17.9 28 159
Waiting: 3 31 17.7 27 158
Total: 5 34 17.9 29 159

Percentage of the requests served within a certain time (ms)
50% 29
66% 35
75% 41
80% 45
90% 58
95% 68
98% 83
99% 96
100% 159 (longest request)
