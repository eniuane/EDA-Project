package pt.europeia.eda.secondDeliver;

import edu.princeton.cs.algs4.In;
import static pt.europeia.eda.Tools.out;

public class InsertionTester {

	public static void main(final String[] arguments) {

		final In inPartiallySorted = new In(arguments[0]);
		// final In inShuffled = new In(arguments[1]);
		// final In inSorted = new In(arguments[2]);
		
		final Double[] partiallySortedNumbers = inPartiallySorted.readAllDoubles();
		// final double[] shuffledNumbers = inShuffled.readAllInts();
		// final double[] sortedNumbers = inSorted.readAllInts();

		for (int i = 0; i != partiallySortedNumbers.length; i++)
			out.println(partiallySortedNumbers[i]);

		Insertion.sort(partiallySortedNumbers);

	}

}
