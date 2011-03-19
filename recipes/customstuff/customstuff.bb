DESCRIPTION = "Custom Changes"
LICENSE = "unknown"

SRC_URI = "file://inshp.sh \
          "

S = "${WORKDIR}"

do_install() {
	install -d ${D}/etc/init.d
	install -m 0755 ${S}/inshp.sh ${D}/etc/init.d
	install -d ${D}/etc/rcS.d
	ln -s ../init.d/inshp.sh ${D}/etc/rcS.d/S75inshp.sh
}

FILES_${PN} = "etc/* \
	      "
PACKAGE_ARCH = "all"
