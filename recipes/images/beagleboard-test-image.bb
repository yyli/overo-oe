# this image was created for use with the BeagleBoard for diagnostics
# for creating a small ramdisk image

require minimal-image.bb

IMAGE_INSTALL += " \
  dosfstools \
  e2fsprogs \
  e2fsprogs-mke2fs \
  mtd-utils \
  alsa-utils \
  alsa-utils-aplay \
  mplayer \
  memtester \
  evtest \
  i2c-tools \
  cpufrequtils \
  angstrom-uboot-scripts \
  beagleboard-test-scripts \
  nano \
  cpuburn-neon \
  kernel-module-mt9t112 \
  u-boot-mkimage \
  sox \
  devmem2 \
"

export IMAGE_BASENAME = "beagleboard-test-image"

EXTRA_IMAGEDEPENDS += "x-load u-boot virtual/kernel"
IMAGE_FSTYPES += "ext2.gz"