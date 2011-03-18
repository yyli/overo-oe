# Based on the Angstrom minimalist image
# Gives a small image with ssh access, a few tools and some
# kernel modules I use regularly.
# You need to have the drivers built as modules or OE won't
# find them for the rootfs install. The 2.6.34 recipe in
# my minimal branch has the correct defconfig.

ANGSTROM_EXTRA_INSTALL ?= "\
            kernel-module-g-ether \
            kernel-module-g-serial \
            kernel-module-omap2-mcspi \
            kernel-module-usbserial \
            kernel-module-ftdi-sio \
            devmem2 \
            openssh-ssh \
            openssh-scp \
            tcpdump \
            python \
            python-pyserial \
            "

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

IMAGE_INSTALL = "task-boot \
            util-linux-ng-mount util-linux-ng-umount \
            ${DISTRO_SSH_DAEMON} \
            ${ANGSTROM_EXTRA_INSTALL} \
            angstrom-version \
	   "

export IMAGE_BASENAME = "serial-image"
IMAGE_LINGUAS = ""

inherit image

