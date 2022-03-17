HOST_FW_LICENSE = "Proprietary"

SRC_URI = "https://rchweb.rchland.ibm.com/afs/rchland/projects/esw/${RELEASE}/Builds/${VERSION}/images/lab/${TARBALL_NAME}"
SRC_URI:append:p10bmc = " https://rchweb.rchland.ibm.com/afs/rchland/projects/esw/${RELEASE}/Builds/${VERSION}/images/lab/${TARBALL_NAME_EVEREST}"
SRC_URI:append:p10bmc = " https://rchweb.rchland.ibm.com/afs/rchland/projects/esw/${RELEASE}/Builds/${VERSION}/ebmc-pkg/staging-dir/host-fw-elements/${ELEMENTS_JSON}"

# P10 systems
RELEASE:p10bmc ?= "fw1020"
VERSION:p10bmc ?= "1020.2212.20220315c"

TARBALL_NAME:p10bmc ?= "obmc-phosphor-image-rainier.ext4.mmc.tar;name=rainier;subdir=rainier"
SRC_URI[rainier.sha256sum] = "cf945765623707e9433a96d29cac022a87904695750c56ecf3d282d88e0ddca6"

TARBALL_NAME_EVEREST:p10bmc ?= "obmc-phosphor-image-everest.ext4.mmc.tar;name=everest;subdir=everest"
SRC_URI[everest.sha256sum] = "5bfca8b599804b96a5b7bb3570f961300032aeb1e6a137a4e752fe379fe8505a"

ELEMENTS_JSON:p10bmc ?= "host-fw-elements_lids.json;name=json"
SRC_URI[json.sha256sum] = "b339c5b81222c27571505ae2c39e438c1aa026af30f99d47827e4ff18386f4b2"

# Tacoma
RELEASE:witherspoon-tacoma ?= "fw1009"
VERSION:witherspoon-tacoma ?= "1009.2135.20210824a"
TARBALL_NAME:witherspoon-tacoma ?= "obmc-phosphor-image-witherspoon-tacoma.ext4.mmc.tar;name=tacoma"
SRC_URI[tacoma.sha256sum] = "cec1ec249e78f9c58309c07dfd3d229a78c551201de4a7a78d2556f07caed4b8"

DEPENDS:p10bmc = "squashfs-tools-native"

S = "${WORKDIR}"
B = "${WORKDIR}/build"
do_compile[cleandirs] = "${B}"

do_compile:prepend:p10bmc() {
    install -d ${B}/squashfs-root-combined

    unsquashfs -d ${B}/squashfs-root-everest ${S}/everest/image-hostfw
    unsquashfs -d ${B}/squashfs-root-rainier ${S}/rainier/image-hostfw

    install -m 0440 ${B}/squashfs-root-everest/* ${B}/squashfs-root-combined/
    install -m 0440 ${B}/squashfs-root-rainier/* ${B}/squashfs-root-combined/

    # Create the squashfs in the ${B} directory since it gets cleaned on every
    # run, otherwise the mksquashfs command will duplicate the content.
    mksquashfs ${B}/squashfs-root-combined ${B}/image-hostfw -all-root
    install -m 0440 ${B}/image-hostfw ${S}/image-hostfw
}

do_compile:append() {
    install -m 0440 ${S}/image-hostfw ${B}/image/hostfw-a
    install -m 0440 ${S}/image-hostfw ${B}/image/hostfw-b
    install -m 0440 ${S}/image-hostfw ${B}/update/image-hostfw
}

FILES:${PN}:append:p10bmc = " ${datadir}/hostfw/elements.json"
do_install:append:p10bmc() {
    install -d ${D}/${datadir}/hostfw
    install -m 0644 ${WORKDIR}/host-fw-elements_lids.json ${D}/${datadir}/hostfw/elements.json
}
