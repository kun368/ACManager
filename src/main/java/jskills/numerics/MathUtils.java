package jskills.numerics;

import java.util.Collection;

/**
 * For all the functions that aren't in java.lang.Math
 */
public final class MathUtils {

	/** Don't allow instantiation **/
	private MathUtils() { }
	
	/** Square a number **/
	public static double square(double x) { return x*x; }
	
	
	public static double mean(Collection<Double> collection) {
	    double ret = 0;
	    for(Double d : collection) ret += d.doubleValue();
	    return ret/collection.size();
	}
}
