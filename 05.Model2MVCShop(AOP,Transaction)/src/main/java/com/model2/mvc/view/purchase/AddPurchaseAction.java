package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class AddPurchaseAction extends Action {//���� ��û

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddPurchaseAction : execute() ���� >>>>>");
		
		//addPurchaseView.jsp���� ������ ���� DB�� ���� �����ϱ� ���� Purchase �ν��Ͻ��� �����Ͽ�, ������ ���� ����
		Purchase purchase = new Purchase();
		purchase.setPaymentOption(request.getParameter("paymentOption")); //���Ź��
		purchase.setReceiverName(request.getParameter("receiverName"));   //�������̸�
		purchase.setReceiverPhone(request.getParameter("receiverPhone")); //�����ڿ���ó
		purchase.setDivyAddr(request.getParameter("receiverAddr"));       //�������ּ�
		purchase.setDivyRequest(request.getParameter("receiverRequest")); //���ſ�û����
		purchase.setDivyDate(request.getParameter("receiverDate"));       //����������
		purchase.setTranCode("1");
		
		//2. Product�� ���� Purchase�� ����
		//���1)
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct((Integer.parseInt(request.getParameter("prodNo"))));
		purchase.setPurchaseProd(product);                                //��ǰ����
		//���2)
//		Product product = new Product();
//		product.setProdNo(Integer.parseInt(request.getParameter("prodNo"))); 
//		purchase.setPurchaseProd(product);
		
		//3. User�� ���� Purchase�� ����
		//���1)
		HttpSession session = request.getSession();
		purchase.setBuyer((User)session.getAttribute("user"));		      //�����ھ��̵�
		//���2)
//		User user = new User();
//		user.setUserId(request.getParameter("userId"));
//		purchase.setBuyer(user);	
		
		System.out.println("purchase ���ÿϷ� : " + purchase);
		
		//purchaseVO�� DB�� �����ϱ� ���� PurchaseServiceImpl �ν��Ͻ� ����
		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.addPurchase(purchase);
		
		request.setAttribute("purchase", purchase);
		
		System.out.println("<<<<< AddPurchaseAction : execute() ���� >>>>>");
		
		return "forward:/purchase/addPurchase.jsp";		
	}
}
