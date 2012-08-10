package org.devunited.grails.plugin.jsi18n

import java.util.concurrent.ConcurrentHashMap

class Group {

    private static ConcurrentHashMap<String, Group> groups

    static {
        groups = new ConcurrentHashMap<String, Group>()
    }

    public static Group load(String name) {
        groups.get(name) ?: {Group group -> groups.put(name, group); group}.call(new Group(name))
    }

    public static List<String> list() {
        groups.keySet().asList()
    }

    public static each(Closure closure) {
        groups.each {String key, Group val ->
            closure.delegate = [key: key, val: val]
            closure.call(key, val)
        }
    }

    public static String prepareMarkup(String markup, String groups, Closure builder) {
        List<String> groupList = groups.tokenize(',')*.trim()
        StringBuffer stringBuffer = new StringBuffer()
        groupList.each {
            stringBuffer.append(builder.call(it) as String)
            stringBuffer.append('\n')
        }
        stringBuffer.toString() + markup
    }

    public static reset() {
        groups.clear()
    }

    String name

    String header

    List<String> JSPropertyLines = []

    List<String> JSObjects = []

    List<String> propertyLines = []

    public Group(String name) {
        this.name = name
        header = "function i18nDef(prop){ return (prop != null && typeof(prop) != 'undefined')} if(!i18nDef(window.JSi18n)){window.JSi18n = new Object()}"
    }

    void addPropertyLine(String line) {
        propertyLines.push(line)
    }

    void split(String line, Closure closure) {
        List<String> tokens = line.trim().tokenize("=")
        String prop = tokens.remove(0)
        closure.call(prop, tokens.join("="))
    }

    void build() {
        propertyLines.each {line ->
            if (line.trim()) {
                split(line) {String prop, String message ->
                    JSObjects.push(prop.replaceAll(/\.[^\.]*$/, ''))
                    JSPropertyLines.push(" JSi18n.${prop}=\"${message.encodeAsJavaScript()}\";")
                }
            }
        }
        JSObjects = JSObjects.unique()
        Map<String, Boolean> alreadyAdded = [:]
        JSObjects.each {obj ->
            String prev = "JSi18n"
            obj.tokenize('.').each {token ->
                prev += "." + token
                if (!alreadyAdded.get(prev)) {
                    header += " if(!i18nDef(${prev})){${prev}={}}"
                    alreadyAdded.put(prev, true)
                }
            }
        }
        File file = new File(Properties.read('destPath').toString() + File.separatorChar + "${name}.js")
        file.delete()
        file.createNewFile()
        file.append(header)
        JSPropertyLines.each {
            file.append(it)
        }
    }
}