package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {//��ǰ�������� ��û

	//1. �ǸŻ�ǰ������ ��ǰ���� Ŭ���ؼ� �� ��� (menu=manage)
	//2. UpdateProductViewAction.java�� execute() ����
	//3. updateProductView.jsp �̵�
	//4. [����] ��ư Ŭ�� �� ==> UpdateProductAction.java �̵�
	//5. GetProductAction.java �̵�
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< UpdateProductAction : execute() ���� >>>>>");
				
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("���� prodNo : " + prodNo);
		
		String menu = request.getParameter("menu");
		System.out.println("���� menu : " + menu);
		
		Product product = new Product();
		product.setProdNo(prodNo);
		product.setProdName(request.getParameter("prodName"));
		product.setProdDetail(request.getParameter("prodDetail"));
		product.setManuDate(request.getParameter("manuDate"));
		product.setPrice(Integer.parseInt(request.getParameter("price")));
		product.setFileName(request.getParameter("fileName"));
		System.out.println("product ���ÿϷ� : " + product);
		
		ProductService ProductService = new ProductServiceImpl();
		ProductService.updateProduct(product);
		
		System.out.println("<<<<< UpdateProductAction : execute() ���� >>>>>");
		
		return "redirect:/getProduct.do?prodNo="+prodNo+"&menu="+menu;
		
	}
}
