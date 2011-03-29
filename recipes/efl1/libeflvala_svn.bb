DESCRIPTION = "Vala meets the Enlightenment Foundation Libraries"
AUTHOR = "Michael 'Mickey' Lauer <mlauer@vanille-media.de>"
LICENSE = "LGPL"
SECTION = "devel"
DEPENDS = "vala-native glib-2.0 dbus dbus-glib eina eet evas ecore edje elementary"
PV = "2011.01.13.1+svnr${SRCPV}"
PE = "1"
SRCREV = "${EFL_SRCREV}"
SRCNAME = "vala"

inherit e-base autotools pkgconfig vala

SRC_URI = "${E_SVN}/trunk/BINDINGS;module=${SRCNAME};proto=http"
S = "${WORKDIR}/${SRCNAME}"

PACKAGES =+ "${PN}-examples"
FILES_${PN}-examples = "${datadir}/libeflvala ${bindir}/*"
