### Intro
Performs a scheduled import of coverage data into DB2 or mysql database

cf login -a https://api.eu-gb.bluemix.net -u jason.tan@baesystems.com
cf push jbwtan-spring-batch-coverage -p target/spring-batch-coverage-1.3.2.RELEASE.jar -b https://github.com/cloudfoundry/java-buildpack.git --no-route
cf logs jbwtan-spring-batch-coverage --recent


cf ic build -t <private_Bluemix_repository>/<image_name>:<tag> <dockerfile_location>


## Building docker image
### Windows only
Run Docker Quickstart Terminal
docker-machine env default
& "C:\Program Files\Docker Toolbox\docker-machine.exe" env default | Invoke-Expression

### Login to cf
cf login -u jason.tan@baesystems.com -p
cf ic login

### Test
docker build -t coverage:v1 <dockerfile_location>
docker run -t coverage:v1
docker ps
docker exec -it {id from docker ps} bash

### Deploy image to bluemix. namespace here is jbwtan

cf install-plugin https://static-ice.ng.bluemix.net/ibm-containers-linux_x64 (for cf ic)
cf ic build -t registry.eu-gb.bluemix.net/jbwtan/coverage:v1 <dockerfile_location>


### Create a container on bluemix and deploy with name jbwtan-coverage and image coverage:v1
cf ic rm --force jbwtan-coverage
cf ic run --name jbwtan-coverage -m 2048 -t registry.eu-gb.bluemix.net/jbwtan/coverage:v1

### Take a peak inside
cf ic ps -a
cf ic exec -it jbwtan-coverage bash
## DB2 SYNTAX
SELECT * FROM "USER04533"."COVERAGE_CHECKER" WHERE REFERENCE='IM416614'

### List available ips. Note we need a public ip to access db2
cf ic ip list
cf ic ip bind 134.168.46.94 jbwtan-coverage

## TODO:
Convert this into a maven multi module project
One pom to build all the modules
One docker file to copy over all the jars to image
Start jars within container

external application.properties file

Setup build pipeline.
Build from git
Deploy to IBM Container
Deploy to QA space
Deploy to prod
