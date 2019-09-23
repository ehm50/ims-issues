#!/bin/sh
mvn clean package && docker build -t org.digam/ims-issues .
docker run -p 8082:8080 --name ims-issues org.digam/ims-issues