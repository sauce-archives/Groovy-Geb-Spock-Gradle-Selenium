package functionaltest.specs

import functionaltest.pages.BingQueryPage
import functionaltest.pages.BingResultPage

class BingSpec extends BasePageGebSpec {

    def "Search \"hello!\""() {
        String q = "hello!"
        BingResultPage.searchWord = q

        when:
        to BingQueryPage

        and:
        search(q)

        then:
        waitFor {at BingResultPage}
    }
    def "Search blank"() {
        String q = ""
        BingResultPage.searchWord = ""

        when:
        to BingQueryPage

        and:
        search(q)

        then:
        waitFor {at BingQueryPage}
    }
}
