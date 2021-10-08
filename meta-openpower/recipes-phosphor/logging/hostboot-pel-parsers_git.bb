SUMMARY = "Hostboot PEL python parsers"
DESCRIPTION = "Used by peltool to parse Hostboot UserData sections and SRC details"
PR = "r1"
PV = "1.0+git${SRCPV}"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${S}/LICENSE;md5=34400b68072d710fecd0a2940a0d1658"

S = "${WORKDIR}/git"
SRC_URI += "git://git@github.ibm.com/open-power/hostboot;branch="master-p10";protocol=ssh"
SRCREV = "94b44998ee2547ba2419c05cfa3e2e0873e96fbe"

inherit setuptools3
