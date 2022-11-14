package api.model;

import java.util.ArrayList;
import java.util.List;

import com.github.hiuchida.api.consts.ClosePositionOrderCode;
import com.github.hiuchida.api.consts.SideCode;
import com.github.hiuchida.api.consts.UnderOverCode;

import api.consts.deliv.AfterHitOrderTypeDCode;
import api.consts.deliv.ExchangeDCode;
import api.consts.deliv.FrontOrderTypeDCode;
import api.consts.deliv.TimeInForceCode;
import api.consts.deliv.TradeTypeCode;
import io.swagger.client.model.PositionsDeriv;
import io.swagger.client.model.RequestSendOrderDerivFutureReverseLimitOrder;
import io.swagger.client.model.RequestSendOrderDerivOption;

/**
 * SendoderOptionApiの引数。
 * 
 * Integer型をenum型に変更。
 * String passwordは、SendoderOptionApi側で設定するため、不要。
 * 
 * @see io.swagger.client.model.RequestSendOrderDerivOption
 */
public class SendOrderOptionRequestWrapper {

	public static class ClosePosition {
		private String holdID = null;

		private Integer qty = null;

		public String getHoldID() {
			return holdID;
		}

		public void setHoldID(String holdID) {
			this.holdID = holdID;
		}

		public Integer getQty() {
			return qty;
		}

		public void setQty(Integer qty) {
			this.qty = qty;
		}

		public PositionsDeriv toPositionsDeriv() {
			PositionsDeriv pd = new PositionsDeriv();
			pd.setHoldID(holdID);
			pd.setQty(qty);
			return pd;
		}

	}

	public static class ReverseLimitOrder {
		private Double triggerPrice = null;

		private UnderOverCode underOver = null;

		private AfterHitOrderTypeDCode afterHitOrderType = null;

		private Double afterHitPrice = null;

		public Double getTriggerPrice() {
			return triggerPrice;
		}

		public void setTriggerPrice(Double triggerPrice) {
			this.triggerPrice = triggerPrice;
		}

		public UnderOverCode getUnderOver() {
			return underOver;
		}

		public void setUnderOver(UnderOverCode underOver) {
			this.underOver = underOver;
		}

		public AfterHitOrderTypeDCode getAfterHitOrderType() {
			return afterHitOrderType;
		}

		public void setAfterHitOrderType(AfterHitOrderTypeDCode afterHitOrderType) {
			this.afterHitOrderType = afterHitOrderType;
		}

		public Double getAfterHitPrice() {
			return afterHitPrice;
		}

		public void setAfterHitPrice(Double afterHitPrice) {
			this.afterHitPrice = afterHitPrice;
		}

		public RequestSendOrderDerivFutureReverseLimitOrder toRequestSendOrderDerivFutureReverseLimitOrder() {
			RequestSendOrderDerivFutureReverseLimitOrder rlo = new RequestSendOrderDerivFutureReverseLimitOrder();
			rlo.setTriggerPrice(triggerPrice);
			rlo.setUnderOver(underOver.intValue());
			rlo.setAfterHitOrderType(afterHitOrderType.intValue());
			rlo.setAfterHitPrice(afterHitPrice);
			return rlo;
		}

	}

//	private String password = null;

	private String symbol = null;

	private ExchangeDCode exchange = null;

	private TradeTypeCode tradeType = null;

	private TimeInForceCode timeInForce = null;

	private SideCode side = null;

	private Integer qty = null;

	private ClosePositionOrderCode closePositionOrder = null;

	private List<ClosePosition> closePositions = null;

	private FrontOrderTypeDCode frontOrderType = null;

	private Double price = null;

	private Integer expireDay = null;

	private ReverseLimitOrder reverseLimitOrder = null;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public ExchangeDCode getExchange() {
		return exchange;
	}

	public void setExchange(ExchangeDCode exchange) {
		this.exchange = exchange;
	}

	public TradeTypeCode getTradeType() {
		return tradeType;
	}

	public void setTradeType(TradeTypeCode tradeType) {
		this.tradeType = tradeType;
	}

	public TimeInForceCode getTimeInForce() {
		return timeInForce;
	}

	public void setTimeInForce(TimeInForceCode timeInForce) {
		this.timeInForce = timeInForce;
	}

	public SideCode getSide() {
		return side;
	}

	public void setSide(SideCode side) {
		this.side = side;
	}

	public Integer getQty() {
		return qty;
	}

	public void setQty(Integer qty) {
		this.qty = qty;
	}

	public ClosePositionOrderCode getClosePositionOrder() {
		return closePositionOrder;
	}

	public void setClosePositionOrder(ClosePositionOrderCode closePositionOrder) {
		this.closePositionOrder = closePositionOrder;
	}

	public List<ClosePosition> getClosePositions() {
		return closePositions;
	}

	public void setClosePositions(List<ClosePosition> closePositions) {
		this.closePositions = closePositions;
	}

	public FrontOrderTypeDCode getFrontOrderType() {
		return frontOrderType;
	}

	public void setFrontOrderType(FrontOrderTypeDCode frontOrderType) {
		this.frontOrderType = frontOrderType;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getExpireDay() {
		return expireDay;
	}

	public void setExpireDay(Integer expireDay) {
		this.expireDay = expireDay;
	}

	public ReverseLimitOrder getReverseLimitOrder() {
		return reverseLimitOrder;
	}

	public void setReverseLimitOrder(ReverseLimitOrder reverseLimitOrder) {
		this.reverseLimitOrder = reverseLimitOrder;
	}

	public RequestSendOrderDerivOption toRequestSendOrderDerivOption() {
		RequestSendOrderDerivOption body = new RequestSendOrderDerivOption();
		body.setSymbol(symbol);
		body.setExchange(exchange.intValue());
		body.setTradeType(tradeType.intValue());
		body.setTimeInForce(timeInForce.intValue());
		body.setSide(side.toString());
		body.setQty(qty);
		if (closePositionOrder != null) {
			body.setClosePositionOrder(closePositionOrder.intValue());
		}
		if (closePositions != null) {
			List<PositionsDeriv> pdl = new ArrayList<>();
			for (ClosePosition cp : closePositions) {
				PositionsDeriv pd = cp.toPositionsDeriv();
				pdl.add(pd);
			}
			body.setClosePositions(pdl);
		}
		body.setFrontOrderType(frontOrderType.intValue());
		body.setPrice(price);
		body.setExpireDay(expireDay);
		if (reverseLimitOrder != null) {
			RequestSendOrderDerivFutureReverseLimitOrder rlo = reverseLimitOrder.toRequestSendOrderDerivFutureReverseLimitOrder();
			body.setReverseLimitOrder(rlo);
		}
		return body;
	}

}
