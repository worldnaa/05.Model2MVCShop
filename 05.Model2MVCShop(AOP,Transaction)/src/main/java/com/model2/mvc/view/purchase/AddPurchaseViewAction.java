package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddPurchaseViewAction extends Action {//���Ÿ� ���� ȭ���û
	
	//1. ��ǰ�˻� -> ��ǰ�� -> ��ǰ����ȸ�� [����] ��ư Ŭ��
	//=> http://IP:��Ʈ��ȣ/addPurchaseView.do?prod_no=prodNo
	//2. AddPurchaseViewAction.java�� �̵�
	//3. execute() ���� �� addPurchaseView.jsp�� �̵�

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddPurchaseViewAction : execute() ���� >>>>>");
		
		//getProduct.jsp���� "prodNo"�� value�� �����ͼ� prodNo ������ ����
		int prodNo = Integer.parseInt(request.getParameter("prod_no"));
		System.out.println("���� prodNo : " + prodNo);
		
		//ProductServiceImpl �ν��Ͻ��� �����Ͽ� ��ǰ������ �������� getProduct() ����
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		System.out.println("<<<<< AddPurchaseViewAction : execute() ���� >>>>>");
		
		return "forward:/purchase/addPurchaseView.jsp";
		
	}
}
