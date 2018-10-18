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
RDEPENDS_${PN} += "libsystemd"
RDEPENDS_${PN} += "phosphor-logging"
RDEPENDS_${PN} += "bash"

inherit useradd

USERADD_PACKAGES = "${PN}"
# add groups needed for privilege maintenance
GROUPADD_PARAM_${PN} = "priv-admin; priv-operator; priv-user; priv-callback; web; redfish "
GROUPADD_PARAM_phosphor-ldap = "priv-admin; priv-operator; priv-user; priv-callback "
DBUS_SERVICE_${PN} += "xyz.openbmc_project.User.Manager.service"

#SRC_URI += "git://github.com/openbmc/phosphor-user-manager"
#SRCREV = "7ba3c71cb31c6316e364d1c3c8abde249a6724d1"
SRC_URI += "file://add_groups_workaround.sh"
SRC_URI += "git://github.com/geissonator/phosphor-user-manager;branch=ldap"
SRCREV = "3acca0dc88a7e2c6355609a07a48ccaf4eee220c"
DBUS_SERVICE_${PN} += "xyz.openbmc_project.User.Manager.service"
FILES_phosphor-ldap += " \
        ${sbindir}/phosphor-ldap-mapper \
"
DBUS_SERVICE_phosphor-ldap = " \
        xyz.openbmc_project.LDAP.PrivilegeMapper.service \
"
S = "${WORKDIR}/git"

do_install_append() {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/add_groups_workaround.sh ${D}${bindir}/add_groups_workaround.sh
}
