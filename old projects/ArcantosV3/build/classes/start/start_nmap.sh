#!/bin/bash

echo "Start Nmap";
#nmap -T4 -A -v $1 /home/"$USER"/Desktop/Result/$1_Nmap.txt; 
nmap -T4 -A -v $1;
echo "Finish Nmap";