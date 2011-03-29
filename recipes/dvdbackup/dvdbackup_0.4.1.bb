DESCRIPTION = "Tool to rip DVDs from the command line"
SECTION = "console/multimedia"
PRIORITY = "optional"
LICENSE = "GPLv3+"
DEPENDS = "libdvdread"

SRC_URI = "${SOURCEFORGE_MIRROR}/${PN}/${P}.tar.bz2"
SRC_URI[md5sum] = "e4b35ba716852361f35cecafff44f37c"
SRC_URI[sha256sum] = "8194fe84950f3886551e75357d56c0fff3bd5fbd47d3b661c75d57d60cd3c6be"

inherit autotools
