DESCRIPTION = "The OpenGL Extension Wrangler Library (GLEW) is a cross-platform open-source C/C++ extension loading library."
HOMEPAGE = "http://glew.sourceforge.net/"
SECTION = "x11"
LICENSE = "MIT"

DEPENDS = "virtual/libx11 virtual/libgl libxext libxi libxmu"

PR = "r0"

SRC_URI = "${SOURCEFORGE_MIRROR}/project/glew/glew/${PV}/glew-${PV}.tgz \
           file://autotools.patch \
          "
SRC_URI[md5sum] = "f913ce9dbde4cd250b932731b3534ded"
SRC_URI[sha256sum] = "86bd36a163640d6027ec6be5fdd8a6e3b90f02dcf55f95c3c2429ebb58be3107"

S = "${WORKDIR}/glew-${PV}"

inherit autotools lib_package
