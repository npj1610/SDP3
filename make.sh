#!/bin/bash

function MyHTTPServer {
	echo
	echo Compilando MyHTTPServer
	Compile javac Common/SocketHandling.java MyHTTPServer/HTTPHandling.java MyHTTPServer/ServerThread.java MyHTTPServer/ServerConcurrent.java MyHTTPServer/Main.java MyHTTPServer/StaticRecurses.java MyHTTPServer/DynamicRecurses.java
}

function Controller {
	echo
	echo Compilando Controller
	Compile javac Common/RMIStationInterface.java Common/SocketHandling.java Controller/ControllerThread.java Controller/ControllerConcurrent.java Controller/Main.java
}

function RMIStation {
	echo
	echo Compilando RMIStation
	Compile javac Common/RegistratorInterface.java Common/RMIStationInterface.java RMIStation/RMIStation.java RMIStation/Main.java
}

function Registrator {
	echo
	echo Compilando Registrator
	Compile javac Common/RegistratorInterface.java Common/RMIStationInterface.java Registrator/Registrator.java Registrator/Main.java
}

function Clean {
	echo
	echo Limpiando
	find . -name "*.class" -type f
	find . -name "*.class" -type f -delete
	echo
}

function Compile {
	echo
	echo $*
	$*
	echo
}

if ! [ -z $1 ]; then
	$1
fi

if ! [ -z $2 ]; then
	$2
fi

if ! [ -z $3 ]; then
	$3
fi

if ! [ -z $4 ]; then
	$4
fi

if ! [ -z $5 ]; then
	$5
fi
