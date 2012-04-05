
Running TaskServer from RefereePkg:
1. Add the environment variable TASK_SERVER_CONFIG_DIR. Its value is the full path to the folder having the “config.txt” file.
Eg:-  TASK_SERVER_CONFIG_FILE= “/home/teena/serverconfig”
2. Run TaskServer 

Running TaskClient from RobotPkg:
1. Add the environment variable TASK_CLIENT_CONFIG_DIR. Its value is the full path to the folder having the “config.txt” file.
2. Provide the IP address and port number of the server as command line arguments. For the present implementation use the values 127.0.0.1 8000

Running multiple processes from Eclipse:
1. Run the processes in the usual way. In our case, run the server first. Once it starts listening for client connections, run the client code.
2. In order to view the consoles for the two processes, click on the icon on the right hand side of the console tab named “Display Selected Console”. Each click helps you to switch between the different consoles.
