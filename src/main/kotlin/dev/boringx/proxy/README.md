## Proxy Server

![img.png](img.png)

### Run

To run the `Proxy Server`, execute the following command in a repository's root directory:

```

```

Tested on:

- http://0.0.0.0:8080/raw.githubusercontent.com/b0r1ngx/computer-networks/main/build.gradle.kts
- http://0.0.0.0:8080/hips.hearstapps.com/hmg-prod/images/alpe-di-siusi-sunrise-with-sassolungo-or-langkofel-royalty-free-image-1623254127.jpg

Template to use: http://0.0.0.0:8080/ANYSITE.COM, where ANYSITE.COM is any web-server (site) that you want to visit
through localhost as proxy server.

### Logs

#### Cache miss

```
request: GET /hips.hearstapps.com/hmg-prod/images/alpe-di-siusi-sunrise-with-sassolungo-or-langkofel-royalty-free-image-1623254127.jpg HTTP/1.1
requestedPath: hips.hearstapps.com/hmg-prod/images/alpe-di-siusi-sunrise-with-sassolungo-or-langkofel-royalty-free-image-1623254127.jpg
Cache miss
Successfully create cache entry on server
[TRACE] Application found route.
```

#### Cache hit

```
[INFO] Application - Responding at http://0.0.0.0/0.0.0.0:8080
request: GET /raw.githubusercontent.com/b0r1ngx/computer-networks/main/build.gradle.kts HTTP/1.1
requestedPath: raw.githubusercontent.com/b0r1ngx/computer-networks/main/build.gradle.kts
Cache hit
[TRACE] Application found route.
```
