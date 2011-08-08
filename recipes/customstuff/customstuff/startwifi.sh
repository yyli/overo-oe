#!/bin/bash

CHECK_FIRST=`grep "auto wlan0" /etc/network/interfaces`
if [ "$CHECK_FIRST" == "" ]
then
	echo "initial boot"
	echo -e "auto wlan0\niface wlan0 inet dhcp" >> /etc/network/interfaces	
fi

echo "Loading Drivers"
modprobe libertas_sdio
ifconfig wlan0 down > /dev/null 2>&1
ifconfig wlan0 up > /dev/null 2>&1

echo "Starting Wifi"
iwlist scan > /dev/null 2>&1
CHECK_LEASE=`/etc/init.d/networking restart | grep "Lease of"`    
while [ "$CHECK_LEASE" == "" ]; do
        CHECK_LEASE=`/etc/init.d/networking restart | grep "Lease of"`
        echo failed trying again CTRL-C to stop
done
echo $CHECK_LEASE
