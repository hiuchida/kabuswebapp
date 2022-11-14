package api.model;

import java.util.ArrayList;
import java.util.List;

import com.github.hiuchida.api.consts.SideCode;

import api.consts.ClosePositionOrderCode;
import api.consts.UnderOverCode;
import api.consts.stock.AccountTypeCode;
import api.consts.stock.AfterHitOrderTypeSCode;
import api.consts.stock.CashmarginStockCode;
import api.consts.stock.DelivTypeCode;
import api.consts.stock.ExchangeSCode;
import api.consts.stock.FrontOrderTypeSCode;
import api.consts.stock.FundTypeCode;
import api.consts.stock.MarginTradeTypeCode;
import api.consts.stock.SecurityTypeCode;
import io.swagger.client.model.Positions;
import io.swagger.client.model.RequestSendOrder;
import io.swagger.client.model.RequestSendOrderReverseLimitOrder;

/**
 * SendoderApiの引数。
 * 
 * Integer型やString型を、enum型に変更。
 * String passwordは、SendoderFutureApi側で設定するため不要。
 * 
 * @see io.swagger.client.model.RequestSendOrder
 */
public class SendOrderRequestWrapper {

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

		public Positions toPositions() {
			Positions p = new Positions();
			p.setHoldID(holdID);
			p.setQty(qty);
			return p;
		}

	}

	public static class ReverseLimitOrder {
		private Integer triggerSec = null;

		private Double triggerPrice = null;

		private UnderOverCode underOver = null;

		private AfterHitOrderTypeSCode afterHitOrderType = null;

		private Double afterHitPrice = null;

		public Integer getTriggerSec() {
			return triggerSec;
		}

		public void setTriggerSec(Integer triggerSec) {
			this.triggerSec = triggerSec;
		}

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

		public AfterHitOrderTypeSCode getAfterHitOrderType() {
			return afterHitOrderType;
		}

		public void setAfterHitOrderType(AfterHitOrderTypeSCode afterHitOrderType) {
			this.afterHitOrderType = afterHitOrderType;
		}

		public Double getAfterHitPrice() {
			return afterHitPrice;
		}

		public void setAfterHitPrice(Double afterHitPrice) {
			this.afterHitPrice = afterHitPrice;
		}

		public RequestSendOrderReverseLimitOrder toRequestSendOrderReverseLimitOrder() {
			RequestSendOrderReverseLimitOrder rlo = new RequestSendOrderReverseLimitOrder();
			rlo.setTriggerSec(triggerSec);
			rlo.setTriggerPrice(triggerPrice);
			rlo.setUnderOver(underOver.intValue());
			rlo.setAfterHitOrderType(afterHitOrderType.intValue());
			rlo.setAfterHitPrice(afterHitPrice);
			return rlo;
		}

	}

//	  private String password = null;

	  private String symbol = null;

	  private ExchangeSCode exchange = null;

	  private SecurityTypeCode securityType = null;

	  private SideCode side = null;

	  private CashmarginStockCode cashMargin = null;

	  private MarginTradeTypeCode marginTradeType = null;

	  private Double marginPremiumUnit = null;

	  private DelivTypeCode delivType = null;

	  private FundTypeCode fundType = null;

	  private AccountTypeCode accountType = null;

	  private Integer qty = null;

	  private ClosePositionOrderCode closePositionOrder = null;

	  private List<ClosePosition> closePositions = null;

	  private FrontOrderTypeSCode frontOrderType = null;

	  private Double price = null;

	  private Integer expireDay = null;

	  private ReverseLimitOrder reverseLimitOrder = null;

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public ExchangeSCode getExchange() {
		return exchange;
	}

	public void setExchange(ExchangeSCode exchange) {
		this.exchange = exchange;
	}

	public SecurityTypeCode getSecurityType() {
		return securityType;
	}

	public void setSecurityType(SecurityTypeCode securityType) {
		this.securityType = securityType;
	}

	public SideCode getSide() {
		return side;
	}

	public void setSide(SideCode side) {
		this.side = side;
	}

	public CashmarginStockCode getCashMargin() {
		return cashMargin;
	}

	public void setCashMargin(CashmarginStockCode cashMargin) {
		this.cashMargin = cashMargin;
	}

	public MarginTradeTypeCode getMarginTradeType() {
		return marginTradeType;
	}

	public void setMarginTradeType(MarginTradeTypeCode marginTradeType) {
		this.marginTradeType = marginTradeType;
	}

	public Double getMarginPremiumUnit() {
		return marginPremiumUnit;
	}

	public void setMarginPremiumUnit(Double marginPremiumUnit) {
		this.marginPremiumUnit = marginPremiumUnit;
	}

	public DelivTypeCode getDelivType() {
		return delivType;
	}

	public void setDelivType(DelivTypeCode delivType) {
		this.delivType = delivType;
	}

	public FundTypeCode getFundType() {
		return fundType;
	}

	public void setFundType(FundTypeCode fundType) {
		this.fundType = fundType;
	}

	public AccountTypeCode getAccountType() {
		return accountType;
	}

	public void setAccountType(AccountTypeCode accountType) {
		this.accountType = accountType;
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

	public FrontOrderTypeSCode getFrontOrderType() {
		return frontOrderType;
	}

	public void setFrontOrderType(FrontOrderTypeSCode frontOrderType) {
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

	public RequestSendOrder toRequestSendOrder() {
		RequestSendOrder body = new RequestSendOrder();
		body.setSymbol(symbol);
		body.setExchange(exchange.intValue());
		body.setSecurityType(securityType.intValue());
		body.setSide(side.toString());
		body.setCashMargin(cashMargin.intValue());
		if (marginTradeType != null) {
			body.setMarginTradeType(marginTradeType.intValue());
		}
		body.setMarginPremiumUnit(marginPremiumUnit);
		body.setDelivType(delivType.intValue());
		if (fundType != null) {
			body.setFundType(fundType.toString());
		}
		body.setAccountType(accountType.intValue());
		body.setQty(qty);
		if (closePositionOrder != null) {
			body.setClosePositionOrder(closePositionOrder.intValue());
		}
		if (closePositions != null) {
			List<Positions> pl = new ArrayList<>();
			for (ClosePosition cp : closePositions) {
				Positions p = cp.toPositions();
				pl.add(p);
			}
			body.setClosePositions(pl);
		}
		body.setFrontOrderType(frontOrderType.intValue());
		body.setPrice(price);
		body.setExpireDay(expireDay);
		if (reverseLimitOrder != null) {
			RequestSendOrderReverseLimitOrder rlo = reverseLimitOrder.toRequestSendOrderReverseLimitOrder();
			body.setReverseLimitOrder(rlo);
		}
		return body;
	}

}
