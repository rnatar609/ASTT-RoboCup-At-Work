#include <zmq.hpp>
#include <string>
#include <iostream>

std::string obtainTaskSpecFromServer(std::string server_ip, std::string server_port, std::string team_name) {
	
	std::string conn_infos = "tcp://" + server_ip + ":" + server_port;
	std::cout << conn_infos << std::endl;
	
	zmq::context_t context (1);
    zmq::socket_t socket (context, ZMQ_REQ);
    
    std::cout << "Connecting to the Referee…" << std::endl;
    socket.connect (conn_infos.c_str());
    
    zmq::message_t request (6);
	memcpy ((void *) request.data (), "Hello Referee Please send the Triplets", 5);
	socket.send (request);
	zmq::message_t reply(100);
	socket.recv (&reply);
	std::string returnstring((char*)reply.data());
	std::cout << returnstring << std::endl;
	std::cout << "Received Triplet " << std::endl;

	return returnstring;
}
