package ngordnet.main;

import ngordnet.browser.NgordnetQuery;
import ngordnet.browser.NgordnetQueryHandler;
import ngordnet.ngrams.NGramMap;
import ngordnet.ngrams.TimeSeries;
import ngordnet.plotting.Plotter;
import org.knowm.xchart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class HistoryHandler extends NgordnetQueryHandler {

    NGramMap nGramMap;
    public HistoryHandler(NGramMap map) {
        nGramMap = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        final List<String> words = q.words();
        final int startYear = q.startYear();
        final int endYear = q.endYear();
        List<TimeSeries> timeSeriesList = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();
        for (String word : words) {
            labels.add(word);
            timeSeriesList.add(nGramMap.countHistory(word, startYear, endYear));
        }
        XYChart chart = Plotter.generateTimeSeriesChart(labels, timeSeriesList);
        return Plotter.encodeChartAsString(chart);
    }
}
