PACKAGECONFIG:append = " mmc_layout"

SYSTEMD_SERVICE:${PN} += " \
    obmc-flash-bios-init.service \
    obmc-flash-bios-patch.service \
"

RDEPENDS:${PN}:append:p10bmc = " phosphor-hostfw-image"
