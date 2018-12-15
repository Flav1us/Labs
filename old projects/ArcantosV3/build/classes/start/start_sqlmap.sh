#!/bin/bash

cd /home/"$USER"/Ark/sqlmap;
#python sqlmap.py -v 2 --url=http://$1/ --user-agent=SQLMAP --delay=1 --timeout=15 --retries=2 --keep-alive --threads=5 --eta --batch --dbms=MySQL --os=Linux --level=5 --risk=3 --banner --is-dba --dbs --tables --technique=BEUST -s /home/"$USER"/Desktop/Result/scan_report.txt --flush-session -t /home/"$USER"/Desktop/Result/scan_trace.txt --fresh-queries > /home/"$USER"/Desktop/Result/$1_SQLmap.txt;
#rm /home/"$USER"/Desktop/Result/scan_report.txt;
#rm /home/"$USER"/Desktop/Result/scan_out.txt;
python sqlmap.py -v 2 --url=http://$1/ --user-agent=SQLMAP --delay=1 --timeout=15 --retries=2 --keep-alive --threads=5 --eta --batch --dbms=MySQL --os=Linux --level=5 --risk=3 --banner --is-dba --dbs --tables --technique=BEUST;