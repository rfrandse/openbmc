SUMMARY = "Phosphor DBus Interfaces"
DESCRIPTION = "Generated bindings, using sdbus++, for the phosphor YAML"
PR = "r1"

S = "${WORKDIR}/git"

inherit autotools pkgconfig
inherit obmc-phosphor-license
inherit pythonnative
inherit phosphor-dbus-yaml

DEPENDS += "autoconf-archive-native"
DEPENDS += "sdbus++-native"

#SRC_URI += "git://github.com/openbmc/phosphor-dbus-interfaces"
#SRCREV = "bf21cfa8640c968a5e825b141866b858118fb1a1"
SRC_URI += "git://github.com/geissonator/phosphor-dbus-interfaces;branch=ldap"
SRCREV = "d95060b115b4601d2bc89e52830f701d06da9f4d"
SRC_URI += "file://0001-DO-NOT-MERGE-SNMP-Client-Interface-to-be-backward-co.patch"
SRC_URI += "file://0002-Add-new-Software-Version-Incompatible-error.patch"

DEPENDS_remove_class-native = "sdbus++-native"
DEPENDS_remove_class-nativesdk = "sdbus++-native"

PACKAGECONFIG ??= "libphosphor_dbus"
PACKAGECONFIG[libphosphor_dbus] = " \
        --enable-libphosphor_dbus, \
        --disable-libphosphor_dbus, \
        systemd sdbusplus, \
        libsystemd sdbusplus \
        "

PACKAGECONFIG_remove_class-native = "libphosphor_dbus"
PACKAGECONFIG_remove_class-nativesdk = "libphosphor_dbus"

BBCLASSEXTEND += "native nativesdk"
