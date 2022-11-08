package api.consts;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ClosePositionOrderCodeTest {

	@Test
	public void intValueTest() {
		assertEquals(0, ClosePositionOrderCode.日付_古い順_損益_高い順.intValue());
		assertEquals(1, ClosePositionOrderCode.日付_古い順_損益_低い順.intValue());
		assertEquals(2, ClosePositionOrderCode.日付_新しい順_損益_高い順.intValue());
		assertEquals(3, ClosePositionOrderCode.日付_新しい順_損益_低い順.intValue());
		assertEquals(4, ClosePositionOrderCode.損益_高い順_日付_古い順.intValue());
		assertEquals(5, ClosePositionOrderCode.損益_高い順_日付_新しい順.intValue());
		assertEquals(6, ClosePositionOrderCode.損益_低い順_日付_古い順.intValue());
		assertEquals(7, ClosePositionOrderCode.損益_低い順_日付_新しい順.intValue());
	}

	@Test
	public void toStringTest() {
		assertEquals("0", ClosePositionOrderCode.日付_古い順_損益_高い順.toString());
		assertEquals("1", ClosePositionOrderCode.日付_古い順_損益_低い順.toString());
		assertEquals("2", ClosePositionOrderCode.日付_新しい順_損益_高い順.toString());
		assertEquals("3", ClosePositionOrderCode.日付_新しい順_損益_低い順.toString());
		assertEquals("4", ClosePositionOrderCode.損益_高い順_日付_古い順.toString());
		assertEquals("5", ClosePositionOrderCode.損益_高い順_日付_新しい順.toString());
		assertEquals("6", ClosePositionOrderCode.損益_低い順_日付_古い順.toString());
		assertEquals("7", ClosePositionOrderCode.損益_低い順_日付_新しい順.toString());
	}

	@Test
	public void valueOfTest() {
		assertEquals(ClosePositionOrderCode.日付_古い順_損益_高い順, ClosePositionOrderCode.valueOf(0));
		assertEquals(ClosePositionOrderCode.日付_古い順_損益_低い順, ClosePositionOrderCode.valueOf(1));
		assertEquals(ClosePositionOrderCode.日付_新しい順_損益_高い順, ClosePositionOrderCode.valueOf(2));
		assertEquals(ClosePositionOrderCode.日付_新しい順_損益_低い順, ClosePositionOrderCode.valueOf(3));
		assertEquals(ClosePositionOrderCode.損益_高い順_日付_古い順, ClosePositionOrderCode.valueOf(4));
		assertEquals(ClosePositionOrderCode.損益_高い順_日付_新しい順, ClosePositionOrderCode.valueOf(5));
		assertEquals(ClosePositionOrderCode.損益_低い順_日付_古い順, ClosePositionOrderCode.valueOf(6));
		assertEquals(ClosePositionOrderCode.損益_低い順_日付_新しい順, ClosePositionOrderCode.valueOf(7));
	}

}
