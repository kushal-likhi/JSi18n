package jsi18n

import org.codehaus.groovy.grails.web.util.StreamCharBuffer
import org.codehaus.groovy.grails.web.sitemesh.GSPSitemeshPage
import org.devunited.grails.plugin.jsi18n.Group
import org.devunited.grails.plugin.jsi18n.taglib.JSi18nTagLib

class JSi18nFilters {

    def filters = {
        all(controller:'*', action:'*') {
            before = {

            }
            after = { Map model ->
                try {
                    JSi18nTagLib itg = grailsApplication.mainContext.getBean("com.webinventions.emergly.internationalization.taglib.InternationalizationTagLib") as JSi18nTagLib
                    GSPSitemeshPage sitemeshPage = response?.getContent() as GSPSitemeshPage
                    if (sitemeshPage && sitemeshPage.getPage()) {
                        String jsi18nProp = sitemeshPage.getProperty("meta.jsi18n")
                        if (jsi18nProp) {
                            String head = new String(sitemeshPage.getHead())
                            StreamCharBuffer writer = new StreamCharBuffer()
                            String newHead = Group.prepareMarkup(head, jsi18nProp) {String group ->
                                itg.group(name: group)
                            }
                            writer.getWriter().write(newHead)
                            sitemeshPage.setHeadBuffer(writer)
                        }
                    }
                } catch (Throwable ex) {
                    ex.printStackTrace()
                }
            }
            afterView = { Exception e ->

            }
        }
    }
}
