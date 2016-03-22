### Intro
Performs a scheduled import of maintenance data into a DB2 or mysql database

cf login -a https://api.eu-gb.bluemix.net -u jason.tan@baesystems.com
cf push jbwtan-spring-batch-maintenance -p target/spring-batch-maintenance-1.3.2.RELEASE.jar -b https://github.com/cloudfoundry/java-buildpack.git --no-route
cf logs jbwtan-spring-batch-maintenance --recent


cf ic build -t <private_Bluemix_repository>/<image_name>:<tag> <dockerfile_location>


## Building docker image
### Windows only
Run Docker Quickstart Terminal
docker-machine env default
& "C:\Program Files\Docker Toolbox\docker-machine.exe" env default | Invoke-Expression

### Login to cf
cf login -u jason.tan@baesystems.com
cf ic login

### Test
docker build -t maintenance:v2 <dockerfile_location>
docker run -t maintenance:v2
docker ps
docker exec -it {id from docker ps} bash

### Deploy image to bluemix. namespace here is jbwtan
cf ic build -t registry.eu-gb.bluemix.net/jbwtan/maintenance:v2 <dockerfile_location>


### Create a container on bluemix and deploy with name jbwtan-maintenance and image maintenance:v2
cf ic rm --force jbwtan-maintenance 
cf ic run --name jbwtan-maintenance -m 2048 -t registry.eu-gb.bluemix.net/jbwtan/maintenance:v2 

### Take a peak inside
cf ic ps -a
cf ic exec -it jbwtan-maintenance bash
## DB2 SYNTAX
SELECT * FROM "USER04533"."PLANNED_MAINTENANCE" WHERE REFERENCE='IM416614'


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
