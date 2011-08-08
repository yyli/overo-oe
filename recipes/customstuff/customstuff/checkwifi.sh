#!/bin/bash

DEVICE="wlan0"
ROUTER_NAME="hovercraft"
IP="192.168.0.121"

while [ 1 == 1 ]; do 
	echo check
	sleep 60
	CHECK_LEASE=`ping 192.168.0.1 -w 1 | grep time`
	while [ "$CHECK_LEASE" == "" ]; do
		echo Not connected - Trying to connect again CTRL-C to stop
		sleep 2
		iwlist $DEVICE scan > /dev/null 2>&1
		iwconfig $DEVICE mode managed essid $ROUTER_NAME
		ifconfig $DEVICE $IP up
		CHECK_LEASE=`ping 192.168.0.1 -w 3 | grep time`
	done
done
