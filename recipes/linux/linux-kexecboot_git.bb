require linux-kexecboot.inc

KERNEL_RELEASE = "2.6.38-rc7"
OLD_KERNEL_RELEASE = "2.6.37"
PV = "${OLD_KERNEL_RELEASE}+${KERNEL_RELEASE}+gitr${SRCPV}"

SRCREV = "b65a0e0c84cf489bfa00d6aa6c48abc5a237100f"

SRC_URI += "git://git.kernel.org/pub/scm/linux/kernel/git/torvalds/linux-2.6.git;protocol=git;branch=master \
            file://defconfig"

S = "${WORKDIR}/git"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-2"
