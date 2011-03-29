DESCRIPTION = "An Internet printing system for Unix."
SECTION = "console/utils"
LICENSE = "GPL LGPL"
DEPENDS = "gnutls jpeg dbus dbus-glib libpng zlib fakeroot-native"

PR = "r8"

SRC_URI = "ftp://ftp.easysw.com/pub/cups/${PV}/cups-${PV}-source.tar.bz2 \
           file://0001-cups-desktop-fix-cups-desktop-file.patch \
  		   "

inherit autotools binconfig

EXTRA_OECONF = " \
                --enable-gnutls \
		--enable-dbus \
		--enable-browsing \
                --disable-openssl \
		--disable-tiff \
		--disable-ssl \
		--without-php \
		--without-perl \
		--without-python \
		--without-java \
               "


do_configure() {
    	export DSOFLAGS="${LDFLAGS}"
	gnu-configize
	libtoolize --force
	oe_runconf
}

do_compile () {
	sed -i s:STRIP:NOSTRIP: Makedefs
	sed -i s:serial:: backend/Makefile

	echo "all:"    >  man/Makefile
	echo "install:" >> man/Makefile

	oe_runmake "SSLLIBS=-lgnutls -L${STAGING_LIBDIR}" \
		   "LIBPNG=-lpng -lm -L${STAGING_LIBDIR}" \
		   "LIBJPEG=-ljpeg -L${STAGING_LIBDIR}" \
		   "LIBZ=-lz -L${STAGING_LIBDIR}" \
		   "-I."
}

fakeroot do_install () {
	oe_runmake "DSTROOT=${D}" install

   # This directory gets installed with perms 511, which makes packaging fail
   chmod 0711 "${D}/${localstatedir}/run/cups/certs"
}

python do_package_append() {
	# Change permissions back the way they were, they probably had a reason...
	pkgdest = bb.data.getVar('PKGDEST', d, 1)
	os.system('chmod 0511 %s/cups/var/run/cups/certs' % pkgdest)
}

SYSROOT_PREPROCESS_FUNCS += "cups_config_mangle"

cups_config_mangle() {
	# Undo mangle of cups_datadir and cups_serverbin
	sed -e 's:cups_datadir=.*:cups_datadir=${datadir}/cups:g' \
	    -e 's:cups_serverbin=.*:cups_serverbin=${libdir}/cups:g' \
	    -i  ${SYSROOT_DESTDIR}${STAGING_BINDIR_CROSS}/cups-config
}

PACKAGES =+ "${PN}-lib ${PN}-libimage"

FILES_${PN}-lib = "${libdir}/libcups.so.*"

FILES_${PN}-libimage = "${libdir}/libcupsimage.so.*"

FILES_${PN}-dbg += "${libdir}/cups/backend/.debug \
                    ${libdir}/cups/cgi-bin/.debug \
		    ${libdir}/cups/filter/.debug \
		    ${libdir}/cups/monitor/.debug \
		    ${libdir}/cups/notifier/.debug \
		    ${libdir}/cups/daemon/.debug \
		    "
#package the html for the webgui inside the main packages (~1MB uncompressed)

FILES_${PN} += "${datadir}/doc/cups/images \
		${datadir}/doc/cups/*html \
		${datadir}/doc/cups/*.css \
                ${datadir}/icons/ \
	       "

SRC_URI[md5sum] = "d410658468384b5ba5d04a808f6157fe"
SRC_URI[sha256sum] = "b4ff8e934da7db32d5654360ea9068faa0ed5a00fde02161ae53c2052510d00f"
