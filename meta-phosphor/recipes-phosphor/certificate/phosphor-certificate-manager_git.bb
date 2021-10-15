SUMMARY = "Phosphor Certificate Manager"
DESCRIPTION = "Manages client and server certificates"
HOMEPAGE = "https://github.com/openbmc/phosphor-certificate-manager"

PR = "r1"
PV = "0.1+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=86d3f3a95c324c9479bd8986968f4327"

SRC_URI = "git://github.com/ibm-openbmc/phosphor-certificate-manager;nobranch=1"
SRCREV = "17af136fc33f2689a658b785b32598113b16db29"

inherit autotools \
        pkgconfig \
        obmc-phosphor-systemd

DEPENDS = " \
        autoconf-archive-native \
        openssl \
        phosphor-dbus-interfaces \
        phosphor-logging \
        sdbusplus \
        sdeventplus \
        "

S = "${WORKDIR}/git"

EXTRA_OECONF += "--disable-tests"

CERT_TMPL = "phosphor-certificate-manager@.service"
SYSTEMD_SERVICE:${PN} = "${CERT_TMPL}"

PACKAGECONFIG ??= ""
PACKAGECONFIG[ibm-hypervisor-cert] = "--enable-ca-cert-extension,,"
SYSTEMD_SERVICE:${PN} += "${@bb.utils.contains('PACKAGECONFIG', 'ibm-hypervisor-cert', 'bmc-vmi-ca-manager.service', '', d)}"
