#!/usr/bin/python

from refereeBoxClient import *


if __name__ == "__main__":
	print obtainTaskSpecFromServer("127.0.1.1", "11111", "b-it-bots")
