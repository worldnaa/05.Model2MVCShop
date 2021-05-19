package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseAction extends Action {//�������� ���� ��û

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdatePurchaseAction : execute() ���� >>>>>");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("���� tranNo : " + tranNo);
		
		Purchase purchase = new Purchase();
		purchase.setTranNo(tranNo);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		System.out.println("purchase ���ÿϷ� : " + purchase);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updatePurcahse(purchase);
			
		System.out.println("<<<<< UpdatePurchaseAction : execute() ���� >>>>>");
		
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}
}
