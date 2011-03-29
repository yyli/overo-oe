require tzdata.inc

# Note that elsie.nci.nih.gov removes old archives after a new one is
# released. So if this doesn't build for you because of missing source file
# just bump it to the latest available version, removing the old one

PR = "${INC_PR}.0"

SRC_URI[md5sum] = "9eaf3ca354c42a32bd28e623539bf0e0"
SRC_URI[sha256sum] = "ff45f5ddc2ec925249626d00d7bc2ffff587e0956a1d8245517a023bf27e4cc9"

