PACKAGECONFIG_append = " mmc_layout"

SYSTEMD_SERVICE_${PN} += " \
    obmc-flash-bios-init.service \
    obmc-flash-bios-patch.service \
    openpower-process-host-firmware.service \
    openpower-update-bios-attr-table.service \
"

RDEPENDS_${PN}_append_p10bmc = " phosphor-hostfw-image"
