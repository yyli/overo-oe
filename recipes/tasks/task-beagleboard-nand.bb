DESCRIPTION = "Task for putting an X11 desktop in NAND"

inherit task 

ECONFIG ?= "places e-wm-config-angstrom e-wm-config-angstrom-widescreen e-wm-config-default"
EMENU ?= "e-wm-menu"

RDEPENDS_${PN} = "\
    beagleboard-test-scripts \
    cron ntpdate \
    task-base-extended \
    angstrom-x11-base-depends \
    angstrom-gpe-task-base \
    angstrom-gpe-task-settings \
    angstrom-zeroconf-audio \
    angstrom-led-config \ 
    gpe-scap \
    mime-support e-wm ${ECONFIG} ${EMENU} \
    xterm \
    midori \
    hicolor-icon-theme gnome-icon-theme \
    nmap iperf \
    powertop oprofile \
    mplayer \
	synergy \
	x11vnc angstrom-x11vnc-xinit \
	angstrom-gnome-icon-theme-enable \
	openssh-scp openssh-ssh \
	picodlp-control \
	network-manager-applet \
	gnome-bluetooth \
"

# Install all kernel modules
RRECOMMENDS_${PN} += " \
	kernel-modules \
    rt73-firmware \
	zd1211-firmware \
"

PACKAGE_ARCH = "${MACHINE_ARCH}"
RRECOMMENDS_${PN}_append_armv7a = " \
	omapfbplay"

