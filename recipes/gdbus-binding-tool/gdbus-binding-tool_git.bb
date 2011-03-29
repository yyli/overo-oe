DESCRIPTION = "gdbus-binding-tool is used to generate C code for interacting with remote objects using D-Bus."
DEPENDS = "glib-2.0 gdbus-binding-tool-native"
DEPENDS_virtclass-native = "glib-2.0-native"
RDEPENDS_${PN} = "glib-2.0-utils"

PR = "r2"

inherit autotools pkgconfig

SRC_URI = "git://anongit.freedesktop.org/~david/${BPN};protocol=git;branch=master"
SRCREV = "229fd9adbb6bd9d824b38a3bd092229016540f41"
PV = "0.1+gitr${SRCPV}"
S = "${WORKDIR}/git"

do_configure() {
  # missing ${topdir}/gtk-doc.make and --disable-gtk-doc* is not enough
  sed -i '/^doc\/Makefile/d' ${S}/configure.ac
  sed -i 's/SUBDIRS = src doc/SUBDIRS = src/g' ${S}/Makefile.am

  # cannot execute target binary, so use staged native
  sed -i "s#\$(top_builddir)/src/gdbus-codegen#${STAGING_BINDIR_NATIVE}/gdbus-codegen#g" ${S}/src/Makefile.am

  autotools_do_configure
}
do_configure_virtclass-native() {
  # missing ${topdir}/gtk-doc.make and --disable-gtk-doc* is not enough
  sed -i '/^doc\/Makefile/d' ${S}/configure.ac
  sed -i 's/SUBDIRS = src doc/SUBDIRS = src/g' ${S}/Makefile.am

  autotools_do_configure
}

BBCLASSEXTEND = "native"
