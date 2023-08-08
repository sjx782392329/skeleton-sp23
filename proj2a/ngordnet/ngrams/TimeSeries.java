package ngordnet.ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    private static final int MIN_YEAR = 1400;
    private static final int MAX_YEAR = 2100;
    // TODO: Add any necessary static/instance variables.

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        // TODO: Fill in this constructor.
        if (startYear < MIN_YEAR || endYear > MAX_YEAR) {
            throw new IllegalArgumentException("startYear : endYear [1400,2100] startYear is: " + startYear + ", " + "endYear is: " + endYear);
        }
        ts.forEach((k, v) -> {
            if (k >= startYear && k <= endYear) {
                this.put(k, v);
            }
        });
    }

    /**
     * Returns all years for this TimeSeries (in any order).
     */
    public List<Integer> years() {
        // TODO: Fill in this method.
        return List.copyOf(this.keySet());
    }

    /**
     * Returns all data for this TimeSeries (in any order).
     * Must be in the same order as years().
     */
    public List<Double> data() {
        // TODO: Fill in this method.
        List<Double> datas = new ArrayList<>();
        for (int year : this.keySet()) {
            datas.add(this.get(year));
        }
        return datas;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries merge = new TimeSeries();
        for (int year : this.keySet()) {
            merge.put(year, this.get(year));
        }
        for (int year : ts.keySet()) {
            merge.put(year, merge.getOrDefault(year, 0.0) + ts.get(year));
        }
        return merge;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        // TODO: Fill in this method.
        TimeSeries divided = new TimeSeries();
        for (int year : this.keySet()) {
            divided.put(year, this.get(year));
        }
        for (int year : divided.keySet()) {
            if (!ts.containsKey(year)) {
                throw new IllegalArgumentException("TS is missing a year that exists in this TimeSeries");
            }
            divided.put(year, divided.get(year) / ts.get(year));
        }
        return divided;
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
    public static void main(String[] args) {
        TimeSeries timeSeries = new TimeSeries();
        timeSeries.put(1994, 12.0);
        System.out.println(timeSeries);
        System.out.println(timeSeries.years());
    }
}
