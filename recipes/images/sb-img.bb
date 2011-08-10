# Based on the Angstrom minimalist image
# The image for the SolarBubbles Team
# Yiying Li @ University of Michigan 2011 yyli@umich.edu
ANGSTROM_EXTRA_INSTALL ?= "\
	    kernel-module-g-serial \
	    kernel-module-g-ether \
	    kernel-module-libertas \
	    kernel-module-libertas-sdio \
	    kernel-module-lib80211 \
	    libertas-sd-firmware \
	    wireless-tools \
	    ntp \
		ntpdate \
	    devmem2 \
        screen \
		openssh-ssh \
        openssh-sftp \
        openssh-scp \
        tcpdump \
        linux-hotplug \
	    ncurses-terminfo \
		customstuff \
	    "

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp;"

IMAGE_INSTALL = "task-boot \
            util-linux-ng-mount util-linux-ng-umount \
            ${DISTRO_SSH_DAEMON} \
            ${ANGSTROM_EXTRA_INSTALL} \
            angstrom-version \
	   "

export IMAGE_BASENAME = "sb-img"
IMAGE_LINGUAS = ""

inherit image

