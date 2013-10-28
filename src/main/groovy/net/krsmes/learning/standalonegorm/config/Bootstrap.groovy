package net.krsmes.learning.standalonegorm.config

import grails.util.Holders
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.spring.GrailsRuntimeConfigurator
import org.codehaus.groovy.grails.commons.spring.WebRuntimeSpringConfiguration
import org.codehaus.groovy.grails.plugins.DefaultGrailsPluginManager
import org.codehaus.groovy.grails.plugins.GrailsPluginManager
import org.hibernate.SessionFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.orm.hibernate3.SessionHolder
import org.springframework.transaction.support.TransactionSynchronizationManager

/**
 * @author learning@krsmes.com
 */
class Bootstrap {
    static def plugins = [
            'org.codehaus.groovy.grails.plugins.DomainClassGrailsPlugin',
            'net.krsmes.learning.standalonegorm.config.HibernateGrailsPlugin'
    ]

    static void init() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext("net.krsmes.learning.standalonegorm")
        def grailsApplication = Holders.grailsApplication

        WebRuntimeSpringConfiguration springConfiguration = new WebRuntimeSpringConfiguration(applicationContext, grailsApplication.classLoader)

        def pluginManager = initPlugins(grailsApplication, springConfiguration)
        Holders.pluginManager = pluginManager

        initSessionFactory(pluginManager.applicationContext)
    }

    private static void initSessionFactory(ApplicationContext springApplicationContext) {
        def sessionFactory = (SessionFactory) springApplicationContext.getBean(GrailsRuntimeConfigurator.SESSION_FACTORY_BEAN)
        if (!TransactionSynchronizationManager.hasResource(sessionFactory)) {
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(sessionFactory.openSession()))
        }
    }

    private static GrailsPluginManager initPlugins(GrailsApplication grailsApplication, WebRuntimeSpringConfiguration springConfiguration) {
        def loadedPluginClasses = plugins.collect { plugin -> grailsApplication.classLoader.loadClass(plugin)}
        DefaultGrailsPluginManager pluginManager =
            new DefaultGrailsPluginManager(loadedPluginClasses.toArray(new Class[loadedPluginClasses.size()]), grailsApplication)

        pluginManager.loadPlugins()
        pluginManager.application = grailsApplication
        pluginManager.doArtefactConfiguration()
        pluginManager.doRuntimeConfiguration(springConfiguration)
        pluginManager.applicationContext = initApplication(grailsApplication, springConfiguration)
        pluginManager.doDynamicMethods()
        return pluginManager
    }

    private static ApplicationContext initApplication(GrailsApplication grailsApplication, WebRuntimeSpringConfiguration springConfiguration) {
        grailsApplication.initialise()

        grailsApplication.setMainContext(springConfiguration.getUnrefreshedApplicationContext())
        ApplicationContext springApplicationContext = springConfiguration.getApplicationContext()
        return springApplicationContext
    }

}
