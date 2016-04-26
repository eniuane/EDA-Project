package pt.europeia.eda.sortAlgorithms;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public final class InstrumentedQuickWithoutShuffle {

	private static long numberOfComparisons = 0;
	private static long numberOfArrayReads = 0;
	private static long numberOfArrayWrites = 0;
	private static long numberOfSwaps = 0;

	private InstrumentedQuickWithoutShuffle() {
		throw new RuntimeException("Attempt to instantiate package-class");
	}

	public static <Item extends Comparable<? super Item>> void sort(final Item[] values) {
		resetNumbers();

		sort(values, 0, values.length - 1);

		assert isIncreasing(values) : "Array should be increasing after sorting.";
	}

	private static <Item extends Comparable<? super Item>> void sort(final Item[] values, final int first,
			final int last) {
		if (last <= first)
			return;

		final int j = partition(values, first, last);
		sort(values, first, j - 1);
		sort(values, j + 1, last);

		assert isIncreasing(values, first, last) : "Array segment should be increasing after sorting.";
	}

	private static <Item extends Comparable<? super Item>> int partition(final Item[] values, final int first,
			final int last) {
		int i = first;
		int j = last + 1;
		numberOfArrayReads++;
		final Item pivot = values[first];

		while (true) {

			do {
				++i;
				numberOfArrayReads++;
			} while (isLess(values[i], pivot) && i != last);

			do {
				--j;
				numberOfArrayReads++;
			} while (isLess(pivot, values[j]) && j != first);

			if (i >= j)
				break;

			swap(values, i, j);
		}

		swap(values, first, j);

		return j;
	}

	public static <Item extends Comparable<? super Item>> Item select(final Item[] values, final int k) {
		if (k < 0 || values.length <= k)
			throw new IndexOutOfBoundsException("Selected element out of bounds");

		StdRandom.shuffle(values);

		int first = 0;
		int last = values.length - 1;

		while (last > first) {
			final int j = partition(values, first, last);
			if (j > k)
				last = j - 1;
			else if (j < k)
				first = j + 1;
			else {
				numberOfArrayReads++;
				return values[j];
			}
		}

		numberOfArrayReads++;
		return values[first];
	}

	private static <Value extends Comparable<? super Value>> boolean isLess(final Value first, final Value second) {
		numberOfComparisons++;
		return first.compareTo(second) < 0;
	}

	private static void swap(final Object[] values, final int i, final int j) {
		numberOfArrayWrites += 2;
		numberOfArrayReads += 2;
		numberOfSwaps++;
		final Object temporary = values[i];
		values[i] = values[j];
		values[j] = temporary;
	}

	private static <Item extends Comparable<? super Item>> boolean isIncreasing(final Item[] values) {
		return isIncreasing(values, 0, values.length - 1);
	}

	private static <Item extends Comparable<? super Item>> boolean isIncreasing(final Item[] a, final int first,
			final int last) {
		for (int i = first + 1; i <= last; i++)
			if (isLess(a[i], a[i - 1]))
				return false;
		return true;
	}

	private static void show(final Object[] values) {
		for (Object value : values)
			StdOut.println(value);
	}

	public static void main(final String[] arguments) {
		final In in = new In(arguments[0]);
		final String[] words = in.readAllStrings();
		Quick.sort(words);
		show(words);

		StdRandom.shuffle(words);

		StdOut.println();
		for (int i = 0; i != words.length; i++) {
			final String ith = Quick.select(words, i);
			StdOut.println(ith);
		}
	}

	private static void resetNumbers() {
		numberOfComparisons = 0;
		numberOfArrayReads = 0;
		numberOfArrayWrites = 0;
		numberOfSwaps = 0;
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

	public static long getNumberOfArrayAccesses() {
		return numberOfArrayReads + numberOfArrayWrites;
	}

	public static long getNumberOfSwaps() {
		return numberOfSwaps;
	}
}

/*
 * Copyright 2015 and 2016, Robert Sedgewick and Kevin Wayne.
 * 
 * Copyright 2015 and 2016, Manuel Menezes de Sequeira.
 * 
 * This file is a derivative work of the code which accompanies the textbook
 * 
 * Algorithms, 4th edition by Robert Sedgewick and Kevin Wayne, Addison-Wesley
 * Professional, 2011, ISBN 0-321-57351-X. http://algs4.cs.princeton.edu
 * 
 * This code is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this code. If not, see http://www.gnu.org/licenses.
 * 
 * Any errors found in this code should be assumed to be the responsibility of
 * the author of the modifications to the original code (viz. Manuel Menezes de
 * Sequeira).
 */