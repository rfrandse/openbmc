FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

FILES_${PN} += "${systemd_system_unitdir}/nslcd.service.d/override.conf"
FILES_${PN} += "${systemd_system_unitdir}/nscd.service.d/override.conf"
SRC_URI += "file://override.conf"

do_install_append() {
        install -m 0644 ${D}${sysconfdir}/nslcd.conf ${D}${sysconfdir}/nslcd.conf.default

        install -d ${D}${systemd_system_unitdir}/nslcd.service.d
        install -m 0644 ${WORKDIR}/override.conf \
            ${D}${systemd_system_unitdir}/nslcd.service.d/override.conf

        install -d ${D}${systemd_system_unitdir}/nscd.service.d
        install -m 0644 ${WORKDIR}/override.conf \
            ${D}${systemd_system_unitdir}/nscd.service.d/override.conf
}
