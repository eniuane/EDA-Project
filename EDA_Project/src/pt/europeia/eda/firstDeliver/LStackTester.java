package pt.europeia.eda.firstDeliver;

import static java.lang.System.out;

import java.lang.reflect.Array;
import java.security.AllPermission;

import pt.europeia.eda.Stopwatch;

import java.util.ArrayList;

import edu.princeton.cs.algs4.Average;

import static pt.europeia.eda.ObjectSizeFetcher.sizeOf;

public class LStackTester {
	// A time budget is established per experiment. Each experiment is repeated
	// as many times as necessary to expend this budget. That is, each
	// experiment is repeated until the total time spent repeating it exceeds
	// the budget.
	public static final double timeBudgetPerExperiment = 2.0 /* seconds */;
	
	// Small execution times are very "noisy", since the System.nanoTime()
	// method does not have sufficient precision to measure them. In some
	// systems, smaller execution times may even be measured as 0.0! Hence, in
	// many cases it is preferable to perform a run of contiguous repetitions of
	// an experiment, instead of a single experiment. The total
	// execution time of that run of contiguous repetitions is measured. Then,
	// the execution time of a single experiment is estimated as the average
	// execution time, that is, the total execution time of the contiguous
	// repetitions divided by the number of contiguous repetitions of the
	// experiment performed. Instead of using always the same number of
	// contiguous repetitions, however, it is preferable to establish the
	// minimum
	// duration of a run to value which is clearly long enough for
	// System.nanoTime() to measure with acceptable accuracy.
	public static final double minimumTimePerContiguousRepetitions = 1e-5 /* seconds */;

	// A simple, inefficient way to calculate the median of the values in an
	// ArrayList:
	public static double medianOf(final ArrayList<Double> values) {
		final int size = values.size();

		values.sort(null);

		if (size % 2 == 0)
			return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
		else
			return values.get(size / 2);
	}
	
	public static double averageOf(final ArrayList<Double> values){
		double sum = 0.0;
		
		if(values.size()==1)
			return values.get(0);
		
		for(int i = 0; i != values.size() - 1; i++)
			sum += values.get(i);
		
		return sum / values.size();
	}

	// Estimate the number of contiguous repetitions to perform for a given
	// limit of the numbers to sum in the experiment:
	public static int contiguousRepetitionsFor(final int limit) {
		final Stopwatch stopwatch = new Stopwatch();
		int contiguousRepetitions = 0;
		do {
			final LinkedStack<Integer> stackOfInts = new LinkedStack<Integer>();
			for (int i = 0; i != limit; i++) {
				stackOfInts.push(i);
			}
			contiguousRepetitions++;

		} while (stopwatch.elapsedTime() < minimumTimePerContiguousRepetitions);

		// The loop stops when the minimum time per contiguous repetitions is
		// reached. For longer experiments, this will mostly turn out to be one,
		// which is what we would expect, since contiguous repetitions are
		// useful only for small execution times.

		return contiguousRepetitions;
	}

	// Performs a run of contiguous repetitions of an experiment to obtain the
	// execution time of the method to calculate the sum of the integers from 1
	// to a given limit. The number of contiguous experiments is also passed as
	// argument.
	public static double executionTimeFor(final int limit, final int contiguousRepetitions) {
		final ArrayList<LinkedStack<Integer>> linkedStacks = new ArrayList<LinkedStack<Integer>>();
		for (int i = 0; i != contiguousRepetitions; i++)
			linkedStacks.add(new LinkedStack<Integer>());

		final Stopwatch stopwatch = new Stopwatch();
		for (int i = 0; i != contiguousRepetitions; i++) {
			final LinkedStack<Integer> stackOfInts = linkedStacks.get(i);
			for (int j = 0; j != limit; j++) {
				stackOfInts.push(j);
			}
			linkedStacks.set(i, null);
		}
		return stopwatch.elapsedTime() / contiguousRepetitions;
	}

	// Performs experiments to obtain a sequence of estimates of the execution
	// time of the method to calculate the sum of the integers from 1
	// to a given limit. The number of experiments to performed is not fixed.
	// Rather, a time budget is used and the experiments are repeated until the
	// budged is spent. The sequence of the execution times obtained is then
	// used to calculate the median execution time, which is a reasonably robust
	// statistic. The results are shown, except if this is a warm up run.
	public static void performExperimentsFor(final int limit, final boolean isWarmup) {
		final ArrayList<Double> executionTimes = new ArrayList<Double>();
		final Stopwatch stopwatch = new Stopwatch();
		final int contiguousRepetitions = contiguousRepetitionsFor(limit);
		long repetitions = 0;
		do {
			executionTimes.add(executionTimeFor(limit, contiguousRepetitions));
			repetitions++;
		} while (stopwatch.elapsedTime() < timeBudgetPerExperiment);
		
		final double median = medianOf(executionTimes);
		final double average = averageOf(executionTimes);

		if (!isWarmup)
		{
			final LinkedStack<Integer> stackOfInts = new LinkedStack<Integer>();
			for (int i = 0; i != limit ; i++)
				stackOfInts.push(i);
			
			out.println("Made " + limit + " pushes \t median= " + median + "\t Average= " + average + "\t Minimum= " + executionTimes.get(0) + "\t Maximum= " + executionTimes.get(executionTimes.size() - 1) + "\t Reps= " + repetitions);
			
			if(repetitions == 1)
				System.exit(1);
		}
	}
	
//	public static long allSizeOf(final LinkedStack<Integer> stack)
//	{
//		long totalMemory = sizeOf(stack);
//		
//
//		for (Integer item : stack)
//			totalMemory += sizeOf(item);
//		
//		return totalMemory;
//	}

	public static void main(final String[] arguments) throws InterruptedException {
		// The experiments are run for limits of the sums which increase
		// geometrically, through the powers of 2:

		// Warm up (this attempts to force the JIT compiler to do its work
		// before the experiments actually begin):
		
		//out.println(sizeOf(new Stack<Integer>()));
		
		for (int exponent = 0, limit = 1; exponent != 8; exponent++, limit *= 2)
			performExperimentsFor(limit, true);

		// The actual experiments are performed here, with limits going from 1
		// to 2^30:
		for (int exponent = 0, limit = 1; exponent != 31; exponent++, limit *= 2) 
			performExperimentsFor(limit, false);

	}

}

/*
 * Copyright 2016, Manuel Menezes de Sequeira.
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
 */