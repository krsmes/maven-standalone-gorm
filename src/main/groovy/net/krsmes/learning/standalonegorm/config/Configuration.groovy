package net.krsmes.learning.standalonegorm.config

import org.codehaus.groovy.grails.commons.GrailsResourceLoaderFactoryBean
import org.codehaus.groovy.grails.commons.GrailsApplication
import org.codehaus.groovy.grails.commons.GrailsApplicationFactoryBean
import org.codehaus.groovy.grails.compiler.support.GrailsResourceLoader
import org.codehaus.groovy.grails.compiler.support.GrailsResourceLoaderHolder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.DependsOn

@org.springframework.context.annotation.Configuration
class Configuration  {

    static def dataSourceConfig = "net.krsmes.learning.standalonegorm.config.DataSource"

    @Bean
    public GrailsResourceLoader grailsResourceLoader() {
        def a = new GrailsResourceLoaderFactoryBean()
        a.afterPropertiesSet()
        a.object;
    }

    @Bean
    @DependsOn(['grailsResourceLoader'])
    public GrailsApplication grailsApplication() {
        GrailsApplication grailsApplication = createGrailsApplication()
        setupDataSource(grailsApplication)
        grailsApplication
    }

    private GrailsApplication createGrailsApplication() {
        def grailsApplicationFactory = new GrailsApplicationFactoryBean()
        def grailsApplication = grailsApplicationFactory.with {
            grailsResourceLoader = GrailsResourceLoaderHolder.resourceLoader
            afterPropertiesSet()
            object
        }
        return grailsApplication
    }

    private void setupDataSource(GrailsApplication grailsApplication) {
        def configSlurper = new ConfigSlurper()
        def datasourceConfig = configSlurper.parse(grailsApplication.classLoader.loadClass(dataSourceConfig))
        grailsApplication.config.merge(datasourceConfig)
        grailsApplication.@loadedClasses = grailsApplication.config.domainClasses.collect {
            domainClass -> grailsApplication.classLoader.loadClass(domainClass) }
    }


}
