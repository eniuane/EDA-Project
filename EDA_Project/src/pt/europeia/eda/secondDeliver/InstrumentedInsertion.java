package pt.europeia.eda.secondDeliver;

public class InstrumentedInsertion {

	private static int numberOfComparisons = 0;
	private static int numberOfArrayReads = 0;
	private static int numberOfArrayWrites = 0;
	private static int numberOfSwaps = 0;

	private InstrumentedInsertion() {
		throw new RuntimeException("Attempt to instantiate package-class");
	}

	public static <Item extends Comparable<? super Item>> void sort(final Item[] values) {
		for (int numberOfSortedItems = 1; numberOfSortedItems < values.length; numberOfSortedItems++) {
			numberOfArrayReads += numberOfSortedItems * 2;
			for (int i = numberOfSortedItems; i != 0 && isLess(values[i], values[i - 1]); i--)
				swap(values, i - 1, i);
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

	public static int getNumberOfComparisons() {
		return numberOfComparisons;
	}

	public static int getNumberOfArrayReads() {
		return numberOfArrayReads;
	}

	public static int getNumberOfArrayWrites() {
		return numberOfArrayWrites;
	}

	public static int getNumberOfArrayAccesses() {
		return numberOfArrayReads + numberOfArrayWrites;
	}
	
	public static int getNumberOfSwaps() {
		return numberOfSwaps;
	}
}
