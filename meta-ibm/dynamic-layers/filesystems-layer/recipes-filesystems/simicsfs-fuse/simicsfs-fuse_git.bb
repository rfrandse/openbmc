SUMMARY = "simicsfs-fuse"
PR = "r1"

inherit simics
inherit systemd

SIMICS_SUBDIR = "base/src/misc/simicsfs-client"
DEPENDS = "fuse libmagicpipe"
EXTRA_OEMAKE = "'CC=${CC}' 'EXTRA_CFLAGS=${CFLAGS}' 'EXTRA_LDFLAGS=${LDFLAGS}'"

SRC_URI += "file://host.mount"
FILES:${PN} += "${systemd_system_unitdir}"

do_install() {
    install -D ${S}/simicsfs-client ${D}${bindir}/simicsfs-client
    install -D -m0644 ${WORKDIR}/host.mount ${D}${systemd_system_unitdir}/host.mount
}
