package api.consts.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SecurityTypeCodeTest {

	@Test
	public void intValueTest() {
		assertEquals(1, SecurityTypeCode.株式.intValue());
	}

	@Test
	public void toStringTest() {
		assertEquals("1", SecurityTypeCode.株式.toString());
	}

	@Test
	public void valueOfTest() {
		assertEquals(SecurityTypeCode.株式, SecurityTypeCode.valueOf(1));
	}

}
