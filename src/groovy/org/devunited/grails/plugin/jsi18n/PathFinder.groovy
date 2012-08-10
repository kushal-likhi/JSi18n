package org.devunited.grails.plugin.jsi18n

import groovy.io.FileType
import org.apache.log4j.Logger

class PathFinder {

    private static log = Logger.getLogger(this)

    public static String findDestinationPath() {
        String path = null
        try {
            new File(".").eachFileRecurse(FileType.DIRECTORIES) {dir ->
                if (path) return
                if (dir.name.equals("generated") && dir.path.matches(/^.*jsi18n[\/\\]generated[\/\\]?$/)) {
                    path = dir.path
                }
            }
        } catch (Exception e) {
            log.error(e.message)
            log.error(e.getStackTrace())
            e.printStackTrace()
        }
        path
    }
    
    public static String findAppPath(){
        new File(".").getCanonicalPath()
    }
    
    public static List<String> findAlli18nDirs(){
        List<String> files = []
        try {
            new File(".").eachFileRecurse(FileType.DIRECTORIES) {dir ->
                if (dir.name.equals("i18n") && dir.path.matches(/^.*grails-app[\/\\]i18n[\/\\]?$/)) {
                    files.push(dir.getPath())
                }
            }
        } catch (Exception e) {
            log.error(e.message)
            log.error(e.getStackTrace())
            e.printStackTrace()
        }
        files
    }

}
