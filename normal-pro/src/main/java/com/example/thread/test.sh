#!bin/sh

A='ps -C nginx --no-header |wc -l'

if [ $A -eq 0 ]
   then
      service keepalived stop
fi
