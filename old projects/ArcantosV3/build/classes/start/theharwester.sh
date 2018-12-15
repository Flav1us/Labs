#!/bin/bash
echo "Start Information Gethering";
cd /home/"$USER"/Ark/theHarvester/;
TARGET="$1";
echo "The Harvester";
./theHarvester.py  -d $TARGET -b google;
./theHarvester.py  -d $TARGET -b bing;
./theHarvester.py  -d $TARGET -b linkedin;
./theHarvester.py -d $TARGET -b pgp;

