package org.devunited.grails.plugin.jsi18n

import org.apache.log4j.Logger

class Loader {

    static log = Logger.getLogger("grails.app.JSi18nPlugin")

    static buildAll = {applicationContext ->
        log.info "Loading JSi18n Module"
        initialize()
        buildGroups()
        writeGroupsToFile()
    }

    private static initialize() {
        Properties.save 'destPath', PathFinder.findDestinationPath()
        Properties.save 'appPath', PathFinder.findAppPath()
        Properties.save 'dirs', PathFinder.findAlli18nDirs()
        log.trace "Dest Dir: " + Properties.read('destPath')
        log.trace "i18n Dirs: " + Properties.read('dirs')
    }

    private static buildGroups() {
        Group.reset()
        List<String> dirs = Properties.read('dirs') as List<String>
        String tempGroup = null
        dirs.each {dir ->
            new File(dir).eachFile {file ->
                if (file.getName().endsWith(".properties")) {
                    file.eachLine {line ->
                        if (line.trim().find(/#JSi18n\(.*?\)/)) {
                            tempGroup = line.find(/#JSi18n\((.*?)\)/) {
                                tempGroup = it[1] + file.getName().replaceAll(/(messages)|(\.properties)/, '')
                            }
                        } else if (line.trim().find(/#JSi18nEnd/)) {
                            tempGroup = null
                        } else if (tempGroup) Group.load(tempGroup).addPropertyLine(line)
                    }
                }
            }
        }
    }

    private static writeGroupsToFile() {
        new File(Properties.read('destPath') as String).eachFile {
            it.delete()
        }
        log.trace("JSi18n Groups: " + Group.list())
        Group.each {key, Group val ->
            val.build()
            log.trace "Build complete: JSi18n Group-->${key}"
        }
    }
}