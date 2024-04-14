## Webserver

### Run

To run the `webserver` in raw version, execute the following command in a repository's root directory:

```

```

Navigate to [http://0.0.0.0:8080/](http://0.0.0.0:8080/) to see the output.

### Raw (non-dependency) version

Server log

```
[INFO] Application - Responding at http://0.0.0.0/0.0.0.0:8080

// GET / Request
[TRACE] Application found route.

// GET /index.html Request
[TRACE] Application not found route.
```

### Ktor version

Server log

```
2024-04-13 23:31:01.029 [main] INFO  Application - Autoreload is disabled because the development mode is off.
2024-04-13 23:31:01.161 [main] INFO  Application - Application started in 0.147 seconds.
2024-04-13 23:31:01.229 [DefaultDispatcher-worker-1] INFO  Application - Responding at http://0.0.0.0:8080

// GET / Request
2024-04-13 23:31:07.072 [eventLoopGroupProxy-4-2] TRACE io.ktor.routing.Routing - Trace for []
/, segment:0 -> SUCCESS @ /
/, segment:0 -> SUCCESS @ /
/(method:GET), segment:0 -> SUCCESS @ /(method:GET)
/{...}, segment:0 -> FAILURE "Better match was already found" @ /{...}
Matched routes:
"" -> "<slash>" -> "(method:GET)"
Route resolve result:
SUCCESS @ /(method:GET)

// GET /index.html Request
2024-04-13 23:31:08.619 [eventLoopGroupProxy-4-2] TRACE io.ktor.routing.Routing - Trace for [index.html]
/, segment:0 -> SUCCESS @ /
/, segment:0 -> SUCCESS @ /
/(method:GET), segment:0 -> FAILURE "Not all segments matched" @ /(method:GET)
/{...}, segment:1 -> SUCCESS; Parameters [static-content-path-parameter=[index.html]] @ /{...}
/{...}/(method:GET), segment:1 -> SUCCESS @ /{...}/(method:GET)
Matched routes:
"" -> "<slash>" -> "{...}" -> "(method:GET)"
Route resolve result:
SUCCESS; Parameters [static-content-path-parameter=[index.html]] @ /{...}/(method:GET)

// GET /secrets.html (not existed file) Request
2024-04-14 00:10:44.650 [eventLoopGroupProxy-4-2] TRACE io.ktor.routing.Routing - Trace for [secrets.html]
/, segment:0 -> SUCCESS @ /
  /, segment:0 -> SUCCESS @ /
    /(method:GET), segment:0 -> FAILURE "Not all segments matched" @ /(method:GET)
    /{...}, segment:1 -> SUCCESS; Parameters [static-content-path-parameter=[secrets.html]] @ /{...}
      /{...}/(method:GET), segment:1 -> SUCCESS @ /{...}/(method:GET)
Matched routes:
  "" -> "<slash>" -> "{...}" -> "(method:GET)"
Route resolve result:
  SUCCESS; Parameters [static-content-path-parameter=[secrets.html]] @ /{...}/(method:GET)
```