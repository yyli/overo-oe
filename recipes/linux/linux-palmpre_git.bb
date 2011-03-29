require linux.inc

SECTION = "kernel"
DESCRIPTION = "Modified kernel for the Palm Pre based on the original source from Palm Inc."
KERNEL_IMAGETYPE = "uImage"

SRC_URI = " \
git://git.freesmartphone.org/linux-2.6.git;protocol=git;branch=palmpre/master \
file://defconfig \
"
DEPENDS += "kbd-native"

S = "${WORKDIR}/git/"

SRCREV = "2c70e5959c6ff34bb10a557a048f5afc32039ca6"
KV = "2.6.24"
PR="r4"
PV = "${KV}+gitr${SRCPV}"

# linux.inc overrides LOCAVERSION but we like to stay with the one used originally
do_compile_prepend() {
	sed -i -e '/CONFIG_LOCALVERSION=/d' ${S}/.config
	echo 'CONFIG_LOCALVERSION="-joplin-3430"' >>${S}/.config
}
