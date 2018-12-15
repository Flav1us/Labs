#!/bin/bash
echo "Start WPScan";
cd /home/"$USER"/Ark/wpscan/;
ruby wpscan.rb --url $1 y;
ruby wpscan.rb --url $1 --enumerate p y;
ruby wpscan.rb --url $1 --enumerate y;
ruby wpscan.rb -u $1 --wp-content-dir custom-content y;
ruby wpscan.rb --url $1 --debug-output 2 y;
echo "Finish WPScan";