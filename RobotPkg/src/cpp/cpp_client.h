#include <zmq.hpp>
#include <string>
#include <iostream>


std::string obtainTaskSpecFromServer(std::string server_ip, std::string server_port, std::string team_name) {
	
	std::string conn_infos = "tcp://" + server_ip + ":" + server_port;
	std::cout << conn_infos << std::endl;
	
	zmq::context_t context (1);
	zmq::socket_t socket (context, ZMQ_REQ);
	
	std::cout << "Connecting to the referee server…" << std::endl;
	socket.connect (conn_infos.c_str());
	
	zmq::message_t* send_team_name = new zmq::message_t(team_name.size());
	memcpy ((void *) send_team_name->data (), team_name.c_str(), team_name.size());
	socket.send (*send_team_name);
	std::cout << "Sent team name to server... " << std::endl;
	
	zmq::message_t reply;
	socket.recv (&reply);
	std::string returnstring = std::string(static_cast<char*>(reply.data()), reply.size());
	std::cout << returnstring << std::endl;
	std::cout << "Received taskSpecification: " << returnstring << std::endl;
	
	std::string ack_msg = "ACK";
	zmq::message_t* send_ack = new zmq::message_t(ack_msg.size());
	memcpy ((void *) send_ack->data (), ack_msg.c_str(), ack_msg.size());
	socket.send (*send_ack);
	
	socket.close();	

	delete send_ack;
	delete send_team_name;
	return returnstring;
}
