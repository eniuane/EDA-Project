package pt.europeia.eda.secondDeliver;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.Stopwatch;

import static pt.europeia.eda.Tools.out;

public class InsertionTester {

	public static void main(final String[] arguments) {

		double estimatedTime = 0;

		final In inPartiallySorted = new In(arguments[0]);
		// final In inShuffled = new In(arguments[1]);
		// final In inSorted = new In(arguments[2]);

		//final String[] partiallySortedNumbers = inPartiallySorted.readAllStrings();
		//final Double[] partiallySortedNumbersDouble = new Double[partiallySortedNumbers.length];
		final Double[] partiallySortedNumbersDouble= readAllDoubles(inPartiallySorted);
		// final double[] shuffledNumbers = inShuffled.readAllInts();
		// final double[] sortedNumbers = inSorted.readAllInts();

		//for (int i = 0; i != partiallySortedNumbers.length; i++)
		//	partiallySortedNumbersDouble[i] = Double.parseDouble(partiallySortedNumbers[i]);

		final Stopwatch stopwatch = new Stopwatch();
		Insertion.sort(partiallySortedNumbersDouble);
		estimatedTime = stopwatch.elapsedTime();

		out.println(estimatedTime);
		
		for (int i = 0; i != partiallySortedNumbersDouble.length; i++)
			out.println(partiallySortedNumbersDouble[i]);

	}
	
	
	public static Double[] readAllDoubles(In in) {
        String[] fields = in.readAllStrings();
        Double[] vals = new Double[fields.length];
        for (int i = 0; i < fields.length; i++)
            vals[i] = Double.parseDouble(fields[i]);
        return vals;
    }

}
