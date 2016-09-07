package functionaltest.pages

import geb.Page

class BingQueryPage extends Page {

    static url = "https://www.bing.com/"

    static at = { title.contentEquals("Bing") }

    static content = {
        searchField { $("input", name: "q")}
        searchButton { $("input", name:"go") }
    }

   void search(String word) {
        searchField.value(word)
        searchButton.click()
    }
}