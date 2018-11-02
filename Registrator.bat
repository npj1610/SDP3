@echo off
echo java -Djava.security.policy=Common/registrar.policy Registrator.Main %*
java -Djava.security.policy=Common/registrar.policy Registrator.Main %*