package com.model2.mvc.view.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class ListProductAction extends Action {//��ǰ�����ȸ ��û

	//1. �ǸŻ�ǰ���� or ��ǰ�˻� �޴� Ŭ��
	//2. �ǸŻ�ǰ���� ==> http://192.168.0.96:8080/listProduct.do?menu=manage �̵�
	//3. ��ǰ�˻�    ==> http://192.168.0.96:8080/listProduct.do?menu=search �̵�
	//4. ListProductAction.java�� execute() ����
	//5. listProduct.jsp �̵�
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListProductAction : execute() ���� >>>>>");
		
		Search search = new Search();
		
		int currentPage = 1;

		if(request.getParameter("currentPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
			System.out.println("currentPage : " + currentPage);
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));

		System.out.println("searchCondition : "+request.getParameter("searchCondition"));
		System.out.println("searchKeyword : "+request.getParameter("searchKeyword"));
		
		//web.xml  meta-data �� ���� ��� ���� 
		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));		
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));		
		search.setPageSize(pageSize);
		System.out.println("search ���ÿϷ� : " + search);
		
		//Business logic ����
		ProductService productService = new ProductServiceImpl();
		Map<String,Object> map = productService.getProductList(search);
		System.out.println("map ���ÿϷ� : " + map);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage ���ÿϷ� : "+resultPage);
		
		String menu = request.getParameter("menu");
		System.out.println("menu ���ÿϷ� : " + menu);

		//Model �� View ����
		request.setAttribute("search", search);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("menu", menu);
		
		System.out.println("<<<<< ListProductAction : execute() ���� >>>>>");
		
		return "forward:/product/listProduct.jsp";
		
	}
}
