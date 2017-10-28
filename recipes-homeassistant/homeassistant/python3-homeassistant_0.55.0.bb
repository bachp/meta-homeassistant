SUMMARY = "Open-source home automation platform running on Python 3"
HOMEPAGE = "https://home-assistant.io/"
SECTION = "devel/python"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE.md;md5=f4eda51018051de136d3b3742e9a7a40"

HOMEASSISTANT_CONFIG_DIR ?= "${localstatedir}/lib/homeassistant"
HOMEASSISTANT_CONFIG_DIR[doc] = "Configuration directory used by home-assistant."
HOMEASSISTANT_USER ?= "homeassistant"
HOMEASSISTANT_USER[doc] = "User the home-assistent service runs as."

inherit setuptools3 useradd update-rc.d systemd

inherit pypi
SRC_URI[md5sum] = "72b8c6d977ed9d729be3e194c3bdb4ec"
SRC_URI[sha256sum] = "d70757e42dba06b5441694e44ceb5d084c728db1f430f049dc002eb6cc263572"

SRC_URI += "\
    file://homeassistant.service \
    file://homeassistant.init \
    file://0001-remove-typing-it-is-already-included-in-python-3.5.patch \
    "

USERADD_PACKAGES = "${PN}"
GROUPADD_PARAM_${PN} = "homeassistant"
USERADD_PARAM_${PN} = "--system --home ${HOMEASSISTANT_CONFIG_DIR} \
                       --no-create-home --shell /bin/false \
                       --groups homeassistant,dialout --gid homeassistant ${HOMEASSISTANT_USER}"

INITSCRIPT_NAME = "homeassistant"

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "homeassistant.service"

do_install_append () {
    install -d -o ${HOMEASSISTANT_USER} -g homeassistant ${D}${HOMEASSISTANT_CONFIG_DIR}

    # Install init scripts and set correct config directory
    install -d ${D}${sysconfdir}/init.d
    install -m 0755 ${WORKDIR}/homeassistant.init  ${D}${sysconfdir}/init.d/homeassistant
    sed -i -e 's,@HOMEASSISTANT_CONFIG_DIR@,${HOMEASSISTANT_CONFIG_DIR},g'  ${D}${sysconfdir}/init.d/homeassistant
    sed -i -e 's,@HOMEASSISTANT_USER@,${HOMEASSISTANT_USER},g'  ${D}${sysconfdir}/init.d/homeassistant

    # Install systemd unit files and set correct config directory
    install -d ${D}${systemd_unitdir}/system
    install -m 0644 ${WORKDIR}/homeassistant.service ${D}${systemd_unitdir}/system
    sed -i -e 's,@HOMEASSISTANT_CONFIG_DIR@,${HOMEASSISTANT_CONFIG_DIR},g' ${D}${systemd_unitdir}/system/homeassistant.service
    sed -i -e 's,@HOMEASSISTANT_USER@,${HOMEASSISTANT_USER},g' ${D}${systemd_unitdir}/system/homeassistant.service
}

# Home Assistant core
RDEPENDS_${PN} = " \
    python3-requests (= 2.14.2) \
    python3-pyyaml (>= 3.11)  \
    python3-pytz (>= 2017.02) \
    python3-pip (>= 8.0.3) \
    python3-jinja2 (>= 2.9.6) \
    python3-voluptuous (= 0.10.5) \
    python3-typing (>= 3) \
    python3-aiohttp (= 2.2.5)\
    python3-async-timeout (= 1.4.0) \
    python3-chardet (= 3.0.4) \
    python3-astral (= 1.4) \
    \
    python3-asyncio \
    python3-multiprocessing \
    python3-sqlite3 \
    python3-html \
    "


# Component dependencies
RDEPENDS_${PN} += " \
    python3-aiohttp-cors (= 0.5.3) \
    python3-aiolifx (= 0.6.0) \
    python3-aiolifx-effects (= 0.1.2) \
    python3-colorlog (= 3.0.1) \
    python3-distro (= 1.0.4) \
    python3-forecastio (>= 1.3.5) \
    python3-fritzconnection (= 0.6.3) \
    python3-fuzzywuzzy (= 0.15.1) \
    python3-google-api-python-client (= 1.6.4) \
    python3-gtts-token (= 1.1.1) \
    python3-hbmqtt (= 0.8) \
    python3-jsonrpc-async (= 0.6) \
    python3-jsonrpc-websocket (= 0.5) \
    python3-libpurecoollink (= 0.4.2) \
    python3-liffylights (= 0.9.4) \
    python3-lightify (= 1.0.6) \
    python3-lnetatmo (= 0.9.2) \
    python3-matrix-client (= 0.0.6) \
    python3-mutagen (= 1.38) \
    python3-mystrom (= 0.3.8) \
    python3-netdisco (= 1.2.2) \
    python3-oauth2client (= 4.0.0)\
    python3-paho-mqtt (= 1.3.0) \
    python3-phue (= 1.0) \
    python3-psutil (= 5.3.1) \
    python3-py-cpuinfo (= 3.3.0) \
    python3-pycec (= 0.4.13) \
    python3-pychromecast (= 0.8.2) \
    python3-pydroid-ipcam (= 0.8) \
    python3-pyemby (= 1.4) \
    python3-pylgtv (= 0.1.7) \
    python3-pymata (= 2.14) \
    python3-pyotp (= 2.2.6) \
    python3-python-telegram-bot (= 8.0) \
    python3-pytradfri (= 2.2.2) \
    python3-pyunifi (= 2.13) \
    python3-restrictedpython (= 4.0a3) \
    python3-roombapy (= 1.3.1) \
    python3-samsungctl (= 0.6.0) \
    python3-soco (= 0.12) \
    python3-spotipy (= 2.4.4) \
    python3-sqlalchemy (>= 1.1.14) \
    python3-wakeonlan (= 0.2.2) \
    python3-websockets (= 3.2) \
    python3-xknx (= 0.7.14) \
    python3-xmltodict (= 0.11.0)\
    python3-yahooweather (>= 0.8) \
    python3-yeelight (= 0.3.3) \
    python3-zeroconf (= 0.19.1) \
    "
