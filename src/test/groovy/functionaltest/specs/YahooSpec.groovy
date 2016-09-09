package functionaltest.specs

import functionaltest.pages.BingResultPage
import functionaltest.pages.YahooQueryPage
import functionaltest.pages.YahooResultPage

class YahooSpec extends BasePageGebSpec {

    def "Search \"hello!\""() {
        String q = "hello!"
        YahooResultPage.searchWord = q

        when:
        to YahooQueryPage

        and:
        search(q)

        then:
        waitFor {at YahooResultPage}
    }
    def "Search blank"() {
        String q = ""
        BingResultPage.searchWord = ""

        when:
        to YahooQueryPage

        and:
        waitFor {at YahooQueryPage}
        search(q)

        then:
        waitFor {at YahooQueryPage}

    }
}
