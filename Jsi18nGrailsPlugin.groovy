import org.devunited.grails.plugin.jsi18n.DescriptorHelper

class Jsi18nGrailsPlugin {

    def watchedResources = [
            "file:./grails-app/i18n/**",
            "file:./plugins/*/grails-app/**/*.properties"
    ]

    def version = "3.06"

    def grailsVersion = "2.0 > *"

    def dependsOn = [:]

    def pluginExcludes = [
        "grails-app/views/error.gsp"
    ]

    def title = "Jsi18n Plugin" // Headline display name of the plugin
    def author = "Kushal Likhi"
    def authorEmail = "kushal.likhi@gmail.com"
    def description = '''\
    This plugin aids and provide a very DRY and intuitive way to do internationalization for JavaScript files.
    This plugin is high in runtime performance. It is also CDN/static resource caching friendly, hence could give a faster end user experience.
    This plugin also supports auto reloading of JSi18n properties in the development enviornment just like any other artifact.
    '''

    def documentation = "http://grails.org/plugin/jsi18n"

    def license = "APACHE"

    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    def scm = [ url: "https://github.com/kushal-likhi/JSi18n/" ]

    def doWithWebDescriptor = DescriptorHelper.doWithWebDescriptor

    def doWithSpring = DescriptorHelper.doWithSpring

    def doWithDynamicMethods = DescriptorHelper.doWithDynamicMethods

    def doWithApplicationContext = DescriptorHelper.doWithApplicationContext

    def onChange = DescriptorHelper.onChange

    def onConfigChange = DescriptorHelper.onConfigChange

    def onShutdown = DescriptorHelper.onShutdown
}
