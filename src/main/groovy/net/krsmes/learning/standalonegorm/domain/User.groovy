package net.krsmes.learning.standalonegorm.domain

import grails.persistence.Entity

@Entity
class User {
    String firstName
    String lastName

    static mapping = {
        table name: "MyUsers"
    }

}
