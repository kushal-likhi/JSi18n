package org.devunited.grails.plugin.jsi18n.taglib

class JSi18nTagLib {

    static namespace = "JSi18n"

    def group = {attrs ->
        String name = attrs['name']
        String locale = session['org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE'] ?: Locale.getDefault()
        out << "<script type=\"text/javascript\" src=\"${resource(dir: 'js', file: 'i18n/generated/' + name + '.js', plugin: 'jsi18n')}\" ></script>"
        out << "<script type=\"text/javascript\" src=\"${resource(dir: 'js', file: 'i18n/generated/' + name + "_" +  locale + '.js', plugin: 'jsi18n')}\" ></script>"
    }
}
