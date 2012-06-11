# Installation for development

The software should run under Linux, Windows and OS X.

## Requirements: 
### A [Java Runtime Environment](http://www.oracle.com/technetwork/java/javase/downloads/index.html) (JDK7 or newer) and Eclipse SDK (version 3.7.2 or newer).

### [ØMQ The Intelligent Transport Layer](http://www.zeromq.or) (version 2.2) with the language bindings Java and Python.

## Installation under Ubuntu Linux: 

### Install Java JDK7 under Ubuntu 10.04
download the JDK7 form [Oracle](http://www.oracle.com/technetwork/java/javase/downloads/index.html) as tar.gz
    tar -xvf jdk-7u4-linux-i586.tar.gz
    sudo mv ./jdk1.7.0_04 /usr/lib/jvm/jdk1.7.0
    sudo update-alternatives --install "/usr/bin/java" "java" "/usr/lib/jvm/jdk1.7.0/bin/java" 1
    sudo update-alternatives --install "/usr/bin/javac" "javac" "/usr/lib/jvm/jdk1.7.0/bin/javac" 1
    sudo update-alternatives --install "/usr/bin/javaws" "javaws" "/usr/lib/jvm/jdk1.7.0/bin/javaws" 1

Choose the number for jdk1.7.0
    sudo update-alternatives --config java
Check the version of you new JDK 7 installation:
    java -version
Repeat the above for:
    sudo update-alternatives --config javac
    sudo update-alternatives --config javaws

### Install ØMQ Core
    sudo apt-get install libtool autoconf automake uuid-dev
    wget http://download.zeromq.org/zeromq-2.2.0.tar.gz
    tar -xvf zeromq-2.2.0.tar.gz
    cd zeromq-2.2.0
    ./configure
    make
    sudo make install
    sudo ldconfig

### Install [ØMQ Java binding] (http://www.zeromq.org/bindings:java)
    git clone https://github.com/zeromq/jzmq.git
    cd jzmq
    ./autogen.sh
    ./configure
    make
    sudo make install
    sudo ldconfig

### Install [ØMQ Python binding](http://www.zeromq.org/bindings:python)
    easy_install pyzmq


### Use the Eclipse Project

1. Open Eclipse SDK 
2. Import Project by File -> Import choose in General "Existing Projects into Workspace" than choose the RefereePkg folder
3. Right click -> Build Path -> Configure Build Path -> navigate to Libraries tab -> click on Add External JARs navigate to “/usr/local/share/java/zmq.jar”.
4. And from the same tab click on Add External Class Folder navigate to “jzmq/pre” and click OK.
5. Active the JRE and zmq.jar under "Java Build Path" -> "Order and Export"
6. Run the project. For Referee System drop the RefereePkg src right click on the RefereeSystem.java select Run As Java Application. For Robot System drop the RobotPkg src right click on the JavaClientGUI.java select Run As Java Application.
---------------------------------------------------------------------------------------------------------


Usage
------------
=======
# Usage
A .jar file of the server can be found [here](https://github.com/b-it-bots/RoboCupAtWorkRefereeBox/wiki/RoboCupAtWorkRefereeBox0_1.jar)

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

