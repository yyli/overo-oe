#!/bin/sh

CHECK_FIRST=`grep "auto wlan0" /etc/network/interfaces`
if [ "$CHECK_FIRST" == "" ]
then
	echo "initial boot"
	echo -e "auto wlan1\niface wlan1 inet dhcp" >> /etc/network/interfaces  
fi


DEVICE="wlan0"
ROUTER_NAME="NETGEAR"
IP="192.168.1.121"

echo "Loading Drivers"
modprobe libertas_sdio

iwconfig $DEVICE mode managed essid $ROUTER_NAME
echo "Starting Wifi"
TEMP_LEASE=`ping 192.168.1.1 -w 1 2>&1 | grep unreachable`
CHECK_LEASE=`ping 192.168.1.1 -w 1 2>&1 | grep "time\="`
while [ "$CHECK_LEASE" == "" -o "$TEMP_LEASE" != "" ]; do
	echo Not connected - Trying to connect again CTRL-C to stop
	sleep 2
	iwlist $DEVICE scan > /dev/null 2>&1
	iwconfig $DEVICE mode managed essid $ROUTER_NAME
	#dhclient $DEVICE
	ifconfig $DEVICE $IP up
	CHECK_LEASE=`ping 192.168.1.1 -w 1 2>&1 | grep "time\="`
	TEMP_LEASE=`ping 192.168.1.1 -w 1 2>&1 | grep unreachable`
done
nohup sh /etc/init.d/checkwifi.sh > /dev/null 2>&1 &
