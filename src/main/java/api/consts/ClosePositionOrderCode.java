package api.consts;

/**
 * 決済順序コード。
 * 
 * @see api.SendoderApi body.closePositionOrder:注文発注（株式）情報／決済順序コード。
 * @see api.SendoderFutureApi body.closePositionOrder:注文発注（先物）情報／決済順序コード。
 * @see api.SendoderOptionApi body.closePositionOrder:注文発注（オプション）情報／決済順序コード。
 */
public enum ClosePositionOrderCode {
	日付_古い順_損益_高い順(0), 日付_古い順_損益_低い順(1), 日付_新しい順_損益_高い順(2), 日付_新しい順_損益_低い順(3),
	損益_高い順_日付_古い順(4), 損益_高い順_日付_新しい順(5), 損益_低い順_日付_古い順(6), 損益_低い順_日付_新しい順(7);

	private int id;

	private ClosePositionOrderCode(int id) {
		this.id = id;
	}

	public int intValue() {
		return id;
	}

	@Override
	public String toString() {
		return Integer.toString(id);
	}

	public static ClosePositionOrderCode valueOf(int id) {
		for (ClosePositionOrderCode e : values()) {
			if (e.id == id) {
				return e;
			}
		}
		throw new IllegalArgumentException("No enum constant id=" + id);
	}

}
