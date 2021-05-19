package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdatePurchaseViewAction extends Action {//�������� ������ ���� ȭ���û

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdatePurchaseViewAction : execute() ���� >>>>>");
		
		int tranNo = Integer.parseInt(request.getParameter("tranNo"));
		System.out.println("���� tranNo : " + tranNo);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Purchase purchase = purchaseService.getPurchase(tranNo);
		System.out.println("purchase ���ÿϷ� : " + purchase);
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("<<<<< UpdatePurchaseViewAction : execute() ���� >>>>>");
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
}
