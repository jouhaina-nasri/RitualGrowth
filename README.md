# events-RG
The project consists of the following sub-projects:
* __events-parent__ - parent maven project
    * [__events-common__](events-common) - classes shared by all/most of the other projects
    * [__events-data__](events-data)  - record definitions _
    * [__events-repos__](events-repos) - repository layer
        * [__events-repos-jpa__](events-repos/events-repos-jpa) - JPA repositories
    * [__events-web-common__](events-web-common) - common classes used by all webapp/REST api projects
    * [__events-influxdb__](hippo-rest) :computer: - the app REST api server for writing events in influxdb
    * [__events-rest-rg__](hippo-rest) :computer: - the app REST api server for ritual growth

## Modes and profiles
At the moment the system runs in 2 different modes:
* __development__ - Development on local developer machines - __develpment__ profile - ___the default___ profile
* __testserver__ - Testing on the test server. It currently runs 2 instances of the same version
    * for perfmance testing - __testserver-pt__ profile
    * for the app testing - __testserver__ profile

There should more running modes and profiles in the future: production, staging, ...

## General notes
* The system builds and runs on Java 8 for now
* All the properties that use some locally defined domain names (ususally defined in /etc/hosts file) have to be mapped on the target machine (environment) or replaced with the raw IP address

## Building the projects
Except external dependencies, the Hippo project depends on some [dataprocessing](https://github.com/hippo-labs-inc/dataprocessing) sub-projects. For the sake of simplicity it's better to build the whole [dataprocessing](https://github.com/hippo-labs-inc/dataprocessing) project (instead of partial build) prior to building the Hippo project. The dataprocessing branch that is currently compatible with the latest Hippo version is [feature-hippo-start](https://github.com/hippo-labs-inc/dataprocessing/tree/feature-hippo-start).

Steps to build the [dataprocessing](https://github.com/hippo-labs-inc/dataprocessing) project:
* if the machine that is supposed to build the projects does not have Jenkins (Nexus) connection setup:
    * go to _[dataprocessing_root]/build_ : `cd [dataprocessing_root]/build`
    * run the scrpt _build-without-jenkins.sh_: `./build-without-jenkins.sh`
* go to _[dataprocessing_root]/dataprocessing_ : `cd [dataprocessing_root]/dataprocessing`
* run the build command: `mvn clean install`
    * Optionally, use parameter _-DskipTests_ to skip the tests while building. __It is not recommanded!__ If the test are failiing they should be fixed first!

After successfully building the [dataprocessing](https://github.com/hippo-labs-inc/dataprocessing) project, build the Hippo project

Steps to build the dataprocessing project:
* go to _[events_root] : `cd [events_root]`
* run the build command: `mvn clean install`
    * Optionally, use parameter _-DskipTests_ to skip the tests while building. __It is not recommanded!__ If the test are failiing they should be fixed first!

## Microservices (servers)
At the moment the system consists of 2 Ritual Growth microservices. There are additional external services as well.

* __events-rest-rg__
    * the main  Ritual Growth app backend server
* __events-influxdb__
    * This microservice is used for tracking and writing events in influxdb

The external microservices/components are:
* __Firebase__ - providing JWT based authentication (integrated with Google, Apple and Facebook for now) and push messaging functionality


## <a name='passmgt'>Password management</a>
No passwords or sensitive information should be stored in the code or the config/properties files. All the password properties are externalized. When running any of the services for the first time it will interactively ask for the required password(s) (if any). At the and it will ask for the 'master' password. The 'master' password is used to encrypt the other passwords into a .bin file. The next time the service is ran (restarted) it will only ask for the 'master' password.

## Testing server setup
The services are deployed for testing on a dedicated server behind an nginx reverse proxy with HTTPS (Let's Encrypt). Host names, IP addresses and the nginx mapping are kept out of this repository.

## Tests
Tests live in `events-influxdb/src/test/java` and use JUnit 5 (Jupiter), Mockito and AssertJ.

* Unit tests (no external services needed) run on every build:
  `mvn test`
* Integration tests are tagged `@Tag("integration")` and need PostgreSQL, Redis and InfluxDB running (see `docker-compose.yml`). They are skipped by default; run them with:
  `mvn verify -Pintegration-tests`

## Running instructions

### events-influxdb
It can run in development and testing mode, for now. Each profile has a corresponding .properties file (in [resources](events-influxdb/src/main/resources) folder):

Profile                | .properties file
-----------------------| ------------------------------------ 
_development_ (default)| applicaiton-development.properties
testserver             | applicaiton-testserver.properties
testserver-pt          | applicaiton-testserver-pt.properties

#### Component dependencies
* __Firebase__ - used for app user authentication (based on JWT) and push messaging. It requires a configuration file (Firebase Admin SDK service-account JSON) to be provided. The file is not stored in this repository cause it contains sensitive information. The path to the file has to match the corresponding property in the .properties file.

More info about the properties from the .properties files will be added as comments in the files.

Steps to run the service:
1. if necessary, create the db file as previously explained and copy it to where it should be used (the same server where the jar file is supposed to be deployed)
2. if needed, update the db file reference in the corresponding .properties file
3. build the entire Hippo project (or only the hippo-rest sub-project) unsing the command `mvn clean install`
4. the executable .jar file will be placed in the _hippo-rest/target_ folder. Something like _events-influxdb.\*.jar_
5. copy the .jar file to where it should run (like testserver and then login to the server)
6. run a new terminal session using byobu or some alternative
7. to run the service use command `java -Dspring.profiles.active=[choosen_proflie] -jar /path/to/executable_jar_file.jar`. `-Dspring.profiles.active` is optional and it defaults to 'development'
8. provide required passwords as described in the [Passwords management](#passmgt) section
9. detach from the terminal session
10. if enabled, check the API docs (verify if the server is running). There should be automated API testing in the future.

With the current settings, based on the chosen profile the urls to check are:

Profile       | API docs url
------------- | ----------------------------------------------
development   | http://localhost:8084/docs
testserver    | ............................................
testserver-pt | ............................................

### events-rest-rg
It can run in development and testing mode, for now. Each profile has a corresponding .properties file (in [resources](events-rest-rg/src/main/resources) folder):

Profile                | .properties file
-----------------------| ------------------------------------ 
_development_ (default)| applicaiton-development.properties
testserver             | applicaiton-testserver.properties
testserver-pt          | applicaiton-testserver-pt.properties

#### Component dependencies
* __Firebase__ - used for app user authentication (based on JWT) and push messaging. It requires a configuration file (Firebase Admin SDK service-account JSON) to be provided. The file is not stored in this repository cause it contains sensitive information. The path to the file has to match the corresponding property in the .properties file.

More info about the properties from the .properties files will be added as comments in the files.

Steps to run the service:
1. build the entire Hippo project (or only the hippo-rest sub-project) unsing the command `mvn clean install`
2. the executable .jar file will be placed in the _hippo-ir/target_ folder. Something like _events-rest-rg.\*.jar_
3. copy the .jar file to where it should run (like testserver and then login to the server)
4. run a new terminal session using byobu or some alternative
5. to run the service use command `java -Dspring.profiles.active=[choosen_proflie] -jar /path/to/executable_jar_file.jar`. `-Dspring.profiles.active` is optional and it defaults to 'development'
6. provide required passwords as described in the [Passwords management](#passmgt) section
7. detach from the terminal session
8. if enabled, check the API docs (verify if the server is running). There should be automated API testing in the future.

With the current settings, based on the chosen profile the urls to check are:

Profile       | API docs url
------------- | ----------------------------------------------
development   | http://localhost:8085/docs
testserver    | ............................................
testserver-pt | ............................................

![RG](https://user-images.githubusercontent.com/63677147/218149263-b4851743-2b3f-4edb-a291-9665a78915bf.png)


