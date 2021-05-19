package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class AddProductAction extends Action {//��ǰ��� ��û

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< AddProductAction : execute() ���� >>>>>");
		
		Product product = new Product();
		product.setProdName(request.getParameter("prodName"));                    //��ǰ��
		product.setProdDetail(request.getParameter("prodDetail"));                //��ǰ������
		product.setManuDate(request.getParameter("manuDate").replaceAll("-", ""));//��������
		product.setPrice(Integer.parseInt(request.getParameter("price")));        //����		
		product.setFileName(request.getParameter("fileName"));                    //��ǰ�̹���
		System.out.println("product ���ÿϷ� : " + product);
		
		ProductService service = new ProductServiceImpl();
		service.addProduct(product);
		
		//���1
		request.setAttribute("product", product);
		
		//���2
//		HttpSession session = request.getSession(true);		
//		session.setAttribute("productVO",productVO);
		
		System.out.println("<<<<< AddProductAction : execute() ���� >>>>>");
		
		return "forward:/product/addProduct.jsp";
	
	}
}
