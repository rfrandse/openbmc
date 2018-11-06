SUMMARY = "OpenPower procedure control"
DESCRIPTION = "Provides procedures that run against the host chipset"
PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

S = "${WORKDIR}/git"

inherit autotools obmc-phosphor-utils pkgconfig pythonnative
inherit systemd

SRC_URI += "git://git@github.ibm.com/openbmc/openpower-proc-control;branch=OP920.10;protocol=ssh"
SRCREV = "641cfaa099868422e61a70c5723af07fb9b4217d"

DEPENDS += " \
        autoconf-archive-native \
        phosphor-logging \
        phosphor-dbus-interfaces \
        openpower-dbus-interfaces \
        "

RDEPENDS_${PN} += " \
        phosphor-logging \
        phosphor-dbus-interfaces \
        openpower-dbus-interfaces \
        "

TEMPLATE = "pcie-poweroff@.service"
INSTANCE_FORMAT = "pcie-poweroff@{}.service"
INSTANCES = "${@compose_list(d, 'INSTANCE_FORMAT', 'OBMC_CHASSIS_INSTANCES')}"
SYSTEMD_PACKAGES = "${PN}"
SYSTEMD_SERVICE_${PN} = "${TEMPLATE} ${INSTANCES}"
