# IOTHUB-F4I (IoT hub FIWARE4Industry 4.0)

[//]: # (Common expressions:)
[//]: # (* IOTHUB-F4I)

## Table of contents

* [How to Use start up the system](#section_start)

<a name='section_start'/>
## How to use start up the system
Prerequisites to run the system correctly:

[Node.js](https://nodejs.org/), [npm](https://www.npmjs.com/),  [Java8](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html), [Maven](https://maven.apache.org/) and a [MQTT](http://mqtt.org/) broker running.

Clone the repository
```sh
$ git clone https://github.com/IOTHUB-F4I/IoThub.git IoThub
```

Prepare iotagent-node-lib as a dependency of iotagent-mqtt
```sh
$ cd IoThub/iotagent-node-lib
$ npm install
```
Prepare iotagent-mqtt
```sh
$ cd IoThub/iotagent-mqtt
$ npm install
```
Copy iotagent-node-lib into node_module folder of iotagent-mqtt
```sh
$ cd IoThub/
$ cp -r iotagent-node-lib/ iotagent-mqtt/node_modules/
```
Before starting the iotagent-mqtt, check config.json file, where it has to be set where the MQTT broker is running and in which port will the agent be running:
```sh
config.mqtt = {
    host: 'localhost',
    port: 1883,
    defaultKey: 'IOTHUB',
    thinkingThingsPlugin: true
}
server: {
    port: 4041
}
```
Start iotagent-mqtt
```sh
$ cd IoThub/iotagent-mqtt
$ bin/iotagentMqtt.js
```
Compile fiware-cepheus
```sh
$ cd IoThub/fiware-cepheus
$ mvn clean install
```
Start cepheus-broker
```sh
$ cd IoThub/fiware-cepheus
$ java -jar cepheus-broker/target/cepheus-broker-0.1.5-SNAPSHOT.jar
```
Start cepheus-cep
```sh
$ cd IoThub/fiware-cepheus
$ java -jar cepheus-cep/target/cepheus-cep-0.1.5-SNAPSHOT.jar
```
