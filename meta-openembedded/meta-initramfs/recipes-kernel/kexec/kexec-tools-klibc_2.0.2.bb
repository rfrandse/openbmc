# the binaries are statically linked against klibc
SUMMARY = "Kexec tools, statically compiled against klibc"
AUTHOR = "Eric Biederman"
HOMEPAGE = "http://kernel.org/pub/linux/utils/kernel/kexec/"
SECTION = "kernel/userland"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=ea5bed2f60d357618ca161ad539f7c0a \
                    file://kexec/kexec.c;beginline=1;endline=20;md5=af10f6ae4a8715965e648aa687ad3e09"
DEPENDS = "zlib xz"

PR = "r1"

inherit klibc autotools

FILESPATH =. "${FILE_DIRNAME}/kexec-tools-${PV}:"

SRC_URI = "${KERNELORG_MIRROR}/linux/utils/kernel/kexec/kexec-tools-${PV}.tar.gz"

SRC_URI += " \
            file://kexec-elf-rel.patch \
            file://kexec-syscall.patch \
            file://cflags_static.patch  \
            file://ifdown_errno.patch  \
            file://purgatory_flags.patch \
            file://purgatory_string.patch \
            file://sha256.patch \
            file://sysconf_nrprocessors.patch \
            file://fix-out-of-tree-build.patch \
            file://0001-Adjust-the-order-of-headers-to-fix-build-for-musl.patch \
            "

SRC_URI[md5sum] = "92eff93b097475b7767f8c98df84408a"
SRC_URI[sha256sum] = "09e180ff36dee087182cdc939ba6c6917b6adbb5fc12d589f31fd3659b6471f2"

SRC_URI_append_arm = " file://arm_crashdump.patch"
SRC_URI_append_powerpc = " file://ppc__lshrdi3.patch"
SRC_URI_append_x86 = " file://x86_sys_io.patch file://x86_basename.patch file://x86_vfscanf.patch file://x86_kexec_test.patch"
SRC_URI_append_x86-64 = " file://x86_sys_io.patch file://x86_basename.patch file://x86_vfscanf.patch file://x86_kexec_test.patch"

S = "${WORKDIR}/kexec-tools-${PV}"

EXTRA_OECONF += "--without-zlib --without-lzma --without-xen"

CFLAGS += "-I${STAGING_DIR_HOST}${libdir}/klibc/include -I${STAGING_DIR_HOST}${libdir}/klibc/include/bits32"
CFLAGS_x86-64 += "-I${STAGING_DIR_HOST}${libdir}/klibc/include -I${STAGING_DIR_HOST}${libdir}/klibc/include/bits64"

do_compile_prepend() {
    # Remove the prepackaged config.h from the source tree as it overrides
    # the same file generated by configure and placed in the build tree
    rm -f ${S}/include/config.h

    # Remove the '*.d' file to make sure the recompile is OK
    for dep in `find ${B} -type f -name '*.d'`; do
        dep_no_d="`echo $dep | sed 's#.d$##'`"
        # Remove file.d when there is a file.o
        if [ -f "$dep_no_d.o" ]; then
            rm -f $dep
        fi
    done
}

PACKAGES =+ "kexec-klibc kdump-klibc"

FILES_kexec-klibc = "${sbindir}/kexec"
FILES_kdump-klibc = "${sbindir}/kdump"

INSANE_SKIP_${PN} = "arch"

COMPATIBLE_HOST = '(x86_64.*|i.86.*|arm.*|aarch64.*|powerpc.*|mips.*)-(linux|freebsd.*)'
