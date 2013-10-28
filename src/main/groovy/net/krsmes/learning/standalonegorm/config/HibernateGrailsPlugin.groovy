package net.krsmes.learning.standalonegorm.config

import org.codehaus.groovy.grails.commons.AnnotationDomainClassArtefactHandler
import org.codehaus.groovy.grails.plugins.orm.hibernate.HibernatePluginSupport

class HibernateGrailsPlugin {
    def version = "2.1.0"
    def observe = ['domainClass']
    def dependsOn = [dataSource: "2.1 > *", i18n: "2.1 > *", core: "2.1 > *", domainClass: "2.1 > *"]
    def loadAfter = ['domainClass']

    def artefacts = [new AnnotationDomainClassArtefactHandler()]

    def doWithSpring = HibernatePluginSupport.doWithSpring
    def doWithDynamicMethods = HibernatePluginSupport.doWithDynamicMethods
    def onChange = HibernatePluginSupport.onChange
}
