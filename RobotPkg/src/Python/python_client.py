#
#   Python Client
#   Needs the tcp//... address of the server as argument 
#	(e.g. $ pyton2.7 hwclient.py tcp://localhost.5555
#   Sends the robot name ( in our case b-it-bots) 
#   and waits for reply from the server
#	
import zmq
import sys

def obtainTaskSpecFromServer(server_ip, server_port, team_name):
	context = zmq.Context()

	connection_address = "tcp://" + server_ip + ":" + server_port
	print "Start connection to " + connection_address
	# Socket to talk to server
	socket = context.socket(zmq.REQ)
	
	print "Connecting to server..."
	socket.connect (connection_address)

	print "Sent team name to server..."
	socket.send (team_name)
    
	#  Get the reply.
	message = socket.recv()
	print "Received taskSpecification: ", message

	socket.send ("ACK")

if __name__ == "__main__":
	obtainTaskSpecFromServer("127.0.1.1","11111","python_client")


