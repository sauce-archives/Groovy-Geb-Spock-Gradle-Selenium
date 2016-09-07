package functionaltest.pages

class BingResultPage extends BingQueryPage {

    public static String searchWord;

    static at = { title.startsWith(searchWord) }
}