package net.krsmes.learning.standalonegorm

import net.krsmes.learning.standalonegorm.config.Bootstrap
import net.krsmes.learning.standalonegorm.domain.User
import org.junit.Before
import org.junit.Test

class StandaloneGormTest {

    @Before
    void bootstrap() {
        Bootstrap.init()
    }

    @Test
    void sanity() {
        assert true
    }

    @Test
    void saveAndFindUserDomain() {
        def u = new User(firstName: 'Kevin', lastName: 'Smith').save(flush: true)

        assert User.findAllByLastName('Smith').size() == 1
    }
}
