DESCRIPTION = "The OpenMoko Today2 vfolder files"
SECTION = "openmoko/misc"
PV = "0.1.0+svn${SVNREV}"
PR = "r1"

inherit openmoko2

FILES_${PN} += "${datadir}"