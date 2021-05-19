package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class GetProductAction extends Action {//��ǰ�󼼺��� ��û

	//1. �ǸŻ�ǰ���� or ��ǰ�˻� �޴� Ŭ��
	//2. �ǸŻ�ǰ������ ��ǰ�� ==> http://192.168.0.96:8080/getProduct.do?prodNo=prodNo&menu=manage �̵�
	//3. ��ǰ�˻��� ��ǰ��    ==> http://192.168.0.96:8080/getProduct.do?prodNo=prodNo&menu=search �̵�
	//4. GetProductAction.java�� execute() ����
	//5. menu=manage �� ��� ==> UpdateProductViewAction.java �̵�
	//6. menu=search �� ��� ==> getProduct.jsp �̵�
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< GetProductAction : execute() ���� >>>>>");
		
		String history = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0) {
			for(int i=0; i<cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookie.getName().equals("history")) {
					history = cookie.getValue();
					history += "," + request.getParameter("prodNo");
				}
			}
		}
		
		//��Ű ����
		Cookie cookie = new Cookie("history", history);
		//�������� ��Ű�� ���������� ������� �ʰ�, ���������� ����� �� ����
		cookie.setMaxAge(-1);
		//response�� ������ ��Ű�� �߰�
		response.addCookie(cookie);
		System.out.println("����� ��Ű �̸� : " + cookie.getName());
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println("���� prodNo : " + prodNo);
		
		String menu = request.getParameter("menu");
		System.out.println("���� menu : " + menu);
		
		//menu�� ���� manage�� ���
		if(menu != null && menu.equals("manage")) {
			return "forward:/updateProductView.do?prodNo="+prodNo+"&menu="+menu;
		}
		
		ProductService service = new ProductServiceImpl();
		Product product = service.getProduct(prodNo);
		System.out.println("product ���ÿϷ� : " + product);
		
		request.setAttribute("product", product);
		
		System.out.println("<<<<< GetProductAction : execute() ���� >>>>>");
		
		return "forward:/product/getProduct.jsp";
		
	}
}
