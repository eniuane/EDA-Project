package pt.europeia.eda.stacks;

import static java.lang.System.out;

import java.lang.reflect.Array;
import java.security.AllPermission;

import pt.europeia.eda.Stopwatch;

import java.util.ArrayList;

public class LinkedStackPopTester {

	public static final double timeBudgetPerExperiment = 2.0 /* seconds */;

	public static final double maxTimeForAnExperiment = 30.0;

	public static final double minimumTimePerContiguousRepetitions = 1e-5 /* seconds */;

	public static double medianOf(final ArrayList<Double> values) {
		final int size = values.size();

		values.sort(null);

		if (size % 2 == 0)
			return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
		else
			return values.get(size / 2);
	}

	public static double averageOf(final ArrayList<Double> values) {
		double sum = 0.0;

		if (values.size() == 1)
			return values.get(0);

		for (int i = 0; i != values.size() - 1; i++)
			sum += values.get(i);

		return sum / values.size();
	}

	public static int contiguousRepetitionsFor(final int limit) {
		int contiguousRepetitions = 1;
		for (int exponent = 0; exponent != 31; exponent++, contiguousRepetitions *= 2) {
			final ArrayList<LinkedStack<Integer>> linkedStacks = new ArrayList<LinkedStack<Integer>>();
			for (int i = 0; i != contiguousRepetitions; i++) {
				linkedStacks.add(new LinkedStack<Integer>());
				for (int j = 0; j != limit; j++)
					linkedStacks.get(i).push(j);
			}

			final Stopwatch stopwatch = new Stopwatch();
			for (int i = 0; i != contiguousRepetitions; i++) {
				final LinkedStack<Integer> linkedStack = linkedStacks.get(i);
				for (int j = 0; j != limit; j++) {
					linkedStack.pop();
				}
				linkedStacks.set(i, null);
			}
			if (stopwatch.elapsedTime() >= minimumTimePerContiguousRepetitions)
				break;
		}

		return contiguousRepetitions;
	}

	public static double executionTimeFor(final int limit, final int contiguousRepetitions) {

		final ArrayList<LinkedStack<Integer>> linkedStacks = new ArrayList<LinkedStack<Integer>>();
		for (int i = 0; i != contiguousRepetitions; i++) {
			linkedStacks.add(new LinkedStack<Integer>());
			for (int j = 0; j != limit; j++)
				linkedStacks.get(i).push(j);
		}

		final Stopwatch stopwatch = new Stopwatch();
		for (int i = 0; i != contiguousRepetitions; i++) {
			final LinkedStack<Integer> linkedStack = linkedStacks.get(i);
			for (int j = 0; j != limit; j++) {
				linkedStack.pop();
			}
			linkedStacks.set(i, null);
		}
		return stopwatch.elapsedTime() / contiguousRepetitions;
	}

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
			out.println("Made " + limit + " pops \t median= " + median + "\t Average= " + average + "\t Minimum= "
					+ executionTimes.get(0) + "\t Maximum= " + executionTimes.get(executionTimes.size() - 1)
					+ "\t Reps= " + repetitions + "\t ContiguousReps= " + contiguousRepetitions);
	}

	public static void main(final String[] arguments) throws InterruptedException {

		for (int exponent = 0, limit = 1; exponent != 8; exponent++, limit *= 2)
			performExperimentsFor(limit, true);

		for (int exponent = 0, limit = 1; exponent != 31; exponent++, limit *= 2) {
			final Stopwatch stopwatch = new Stopwatch();
			performExperimentsFor(limit, false);
			if (stopwatch.elapsedTime() > maxTimeForAnExperiment)
				break;
		}
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