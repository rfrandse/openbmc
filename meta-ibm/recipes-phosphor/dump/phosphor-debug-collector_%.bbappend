FILESEXTRAPATHS:prepend := "${THISDIR}/${BPN}:"

inherit openpower-dump

PACKAGECONFIG:append:p10bmc = " host-dump-transport-pldm"
PACKAGECONFIG:append:witherspoon-tacoma = " host-dump-transport-pldm"


PACKAGECONFIG:append:p10bmc = " openpower-dumps-extension"
PACKAGECONFIG:append:witherspoon-tacoma = " openpower-dumps-extension"

EXTRA_OEMESON:append:p10bmc += "-DHOSTBOOT_DUMP_PATH=${hostboot_dump_path}"
EXTRA_OEMESON:append:p10bmc += "-DHOSTBOOT_DUMP_TMP_FILE_DIR=${hostboot_dump_temp_path}"

EXTRA_OEMESON:append:p10bmc += "-DHARDWARE_DUMP_PATH=${hardware_dump_path}"
EXTRA_OEMESON:append:p10bmc += "-DHARDWARE_DUMP_TMP_FILE_DIR=${hardware_dump_temp_path}"

#Dump values is in KB
EXTRA_OEMESON:append:p10bmc += "-DBMC_DUMP_MAX_SIZE=20480"
EXTRA_OEMESON:append:p10bmc += "-DBMC_DUMP_TOTAL_SIZE=409600"

SYSTEMD_SERVICE:${PN}-manager += "clear_hostdumps_poweroff.service"

install_ibm_plugins() {
    install ${S}/tools/dreport.d/ibm.d/plugins.d/* ${D}${dreport_plugin_dir}/
}

#Link the plugins so that dreport can run them based on dump type
python link_ibm_plugins() {
    source = d.getVar('S', True)
    source_path = os.path.join(source, "tools", "dreport.d", "ibm.d", "plugins.d")
    op_plugins = os.listdir(source_path)
    for op_plugin in op_plugins:
        op_plugin_name = os.path.join(source_path, op_plugin)
        install_dreport_user_script(op_plugin_name, d)
}

#Install dump header script from dreport/ibm.d to dreport/include.d
install_dreport_header() {
    install -d ${D}${dreport_include_dir}
    install -m 0755 ${S}/tools/dreport.d/ibm.d/gendumpheader ${D}${dreport_include_dir}/
}

#Install gendumpinfo script from dreport/ibm.d to dreport/include.d
install_gendumpinfo() {
    install -d ${D}${dreport_include_dir}
    install -m 0755 ${S}/tools/dreport.d/ibm.d/gendumpinfo ${D}${dreport_include_dir}/
}

#Install dump package script from dreport/ibm.d to dreport/include.d
install_dump_package() {
    install -d ${D}${dreport_include_dir}
    install -m 0755 ${S}/tools/dreport.d/ibm.d/package ${D}${dreport_include_dir}/
}

IBM_INSTALL_POSTFUNCS:append:witherspoon-tacoma = " install_ibm_plugins link_ibm_plugins"
IBM_INSTALL_POSTFUNCS:append:p10bmc = " install_dreport_header \
                                 install_gendumpinfo \
                                 install_ibm_plugins link_ibm_plugins install_dump_package"

do_install[postfuncs] += "${IBM_INSTALL_POSTFUNCS}"
