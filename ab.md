# Без балансировки
akrik~/Desktop/refugee_shelter $ ab -c 10 -n 10000 http://127.0.0.1:8080/api/v1/rooms 
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


Server Software:        nginx/1.23.1
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /api/v1/rooms
Document Length:        10100 bytes

Concurrency Level:      10
Time taken for tests:   38.951 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      104090000 bytes
HTML transferred:       101000000 bytes
Requests per second:    256.73 [#/sec] (mean)
Time per request:       38.951 [ms] (mean)
Time per request:       3.895 [ms] (mean, across all concurrent requests)
Transfer rate:          2609.71 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0    9 374.7      0   19513
Processing:     7   30 422.7     17   19534
Waiting:        7   30 422.7     17   19534
Total:          7   39 580.2     17   19833

Percentage of the requests served within a certain time (ms)
  50%     17
  66%     19
  75%     21
  80%     23
  90%     28
  95%     34
  98%     53
  99%     68
 100%  19833 (longest request)
# С балансировкой

akrik~/Desktop/refugee_shelter $ ab -c 10 -n 10000 http://127.0.0.1:8080/api/v1/rooms 
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


Server Software:        nginx/1.23.1
Server Hostname:        127.0.0.1
Server Port:            8080

Document Path:          /api/v1/rooms
Document Length:        10100 bytes

Concurrency Level:      10
Time taken for tests:   37.478 seconds
Complete requests:      10000
Failed requests:        0
Total transferred:      104090000 bytes
HTML transferred:       101000000 bytes
Requests per second:    266.82 [#/sec] (mean)
Time per request:       37.478 [ms] (mean)
Time per request:       3.748 [ms] (mean, across all concurrent requests)
Transfer rate:          2712.25 [Kbytes/sec] received

Connection Times (ms)
              min  mean[+/-sd] median   max
Connect:        0   11 317.1      0   13110
Processing:     7   26 198.7     19   13179
Waiting:        7   26 198.7     19   13179
Total:          7   37 374.6     19   13180

Percentage of the requests served within a certain time (ms)
  50%     19
  66%     21
  75%     23
  80%     24
  90%     30
  95%     41
  98%     70
  99%    121
 100%  13180 (longest request)