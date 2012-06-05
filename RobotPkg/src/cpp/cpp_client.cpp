#include <zmq.hpp>
#include <string>
#include <iostream>
#include <fstream>
#include <cstring>

const int MAX_CHARS_PER_LINE = 50;
const int MAX_TOKENS_PER_LINE = 2;
const char* const DELIMITER = " ";

int main ()
{
	std::string referee_ip;
	std::string referee_port;
	std::string conn_infos;
	const char* team_name = "bit-bots";
	
	// create a file-reading object
	using std::ifstream;
	ifstream fin;
	fin.open("config.txt");
	if (!fin.good()) 
		std::cout << "Configfile not found" << std::endl;
		return 1;
	
	while (!fin.eof())
	{
		// read an entire line into memory
		char buf[MAX_CHARS_PER_LINE];
		fin.getline(buf, MAX_CHARS_PER_LINE);
		
		// parse the line into blank-delimited tokens
		int n = 0;
		
		// array to store memory addresses of the tokens in buf
		const char* token[MAX_TOKENS_PER_LINE] = {0};
		
		// parse the line
		token[0] = strtok(buf, DELIMITER); // first token
		if (token[0]) // zero if line is blank
		{
			for (n = 1; n < MAX_TOKENS_PER_LINE; n++)
			{
				token[n] = strtok(0, DELIMITER);
				if (!token[n]) break;
			}
		}
		
		referee_ip = token[1];
		std::cout << "RefereeIP: " << referee_ip << std::endl;
		referee_port = token[3];
		std::cout << "RefereePort: " << referee_port << std::endl;
	}
	
	conn_infos = "tcp://" + referee_ip + ":" + referee_port;
	
	std::cout << conn_infos << std::endl;
	
	//const char *p;
    zmq::context_t context (1);
    zmq::socket_t socket (context, ZMQ_REQ);
    
	//p = (char *) referee_ip;
	std::cout << "Connecting to the Referee…" << std::endl;
    socket.connect (conn_infos.c_str());
	
	zmq::message_t request (6);
	memcpy ((void *) request.data (), team_name, 5);
	socket.send (request);
	std::cout << "Sent Teamname to Referee" << std::endl;
	zmq::message_t reply(100);
	socket.recv (&reply);
	std::cout << reply.data() << std::endl;
	std::cout << "Received Triplet " << std::endl;
    return 0;
}