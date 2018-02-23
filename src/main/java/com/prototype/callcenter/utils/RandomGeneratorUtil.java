package com.prototype.callcenter.utils;

import java.util.Random;

/**
 * The RandomGeneratorUtil.
 */
public class RandomGeneratorUtil {

	/**
	 * Gets the random number in range.
	 *
	 * @param min the min
	 * @param max the max
	 * @return the random number in range
	 */
	public static int getRandomNumberInRange(int min, int max) {

		Random r = new Random();
		return r.ints(min, (max + 1)).limit(1).findFirst().getAsInt();

	}

}
