package util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DoubleUtilTest {
	public static final double ASSERT_DELTA = 0.01;

	@Test
	public void doubleValueTest() {
		Double o1 = 123.5;
		double a1 = DoubleUtil.doubleValue(o1);
		assertEquals(123.5, a1, ASSERT_DELTA);
		Double o2 = null;
		double a2 = DoubleUtil.doubleValue(o2);
		assertEquals(0.0, a2, ASSERT_DELTA);
	}

	@Test
	public void intValueTest() {
		Double o1 = 123.5;
		int a1 = DoubleUtil.intValue(o1);
		assertEquals(123, a1);
		Double o2 = null;
		int a2 = DoubleUtil.intValue(o2);
		assertEquals(0, a2);
	}

}
