package com.model2.mvc.service.purchase.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDao;

public class PurchaseServiceImpl implements PurchaseService {
	//Field
	private PurchaseDao purchaseDao;
	
	//Constructor
	public PurchaseServiceImpl() {
		purchaseDao = new PurchaseDao();
	}

	//Method
	//���Ÿ� ���� ����Ͻ��� ����
	public void addPurchase(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : addPurchase() ���� >>>>>");
		purchaseDao.insertPurchase(purchase);
	}

	//���� ���� ����ȸ�� ���� ����Ͻ��� ����
	public Purchase getPurchase(int tranNo) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : getPurchase() ���� >>>>>");
		return purchaseDao.findPurchase(tranNo);
	}
	
	//���� ��� ���θ� �ľ��ϱ� ���� ����Ͻ��� ����
	public Purchase getPurchase2(int ProdNo) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : getPurchase2() ���� >>>>>");
		return purchaseDao.findPurchase2(ProdNo);
	}

	//���� ��� ���⸦ ���� ����Ͻ��� ����
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : getPurchaseList() ���� >>>>>");
		return purchaseDao.getPurchaseList(search, buyerId);
	}
	
	//�Ǹ� ��� ���⸦ ���� ����Ͻ��� ����
	public Map<String, Object> getSaleList(Search search) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : getSaleList() ���� >>>>>");
		return purchaseDao.getSaleList(search);
	}
	
	//���� ���� ������ ���� ����Ͻ� ����
	public void updatePurcahse(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : updatePurcahse() ���� >>>>>");
		purchaseDao.updatePurchase(purchase);
	}

	//���� ���� �ڵ� ������ ���� ����Ͻ� ����
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseServiceImpl : updateTranCode() ���� >>>>>");
		purchaseDao.updateTranCode(purchase);
	}
	
	
	
	
	
}//end of class
