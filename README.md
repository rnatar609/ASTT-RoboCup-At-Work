Installation
------------

Requirements: 
* Linux, Windows, OS X
* Java 1.7
* ZeroMQ 2.2


### Java 1.7
To run and contribute to the Referee Box you need to have Java 1.7. [Download](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

* Linux
If your Linux does not provide a deb file for java 1.7 you can use these [instructions](http://askubuntu.com/questions/55848/how-do-i-install-oracle-java-jdk-7) to install it.

### ZeroMQ 2.2 
The middle-ware ZeroMQ can be found here: [zeromq.org](http://www.zeromq.org)
You first need to install the core ZeroMQ and afterwards the language bindings like java, python, C++.


Usage
------------

The Referee Box (server) can be found in the folder RefereePkg.
The C++, Python, Java client can be found in the folder RobotPkg.


After you started the Referee Box you first have to load a scenario configuration file. Which you can find in the folder config. Now you are able to create a sequence of triplet for the basic navigation test. (Right now only the basic navigation test is implemented.) When you are finished save the sequence with the save button. Now you are able to start the timer for the setup time. 
When a team is connected to the server, by using one of the client implementations, you can send the triplet to the client. Automatically when the task specification is send to the team the run timer will start.

The sequence in short:

0. edit the server IP address int the config/config.txt file
1. start Referee Box application
2. load scenario configuration file
3. load or create task specification
4. save the task specification
5. client gets connected
6. start setup timer
7. send task specification
8. monitor the run and tick passed are failed for the way points
9. when the team is finished stop the run timer.
10. save the competition to a file with the Competition Finished button

