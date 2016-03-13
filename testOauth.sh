http://www.baeldung.com/rest-api-spring-oauth2-angularjs

curl -v clientIdPassword:secret@localhost:8080/oauth/token -d grant_type=password -d username=vfg -d password=123

* Hostname was NOT found in DNS cache
*   Trying 127.0.0.1...
* Connected to localhost (127.0.0.1) port 8080 (#0)
* Server auth using Basic with user 'clientIdPassword'
> POST /oauth/token HTTP/1.1
> Authorization: Basic Y2xpZW50SWRQYXNzd29yZDpzZWNyZXQ=
> User-Agent: curl/7.35.0
> Host: localhost:8080
> Accept: */*
> Content-Length: 45
> Content-Type: application/x-www-form-urlencoded
> 
* upload completely sent off: 45 out of 45 bytes
< HTTP/1.1 200 OK
* Server Apache-Coyote/1.1 is not blacklisted
< Server: Apache-Coyote/1.1
< X-Content-Type-Options: nosniff
< X-XSS-Protection: 1; mode=block
< Cache-Control: no-cache, no-store, max-age=0, must-revalidate
< Pragma: no-cache
< Expires: 0
< X-Frame-Options: DENY
< Cache-Control: no-store
< Pragma: no-cache
< Content-Type: application/json;charset=UTF-8
< Transfer-Encoding: chunked
< Date: Wed, 09 Mar 2016 18:37:00 GMT
< 
* Connection #0 to host localhost left intact
{"access_token":"ee57ce08-b79b-4f54-a837-1af35f755a25","token_type":"bearer","refresh_token":"f011d883-5f17-4b2c-bb43-ff24c10f0f4d","expires_in":43189,"scope":"read"}


curl -X POST -v -d 'grant_type=password&username=vfg&password=123' --user 'clientIdPassword:secret' http://localhost:8080/oauth/token
curl -H -v "Authorization: bearer 7b375b80-c9ea-4f7b-b652-4b828da87cf4" http://localhost:8080/about