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

echo FIN
GOTO :EOF

:MyHTTPServer
echo.
echo Compilando MyHTTPServer
CALL :Compile javac MyHTTPServer/SocketHandling.java MyHTTPServer/ErrorHandling.java MyHTTPServer/ServerThread.java MyHTTPServer/ServerConcurrent.java MyHTTPServer/Server.java
GOTO :EOF

:Compile
IF [%1] NEQ [] (
	echo.
	echo %*
	%*
	echo.
)
GOTO :EOF