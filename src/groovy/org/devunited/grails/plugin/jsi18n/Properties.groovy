package org.devunited.grails.plugin.jsi18n

import java.util.concurrent.ConcurrentHashMap


class Properties {

    private static ConcurrentHashMap<String, Object> props

    public static void save(String key, def value) {
        getProps().put(key, value)
    }

    public static Object read(String prop) {
        getProps().get(prop)
    }

    private static ConcurrentHashMap<String, Object> getProps() {
        if (!props) props = new ConcurrentHashMap<String, Object>()
        props
    }
}
