# console image for omap3

inherit image

DEPENDS = "task-base"

IMAGE_EXTRA_INSTALL ?= ""

IMAGE_INSTALL += " \
  task-base-extended \
  task-proper-tools \
  alsa-utils \
  alsa-utils-alsactl \
  alsa-utils-alsamixer \
  alsa-utils-aplay \
  bash \
  bzip2 \
  dosfstools \
  e2fsprogs-mke2fs \
  fbgrab \
  fbset \
  fbset-modes \
  ksymoops \
  mkfs-jffs2 \
  mtd-utils \
  openssh-scp \
  openssh-ssh \
  socat \
  strace \
  ${IMAGE_EXTRA_INSTALL} \
  libertas-sd-firmware \
  rt73-firmware \
  zd1211-firmware \
 "

IMAGE_PREPROCESS_COMMAND = "create_etc_timestamp"

#ROOTFS_POSTPROCESS_COMMAND += '${@base_conditional("DISTRO_TYPE", "release", "zap_root_password; ", "",d)}'

