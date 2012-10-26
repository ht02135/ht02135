docs:
1>http://jmeter.apache.org/usermanual/index.html
2>http://jmeter.apache.org/usermanual/component_reference.html 

test files:
1>are in jmx folder

knowledge:
Throughput = total req/sec at the server end
Response = time in terms of jmeter is the time to get the first byte from the server 

N = (R+z)*X

N--> number of concurrent users
R--> response time
z--> think time
X--> throughput 

In a normal situation the graphs of both are identical, increasing throughput leads to 
increasing response times and vice versa. A higher throughput means that more data has 
to be sent from the server to the client and this will take more time. 

What you're saying is that the response times degrade if you increase the throughput 
(as in requests per second).  Based on 225 threads making a single request with a rampup 
of 110 seconds your throughput is going to be in the region of 2 requests every second. 
Increasing the loop count to 3 is going to up that by around a factor of 3 to 6 requests 
a second (assuming no timers). Except of course if the response times are increasing then 
you will not reach this level of throughput which is you problem.

