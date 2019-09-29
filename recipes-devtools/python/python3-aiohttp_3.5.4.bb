SUMMARY = "http client/server for asyncio"
HOMEPAGE = "https://github.com/KeepSafe/aiohttp/"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.txt;md5=cf056e8e7a0a5477451af18b7b5aa98c"

inherit pypi setuptools3

SRC_URI += "file://0001-Improve-make-clean-compatibility.patch"

SRC_URI[md5sum] = "85fe5c9037256c58d4678148bd91b3f3"
SRC_URI[sha256sum] = "9c4c83f4fa1938377da32bc2d59379025ceeee8e24b89f72fcbccd8ca22dc9bf"

RDEPENDS_${PN} = "\
    ${PYTHON_PN}-attrs (>=17.3.0) \
    ${PYTHON_PN}-chardet (>=2.0) \
    ${PYTHON_PN}-multidict (>=4.0) \
    ${PYTHON_PN}-async-timeout (>=3.0) \
    ${PYTHON_PN}-yarl (>=1.0) \
"
