#!/bin/bash

cd /home/"$USER"/Ark/skipfish/;
./skipfish -MEU -o /home/"$USER"/Desktop/Result/$1_Skiphis.txt http://$1;
#./skipfish -MEU http://$1;
