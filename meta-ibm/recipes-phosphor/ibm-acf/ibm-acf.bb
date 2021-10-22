PROVIDES += "ibm-acf"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=e3fc50a88d0a364313df4b21ef20c29e"

SRC_URI = "git://github.com/ibm-openbmc/ibm-acf"
SRCREV = "7db654ffa4846a10781fd58473371daf9038c712"

inherit meson
#JSMN download required
MESONOPTS:remove = " --wrap-mode nodownload "

S = "${WORKDIR}/git"

DEPENDS = " \
    openssl \
    libpam \
    sdbusplus \
    json-c \
"

do_install:append(){
    install -d ${D}/${sysconfdir}/acf
    install -d ${D}/${servicedir}/ibm-acf
    install -m 755 ${S}/subprojects/ce-login/p10-celogin-lab-pub.der \
                   ${D}/${servicedir}/ibm-acf/ibmacf-dev.key
}


FILES:${PN} += " ${base_libdir}/security/* \
                 ${servicedir}/ibm-acf/* "
