#!/bin/bash

cd /home/"$USER"/Ark/nikto/program/;
./nikto.pl -h $1 -p 80,88,443;
