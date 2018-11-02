@echo off
echo rmiregistry -J-Djava.security.policy=Common/registrar.policy %*
rmiregistry -J-Djava.security.policy=Common/registrar.policy %*