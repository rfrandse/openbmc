SUMMARY = "libmagicpipe"
PR = "r1"

inherit simics

RDEPENDS:${PN}-dev = ""
SIMICS_SUBDIR = "base/src/misc/libmagicpipe"

do_install() {
    install -D ${S}/libmagicpipe.a ${D}${libdir}/libmagicpipe.a
    install -D ${S}/magic-pipe.h ${D}${includedir}/magic-pipe.h
}
