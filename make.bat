@echo off

IF [%1] NEQ [] (
	CALL :%1
)

IF [%2] NEQ [] (
	CALL :%2
)

IF [%3] NEQ [] (
	CALL :%3
)

IF [%4] NEQ [] (
	CALL :%4
)

IF [%5] NEQ [] (
	CALL :%5
)

echo FIN
GOTO :EOF

:MyHTTPServer
echo.
echo Compilando MyHTTPServer
CALL :Compile javac Common/SocketHandling.java MyHTTPServer/HTTPHandling.java MyHTTPServer/ServerThread.java MyHTTPServer/ServerConcurrent.java MyHTTPServer/Main.java MyHTTPServer/StaticRecurses.java MyHTTPServer/DynamicRecurses.java
GOTO :EOF

:Controller
echo.
echo Compilando Controller
CALL :Compile javac Common/RMIStationInterface.java Common/SocketHandling.java Controller/ControllerThread.java Controller/ControllerConcurrent.java Controller/Main.java
GOTO:EOF

:RMIStation
echo.
echo Compilando RMIStation
CALL :Compile javac Common/RegistratorInterface.java Common/RMIStationInterface.java RMIStation/RMIStation.java RMIStation/Main.java
GOTO:EOF

:Registrator
echo.
echo Compilando Registrator
CALL :Compile javac Common/RegistratorInterface.java Common/RMIStationInterface.java Registrator/Registrator.java Registrator/Main.java
GOTO:EOF

:Clean
echo.
echo Limpiando
FOR /R %%A IN (*.class) DO del "%%A"
echo.
GOTO:EOF

:Compile
IF [%1] NEQ [] (
	echo.
	echo %*
	%*
	echo.
)
GOTO :EOF