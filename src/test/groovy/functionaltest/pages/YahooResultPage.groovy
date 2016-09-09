package functionaltest.pages

class YahooResultPage extends YahooQueryPage {

    public static String searchWord;

    static at = { title.startsWith(searchWord) }
}