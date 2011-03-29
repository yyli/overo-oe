DESCRIPTION = "GNU roff"
SECTION = "base"
LICENSE = "GPL"
PR = "r3"

SRC_URI = "${GNU_MIRROR}/groff/groff-${PV}.tar.gz"

SRC_URI[md5sum] = "48fa768dd6fdeb7968041dd5ae8e2b02"
SRC_URI[sha256sum] = "b645878135cb620c6c417c5601bfe96172245af12045540d7344938b4c2cd805"

DEPENDS = "groff-native"
DEPENDS_virtclass-native = ""

inherit autotools

PARALLEL_MAKE = ""

do_configure_prepend(){
	if [ "${BUILD_SYS}" != "${HOST_SYS}" ]; then
		sed -i \
		    -e '/^GROFFBIN=/s:=.*:=${STAGING_BINDIR_NATIVE}/groff:' \
		    -e '/^TROFFBIN=/s:=.*:=${STAGING_BINDIR_NATIVE}/troff:' \
		    -e '/^GROFF_BIN_PATH=/s:=.*:=${STAGING_BINDIR_NATIVE}:' \
		    -e '/^GROFF_BIN_DIR=/s:=.*:=${STAGING_BINDIR_NATIVE}:' \
		    ${S}/contrib/*/Makefile.sub \
		    ${S}/doc/Makefile.in \
		    ${S}/doc/Makefile.sub
	fi
}

BBCLASSEXTEND = "native"
