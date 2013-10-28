package net.krsmes.learning.standalonegorm.config

dataSource {
    dbCreate = "create-drop"
    driverClassName = "org.h2.Driver"
    url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
    username = "sa"
    password = ""
}

domainClasses = ['net.krsmes.learning.standalonegorm.domain.User']