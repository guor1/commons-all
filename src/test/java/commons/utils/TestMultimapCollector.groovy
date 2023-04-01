package commons.utils

import com.google.common.collect.Multimap
import commons.test.User4Test
import spock.lang.Specification
import spock.lang.Unroll

class TestMultimapCollector extends Specification {

    def userList = [new User4Test(username: "a1", email: "a11@163.com"),
                    new User4Test(username: "a1", email: "a12@163.com"),
                    new User4Test(username: "a1", email: "a13@163.com"),
                    new User4Test(username: "a2", email: "a21@163.com"),
                    new User4Test(username: "a2", email: "a21@163.com")]

    @Unroll
    def "test1"() {
        when:
        Multimap<String, String> usernameMap = userList.stream().collect(MultimapCollector.toMultimap(User4Test::getUsername, User4Test::getEmail))

        then:
        with(usernameMap) {
            usernameMap.get("a1")[0] == userList[0].getEmail()
        }
    }

    @Unroll
    def "test2"() {
        when:
        Multimap<String, User4Test> userMap = userList.stream().collect(MultimapCollector.toMultimap(User4Test::getUsername))

        then:
        with(userMap) {
            userMap.get("a1")[0] == userList[0]
        }
    }
}
