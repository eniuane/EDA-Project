package pt.europeia.eda.secondDeliver;

public class InstrumentedSelection {

	private InstrumentedSelection() {
		throw new RuntimeException("Attempt to instantiate package-class");
	}

	private static long numberOfComparisons = 0;
	private static long numberOfArrayReads = 0;
	private static long numberOfArrayWrites = 0;
	private static long numberOfSwaps = 0;

	public static <Item extends Comparable<? super Item>> void sort(final Item[] values) {
		for (int numberOfSortedItems = 0; numberOfSortedItems < values.length - 1; numberOfSortedItems++) {
			int indexOfMinimum = numberOfSortedItems;

			for (int i = indexOfMinimum + 1; i != values.length; i++) {
				numberOfArrayReads += 2;
				if (isLess(values[i], values[indexOfMinimum]))
					indexOfMinimum = i;
			}

			swap(values, numberOfSortedItems, indexOfMinimum);
		}

		assert isIncreasing(values) : "Array should be increasing after sorting.";
	}

	private static <Value extends Comparable<? super Value>> boolean isLess(final Value first, final Value second) {
		numberOfComparisons++;
		return first.compareTo(second) < 0;
	}

	private static void swap(final Object[] values, final int firstPosition, final int secondPosition) {
		numberOfArrayWrites += 2;
		numberOfArrayReads += 2;
		numberOfSwaps++;
		final Object temporary = values[firstPosition];
		values[firstPosition] = values[secondPosition];
		values[secondPosition] = temporary;
	}

	private static <Item extends Comparable<? super Item>> boolean isIncreasing(final Item[] values) {
		for (int i = 1; i < values.length; i++)
			if (isLess(values[i], values[i - 1]))
				return false;
		return true;
	}

	public static long getNumberOfComparisons() {
		return numberOfComparisons;
	}

	public static long getNumberOfArrayReads() {
		return numberOfArrayReads;
	}

	public static long getNumberOfArrayWrites() {
		return numberOfArrayWrites;
	}

	public static long getNumberOfSwaps() {
		return numberOfSwaps;
	}

	public static long getNumberOfArrayAccesses() {
		return numberOfArrayReads + numberOfArrayWrites;
	}

}
