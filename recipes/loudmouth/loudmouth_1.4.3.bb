DESCRIPTION = "Loudmouth is a lightweight and easy-to-use C library for programming with the Jabber protocol."
HOMEPAGE = "http://www.loudmouth-project.org/"
LICENSE = "LGPL"
DEPENDS = "glib-2.0 check openssl"
PR = "r2"

SRC_URI += "file://04-use-pkg-config-for-gnutls.patch" 

inherit gnomebase

EXTRA_OECONF = "--with-ssl=openssl"

SRC_URI[archive.md5sum] = "55339ca42494690c3942ee1465a96937"
SRC_URI[archive.sha256sum] = "95a93f5d009b71ea8193d994aa11f311bc330a3efe1b7cd74dc48f11c7f929e3"
