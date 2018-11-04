#!/bin/bash

echo java -Djava.security.policy=Common/registrar.policy RMIStation.Main $*
java -Djava.security.policy=Common/registrar.policy RMIStation.Main $*
