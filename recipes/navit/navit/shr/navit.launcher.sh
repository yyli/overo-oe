#! /bin/sh
#
# Copyright Matthias Hentges <devel@hentges.net> (c) 2008
# License: GPL (see http://www.gnu.org/licenses/gpl.txt for a copy of the license)
#
# Filename: navit.launcher
# Date: 20080105 (YMD)
#
#################################################################################
#
# 20080105 - v0.0.1	- Initial release
# 20090818 -            - Zoff <zoff@zoff.cc> addons and fixes
# 20091025 -            - Zoff check if navit already running
# 20091122 -            - Zoff use fsoraw, use correct LANG setting
#
#
#################################################################################

# On devices with low memory (< 512Mb?) Navit will segfault on start.
# This can be worked around by doing
# "echo 1 > /proc/sys/vm/overcommit_memory"

# if we have procps installed check if navit already running:
if test -e /usr/bin/pgrep; then
  pgrep '^navit$'
  not_running=$?
else
  # we assume it's not running
  not_running=1
fi

if [ $not_running = 0 ]; then
        echo "navit already running !!"
else
        echo "ok starting navit ..."

        # HINT: to get correct LANG setting
        . /etc/profile

        # HINT: we need that for streetname search
        export LC_ALL=''
        unset LC_ALL

        # HINT: that should be set in illume, but atm its not
        # export LANG=de_AT.utf8

	if test "`cat /proc/meminfo | grep ^MemTotal | awk '{print $2}'`" -lt "500000"
	then
	        if test "$USER" = "root"
	        then
	                echo "Enabling low-mem workaround..."
	                OLD=`cat /proc/sys/vm/overcommit_memory`
	                echo 1 > /proc/sys/vm/overcommit_memory
	                navit $*
	                echo ${OLD} > /proc/sys/vm/overcommit_memory
	                exit
	        else
	                echo "I need root-privs to enable the low-mem workaround!"
	        fi
	fi
        navit $*
fi
