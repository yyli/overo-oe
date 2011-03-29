DESCRIPTION = " Enlightenment Web Browser"
LICENSE = "GPL"
DEPENDS = "evas ecore edje elementary webkit-efl"
PV = "0.2.0.0+svnr${SRCPV}"
SRCREV = "${EFL_SRCREV}"

PR = "r2"

inherit e
SRC_URI = "${E_SVN}/trunk;module=${SRCNAME};proto=http;scmdata=keep"
S = "${WORKDIR}/${SRCNAME}"

SRC_URI += "file://eve-theme-for-smaller-screens.patch"

EXTRA_OECONF = "\
  --with-edje-cc=${STAGING_BINDIR_NATIVE}/edje_cc \
"

FILES_${PN} += "\
        ${datadir}/icons/eve.png \
"
