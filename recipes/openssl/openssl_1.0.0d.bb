inherit pkgconfig

require openssl.inc

# For target side versions of openssl enable support for OCF Linux driver
# if they are available.
DEPENDS += "ocf-linux"
CFLAG += "-DHAVE_CRYPTODEV -DUSE_CRYPTODEV_DIGESTS"

SRC_URI[src.md5sum] = "40b6ea380cc8a5bf9734c2f8bf7e701e"
SRC_URI[src.sha256sum] = "92511d1f0caaa298dba250426f8e7d5d00b271847886d1adc62422778d6320db"

PR = "${INC_PR}.0"

DEFAULT_PREFERENCE = "-1"

export DIRS = "crypto ssl apps engines"
export OE_LDFLAGS="${LDFLAGS}"

SRC_URI += "file://configure-targets.patch \
            file://shared-libs.patch \
            file://debian.patch \
            file://oe-ldflags.patch \
	    file://libdeps-first.patch \
	    file://engines-install-in-libdir-ssl.patch \
	   "

PARALLEL_MAKE = ""

PACKAGES += " \
	${PN}-engines \
	${PN}-engines-dbg \
	"

FILES_${PN}-engines = "${libdir}/ssl/engines/*.so"
FILES_${PN}-engines-dbg = "${libdir}/ssl/engines/.debug"
