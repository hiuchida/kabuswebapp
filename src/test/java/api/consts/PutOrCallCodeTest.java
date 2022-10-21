package api.consts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PutOrCallCodeTest {

	@Test
	public void toStringTest() {
		assertEquals("P", PutOrCallCode.PUT.toString());
		assertEquals("C", PutOrCallCode.CALL.toString());
	}

	@Test
	public void valueOfTest() {
		assertEquals(PutOrCallCode.PUT, PutOrCallCode.valueOfCode("P"));
		assertEquals(PutOrCallCode.CALL, PutOrCallCode.valueOfCode("C"));
	}

}
