#include <zmq.hpp>
#include <string>
#include <iostream>

int main (int argc, char **argv)
{
	std::string referee_ip;
	
	if(argc != 3)
		return -1;
		
	std::cout << argv[1] << " " << argv[2] << std::endl;
	
	std::string conn_infos = "tcp://" + std::string(argv[1]) + ":" + std::string(argv[2]);
	
	std::cout << conn_infos << std::endl;
	
	//const char *p;
    zmq::context_t context (1);
    zmq::socket_t socket (context, ZMQ_REQ);
    
	//p = (char *) referee_ip;
	std::cout << "Connecting to the Referee…" << std::endl;
    socket.connect (conn_infos.c_str());
	
	zmq::message_t request (6);
	memcpy ((void *) request.data (), "Hello Referee Please send the Triplets", 5);
	socket.send (request);
	zmq::message_t reply(100);
	socket.recv (&reply);
	std::cout << reply.data() << std::endl;
	std::cout << "Received Triplet " << std::endl;
    return 0;
}
