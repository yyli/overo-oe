DESCRIPTION = "efenniht elementary theme - Efenniht was devised to be clean and neutral. Its name (which means equinox) comes from the chromatic duality that was decided at the very beginning, with a dark theme (black and orange) and a bright one (white and blue) to be developed so that more people feel comfortable using it. Efenniht uses few animations, discrete contrasts between shades of gray and fine lines (colored) that outline the selected elements."
SECTION = "e/utils"
HOMEPAGE = "http://trac.enlightenment.org/e/wiki/Themes#Efenniht"
DEPENDS = "edje-native"
RSUGGESTS_${PN} = "e-wm-theme-illume-efenniht"
PACKAGE_ARCH = "all"
LICENSE = "MIT/BSD"

SRCREV_pn-elementary-theme-efenniht_THM_REV ?= "${EFL_SRCREV_1.0.0}"
SRCREV_pn-elementary-theme-efenniht_ELM_REV ?= "${EFL_SRCREV_1.0.0}"

SRCREV_FORMAT = "THM_REV"
PV = "0.0+svnr${SRCPV}"
PACKAGE_ARCH = "all"

inherit e-base

SRCNAME = "efenniht"
SRC_URI = "${E_SVN}/trunk/THEMES;module=${SRCNAME};proto=http;name=THM_REV"
SRC_URI += "${E_SVN}/trunk/elementary/data;module=themes;proto=http;name=ELM_REV"
S = "${WORKDIR}/${SRCNAME}"

do_compile() {
	${STAGING_BINDIR_NATIVE}/edje_cc -id ${S}/images -id ${WORKDIR}/themes/ ${S}/elm-efenniht.edc -o ${S}/elm-efenniht.edj
}

do_install() {
        install -d ${D}${datadir}/elementary/themes/
        install -m 0644 ${S}/elm-efenniht.edj ${D}${datadir}/elementary/themes/
}

FILES_${PN} = "${datadir}/elementary/themes/"
