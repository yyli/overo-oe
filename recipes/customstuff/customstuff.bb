DESCRIPTION = "Custom Changes"
LICENSE = "unknown"

SRC_URI = "file://hotplug \
           file://inshp.sh \
          "

S = "${WORKDIR}"

do_install() {
	install -d ${D}/${base_sbindir}
	install -m 0755 ${S}/hotplug ${D}/${base_sbindir}/
	install -d ${D}/etc/init.d
	install -m 0755 ${S}/inshp.sh ${D}/etc/init.d
	install -d ${D}/etc/rcS.d
	ln -s ../init.d/inshp.sh ${D}/etc/rcS.d/S75inshp.sh
}

FILES_${PN} = "${base_sbindir}/* \
	       etc/* \
	      "
PACKAGE_ARCH = "all"
