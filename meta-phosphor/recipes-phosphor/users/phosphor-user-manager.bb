SUMMARY = "Phosphor User Manager Daemon"
DESCRIPTION = "Daemon that does user management"
HOMEPAGE = "http://github.com/openbmc/phosphor-user-manager"
PR = "r1"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"
inherit autotools pkgconfig
inherit obmc-phosphor-dbus-service

DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbusplus"
DEPENDS += "phosphor-logging"
DEPENDS += "phosphor-dbus-interfaces"
DEPENDS += "boost"
DEPENDS += "nss-pam-ldapd"
PACKAGE_BEFORE_PN = "phosphor-ldap"
RDEPENDS_${PN} += "libsystemd"
RDEPENDS_${PN} += "phosphor-logging"
RDEPENDS_${PN} += "bash"

inherit useradd

USERADD_PACKAGES = "${PN} phosphor-ldap"
DBUS_PACKAGES = "${USERADD_PACKAGES}"
# add groups needed for privilege maintenance
GROUPADD_PARAM_${PN} = "priv-admin; priv-operator; priv-user; priv-callback; web; redfish "
GROUPADD_PARAM_phosphor-ldap = "priv-admin; priv-operator; priv-user; priv-callback "
DBUS_SERVICE_${PN} += "xyz.openbmc_project.User.Manager.service"
FILES_phosphor-ldap += " \
        ${sbindir}/phosphor-ldap-conf \
        ${sbindir}/phosphor-ldap-mapper \
"
DBUS_SERVICE_phosphor-ldap = " \
        xyz.openbmc_project.Ldap.Config.service \
        xyz.openbmc_project.LDAP.PrivilegeMapper.service \
"

SRC_URI += "git://git@github.ibm.com/openbmc/phosphor-user-manager;branch=OP920.10;protocol=ssh"
SRCREV = "1c6032e448a3d15add5d7ac4b27cb0e4d2a2972b"
SRC_URI += "file://add_groups_workaround.sh"


S = "${WORKDIR}/git"

do_install_append() {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/add_groups_workaround.sh ${D}${bindir}/add_groups_workaround.sh
}
