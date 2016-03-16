#!/bin/sh

clear

echo "\n-------------------------------------------------------------------------------\n"
echo "curl vfg..."
curl -X POST -v -d 'grant_type=password&username=vfg&password=vfg' --user 'blankClient_admin:' http://localhost:8080/blank/oauth/token

echo "\n-------------------------------------------------------------------------------\n"
echo "curl user..."
curl -X POST -v -d 'grant_type=password&username=user&password=user' --user 'blankClient_admin:' http://localhost:8080/blank/oauth/token

echo "\n-------------------------------------------------------------------------------\n"
echo "curl editor..."
curl -X POST -v -d 'grant_type=password&username=editor&password=editor' --user 'blankClient_admin:' http://localhost:8080/blank/oauth/token

echo "\n-------------------------------------------------------------------------------\n"
echo "curl user2.."
curl -X POST -v -d 'grant_type=password&username=user2&password=user2' --user 'blankClient_admin:' http://localhost:8080/blank/oauth/token

echo "\n-------------------------------------------------------------------------------\n"
echo "curl user3.."
curl -X POST -v -d 'grant_type=password&username=user3&password=user3' --user 'blankClient_admin:' http://localhost:8080/blank/oauth/token
echo "\n-------------------------------------------------------------------------------\n"


exit 0