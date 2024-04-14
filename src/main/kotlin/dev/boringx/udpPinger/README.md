## UDP Pinger

### Run

To run the `Server`, execute the following command in a repository's root directory:
```

```

To run the `Client`, execute the following command in a repository's root directory:
```

```


### Hosting on same machines
#### Client log (client hosted on same machine than server is)
```
Request timed out
Response: PING 2 1713082348945
RTT: 0.001 seconds
Response: PING 3 1713082348951
RTT: 0.0 seconds
Response: PING 4 1713082348951
RTT: 0.001 seconds
Response: PING 5 1713082348952
RTT: 0.001 seconds
Response: PING 6 1713082348953
RTT: 0.001 seconds
Request timed out
Request timed out
Request timed out
Response: PING 10 1713082351955
RTT: 0.001 seconds
```

#### Server log
```
Package loss
Successfully response on request
Successfully response on request
Successfully response on request
Successfully response on request
Successfully response on request
Package loss
Package loss
Package loss
Successfully response on request
```

### Hosting on different machines
#### Client log
```
Request timed out
Request timed out
Request timed out
Response: PING 4 1713083206122
RTT: 0.045 seconds
Response: PING 5 1713083206191
RTT: 0.004 seconds
Response: PING 6 1713083206195
RTT: 0.005 seconds
Request timed out
Response: PING 8 1713083207211
RTT: 0.008 seconds
Request timed out
Request timed out
```

#### Server log
```
Packet lost
Packet lost
Packet lost
Successfully response on request
Successfully response on request
Successfully response on request
Packet lost
Successfully response on request
Packet lost
Packet lost
```