HOST_FW_LICENSE = "Proprietary"

SRC_URI = "https://rchweb.rchland.ibm.com/afs/rchland/projects/esw/${RELEASE}/Builds/${VERSION}/images/lab/${TARBALL_NAME}"
SRC_URI:append:p10bmc = " https://rchweb.rchland.ibm.com/afs/rchland/projects/esw/${RELEASE}/Builds/${VERSION}/images/lab/${TARBALL_NAME_EVEREST}"
SRC_URI:append:p10bmc = " https://rchweb.rchland.ibm.com/afs/rchland/projects/esw/${RELEASE}/Builds/${VERSION}/ebmc-pkg/staging-dir/host-fw-elements/${ELEMENTS_JSON}"

# P10 systems
RELEASE:p10bmc ?= "fw1020"
VERSION:p10bmc ?= "1020.2144.20211025a"

TARBALL_NAME:p10bmc ?= "obmc-phosphor-image-rainier.ext4.mmc.tar;name=rainier;subdir=rainier"
SRC_URI[rainier.sha256sum] = "aa909967b3902d7671a280a76bf9e250eae8e45f14ffad2fda3633a3ed4c63b7"

TARBALL_NAME_EVEREST:p10bmc ?= "obmc-phosphor-image-everest.ext4.mmc.tar;name=everest;subdir=everest"
SRC_URI[everest.sha256sum] = "d217153a6050d0dd13bddb848c104fdda3558a8ffbcd45c4f55a3c06c0055738"

ELEMENTS_JSON:p10bmc ?= "host-fw-elements_lids.json;name=json"
SRC_URI[json.sha256sum] = "8876249750165fc6480df41e1cb5631087dc2620890304599f79e85993b7f54e"

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

    unsquashfs -d ${B}/squashfs-root-rainier ${S}/rainier/image-hostfw
    unsquashfs -d ${B}/squashfs-root-everest ${S}/everest/image-hostfw

    install -m 0440 ${B}/squashfs-root-rainier/* ${B}/squashfs-root-combined/
    install -m 0440 ${B}/squashfs-root-everest/* ${B}/squashfs-root-combined/

    mksquashfs ${B}/squashfs-root-combined ${S}/image-hostfw -all-root
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
