DESCRIPTION = "Custom Changes"
LICENSE = "unknown"
RDEPENDS="sed"

SRC_URI = "file://inshp.sh \
           file://disprintk.sh \
	   file://startwifi.sh \
	   file://checkwifi.sh \
	"

S = "${WORKDIR}"

do_install() {
	install -d ${D}/etc/init.d
	install -m 0755 ${S}/inshp.sh ${D}/etc/init.d
	install -m 0755	${S}/disprintk.sh ${D}/etc/init.d
	install -m 0755 ${S}/startwifi.sh ${D}/etc/init.d
	install -m 0755 ${S}/checkwifi.sh ${D}/etc/init.d
	install -d ${D}/etc/rcS.d
	ln -s ../init.d/inshp.sh ${D}/etc/rcS.d/S75inshp.sh
	ln -s ../init.d/disprintk.sh ${D}/etc/rcS.d/S76disprintk.sh
	ln -s ../init.d/startwifi.sh ${D}/etc/rcS.d/S85startwifi.sh
}

FILES_${PN} = "etc/* \
	      "
PACKAGE_ARCH = "all"
