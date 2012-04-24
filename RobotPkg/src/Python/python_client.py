#
#   Python Client
#   Needs the tcp//... address of the server as argument 
#	(e.g. $ pyton2.7 hwclient.py tcp://localhost.5555
#   Sends the robot name ( in our case b-it-bots) 
#   and waits for reply from the server
#	
import zmq
import sys

context = zmq.Context()
for arg in sys.argv:
	print arg
#  Socket to talk to server
print "Connecting to server..."
socket = context.socket(zmq.REQ)
#socket.connect ("tcp://localhost:5555")
socket.connect (arg)

#for request in range (1,10):
print "Sending request ..."
socket.send ("b-it-bots")
    
#  Get the reply.
message = socket.recv()
print "Received message: ", message

socket.send ("Triplet received")



