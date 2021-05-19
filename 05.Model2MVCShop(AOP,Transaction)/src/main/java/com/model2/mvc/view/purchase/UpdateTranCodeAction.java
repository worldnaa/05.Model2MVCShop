package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class UpdateTranCodeAction extends Action {//���Ż����ڵ� ������û (����: tranNo)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateTranCodeAction : execute() ���� >>>>>");
		
		Purchase purchase = new Purchase();
		
		//listPurchase.jsp���� ���ǵ��� ��ư Ŭ�� �� 'tranNo' ������
		purchase.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		System.out.println("���� tranNo : " + purchase.getTranNo());	
		
		//listPurchase.jsp���� ���ǵ��� ��ư Ŭ�� �� 'tranCode = 3' ������ ==> ��ۿϷ� ����
		purchase.setTranCode(request.getParameter("tranCode")); 
		System.out.println("���� tranCode : " + purchase.getTranCode());
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchase);
		
		System.out.println("<<<<< UpdateTranCodeAction : execute() ���� >>>>>");
		
		return "redirect:/listPurchase.do?menu=manage&page="+request.getParameter("page");
	}

}
