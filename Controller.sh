#!/bin/bash

echo java -Djava.security.policy=Common/registrar.policy Controller.Main $*
java -Djava.security.policy=Common/registrar.policy Controller.Main $*
