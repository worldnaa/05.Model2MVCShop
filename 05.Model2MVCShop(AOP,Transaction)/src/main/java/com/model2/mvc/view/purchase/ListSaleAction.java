package com.model2.mvc.view.purchase;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListSaleAction extends Action {//�ǸŸ�� ��û(Adminȭ��)

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("<<<<< ListSaleAction : execute() ���� >>>>>");
		
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
		PurchaseService purchaseService = new PurchaseServiceImpl();
		Map<String, Object> map = purchaseService.getSaleList(search);
		System.out.println("map ���ÿϷ� : " + map);
		
		Page resultPage = new Page(currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage ���ÿϷ� : "+resultPage);
		
		//Model �� View ����
		request.setAttribute("search", search);
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		
		System.out.println("<<<<< ListSaleAction : execute() ���� >>>>>");
		
		return "forward:/purchase/listSale.jsp";
		
	}	
}
