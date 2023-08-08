package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler {

    NGramMap nGramMap;
    public HistoryTextHandler(NGramMap map) {
        nGramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();
        for (String word : words) {
            System.out.print(word + ": ");
            System.out.println(nGramMap.countHistory(word, startYear, endYear).toString());
        }
        return null;
    }
}
