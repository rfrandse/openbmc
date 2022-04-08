HOMEPAGE = "https://github.com/openbmc/openpower-vpd-parser"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/files/common-licenses/Apache-2.0;md5=89aea4e17d99a7cacdbeed46a0096b10"
SRC_URI += "git://github.com/ibm-openbmc/openpower-vpd-parser;nobranch=1"
SRCREV = "46a467ac4ea90952d2a9862d1ba765b538b2f7be"

# ECC private patch
# Required to  stay in 1020-ghe branch
SRC_URI += "file://ecc_impl.patch"
