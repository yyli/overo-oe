#!/bin/bash

CHECK_FIRST=`grep "auto wlan0" /etc/network/interfaces`
if [ "$CHECK_FIRST" == "" ]
then
	echo "initial boot"
	echo -e "auto wlan0\niface wlan0 inet dhcp" >> /etc/network/interfaces  
fi


DEVICE="wlan0"
ROUTER_NAME="hovercraft"
IP="192.168.0.121"

echo "Loading Drivers"
modprobe libertas_sdio

echo "Starting Wifi"
CHECK_LEASE=`ping 192.168.0.1 -w 3 | grep time`
while [ "$CHECK_LEASE" == "" ]; do
	echo Not connected - Trying to connect again CTRL-C to stop
	sleep 2
	iwlist $DEVICE scan > /dev/null 2>&1
	iwconfig $DEVICE mode managed essid $ROUTER_NAME
	ifconfig $DEVICE $IP up
	CHECK_LEASE=`ping 192.168.0.1 -w 3 | grep time`
done
nohup sh /etc/init.d/checkwifi.sh &
