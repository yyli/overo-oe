DESCRIPTION = "gsm.07.10 muxer userspace daemon"
AUTHOR = "M. Dietrich"
SECTION = "console/network"
DEPENDS = "intltool-native dbus"
LICENSE = "GPL"
PV = "0.0+svnr${SRCREV}"
PR = "r1"

SRC_URI = "svn://projects.linuxtogo.org/svn/smartphones/trunk/software;module=gsm0710muxd"
S = "${WORKDIR}/gsm0710muxd"

inherit autotools update-rc.d

INITSCRIPT_NAME = "gsm0710muxd"
INITSCRIPT_PARAMS = "defaults 35"

do_install_append() {
	chmod a+rx ${D}${sysconfdir}/init.d/gsm0710muxd
}
