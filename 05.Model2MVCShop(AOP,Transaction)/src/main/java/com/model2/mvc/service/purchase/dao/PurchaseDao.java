package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class PurchaseDao {
	//Constructor
	public PurchaseDao() {
	}
	
	//Method
	//���Ÿ� ���� DBMS�� ����
	public void insertPurchase(Purchase purchase) throws Exception {	
		System.out.println("<<<<< PurchaseDAO : insertPurchase() ���� >>>>>");
		System.out.println("���� purchase : " + purchase);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "INSERT INTO transaction VALUES (seq_product_prod_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		pStmt.setString(2, purchase.getBuyer().getUserId());
		pStmt.setString(3, purchase.getPaymentOption());
		pStmt.setString(4, purchase.getReceiverName());
		pStmt.setString(5, purchase.getReceiverPhone());
		pStmt.setString(6, purchase.getDivyAddr());
		pStmt.setString(7, purchase.getDivyRequest());
		pStmt.setString(8, purchase.getTranCode());
		pStmt.setString(9, purchase.getDivyDate());
		pStmt.executeUpdate();
		System.out.println("insert �Ϸ� : " + sql);
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : insertProduct() ���� >>>>>");
	}
	
	
	//�������� �� ��ȸ�� ���� DBMS�� ���� ==> tranNo
	public Purchase findPurchase(int tranNo) throws Exception {
		System.out.println("<<<<< PurchaseDAO : findPurchase() ���� >>>>>");
		System.out.println("���� tranNo : " + tranNo);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM transaction WHERE tran_no=? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, tranNo);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql ���ۿϷ� : " + sql);
		
		Purchase purchase = null;
		Product product = null;
		User user = null;
		
		while (rs.next()) {	
			purchase = new Purchase();
			product = new Product();
			user = new User();
			
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			
			product.setProdNo(rs.getInt("prod_no"));
			purchase.setPurchaseProd(product);
			
			user.setUserId(rs.getString("buyer_id"));
			purchase.setBuyer(user);
			
			System.out.println("purchase ���ÿϷ� : " + purchase);
		}
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : findPurchase() ���� >>>>>");
		
		return purchase; 
	}
	
	
	//�������� �� ��ȸ�� ���� DBMS�� ���� ==> prodNo
	public Purchase findPurchase2(int prodNo) throws Exception {
		System.out.println("<<<<< PurchaseDAO : findPurchase2() ���� >>>>>");
		System.out.println("���� prodNo : " + prodNo);
			
		Connection con = DBUtil.getConnection();
			
		String sql = "SELECT * FROM transaction WHERE prod_no=? ";
			
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setInt(1, prodNo);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql ���ۿϷ� : " + sql);	
		
		Purchase purchase = null;
		while (rs.next()) {
			purchase = new Purchase();
			
			purchase.setPaymentOption(rs.getString("payment_option"));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setOrderDate(rs.getDate("order_data"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setTranCode(rs.getString("tran_status_code"));		
		
			System.out.println("purchase ���ÿϷ� : " + purchase);
			System.out.println("findPurchase2 tran_no : " + purchase.getTranNo());
			System.out.println("findPurchase2 tran_status_code : " + purchase.getTranCode());
		}		
		rs.close();
		pStmt.close();
		con.close();
			
		System.out.println("<<<<< PurchaseDAO : findPurchase2() ���� >>>>>");
		
		return purchase; 
	}
		
	
	//���Ÿ�� ���⸦ ���� DBMS�� ����
	public Map<String,Object> getPurchaseList(Search search, String buyerId) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() ���� >>>>>");
		System.out.println("���� search : " + search);
		System.out.println("���� buyerId : " + buyerId);
		
		String sql = "SELECT * FROM transaction WHERE buyer_id='" + buyerId +"'";

		//getTotalCount() �޼ҵ� ���� (this. ��������)
		int totalCount = this.getTotalCount(sql);
		System.out.println("totalCount : " + totalCount);
				
		//CurrentPage �Խù��� �޵��� Query �ٽñ���
		//makeCurrentPageSql() �޼ҵ� ���� (this. ��������)
		sql = this.makeCurrentPageSql(sql, search);
		
		Connection con = DBUtil.getConnection();
		
		Statement stmt = con.createStatement();
		
		ResultSet rs = stmt.executeQuery(sql);
		System.out.println("sql ���ۿϷ� : " + sql);
		
		Map<String,Object> map = new HashMap<String,Object>();
		List<Purchase> list = new ArrayList<Purchase>();
		
		Purchase purchase = null;
		UserService service = new UserServiceImpl();
		
		while (rs.next()) {
			purchase = new Purchase();
			
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setBuyer(service.getUser(rs.getString("buyer_id")));
				
			list.add(purchase);
			
//			if (!rs.next()) {
//				break;
//			}
			System.out.println("purchase ���ÿϷ� : " + purchase);
		}
		map.put("totalCount", totalCount);
		map.put("list", list);
		System.out.println("map�� totalCount �߰� : " + map);
		System.out.println("map�� list �߰� : " + map);
		
		System.out.println("list.size() : " + list.size()); 
		System.out.println("map.size() : " + map.size());
		
		rs.close();
		stmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getPurchaseList() ���� >>>>>");
		
		return map;
	}
	
	
	//�ǸŸ�� ���⸦ ���� DBMS�� ����
	public Map<String,Object> getSaleList(Search search) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getSaleList() ���� >>>>>");
		System.out.println("���� search : " + search);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "SELECT * FROM product ";
		
		//SearchCondition�� ���� ���� ���
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0")) {
				sql += " WHERE prod_no LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1")) {
				sql += " WHERE prod_name LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("2")) {
				sql += " WHERE price LIKE '%" + search.getSearchKeyword() + "%'";
			}
		}
		sql += " ORDER BY prod_no";

		//getTotalCount() �޼ҵ� ���� (this. ��������)
		int totalCount = this.getTotalCount(sql);
		System.out.println("totalCount : " + totalCount);
				
		//CurrentPage �Խù��� �޵��� Query �ٽñ���
		//makeCurrentPageSql() �޼ҵ� ���� (this. ��������)
		sql = this.makeCurrentPageSql(sql, search);
				
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		System.out.println("sql ���ۿϷ� : " + sql);

		//HashMap<String,Object> , ArrayList<Product> �ν��Ͻ� ����
		Map<String,Object> map = new HashMap<String,Object>();
		List<Product> list = new ArrayList<Product>();
				
		PurchaseService service = new PurchaseServiceImpl();
		
		while (rs.next()) {
			Product product = new Product();
			
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			product.setProdDetail(rs.getString("prod_detail"));
			product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setFileName(rs.getString("image_file"));
			product.setRegDate(rs.getDate("reg_date"));
				
			if(findPurchase2(product.getProdNo()) != null) {
				product.setProTranCode(findPurchase2(product.getProdNo()).getTranCode());
			}
				
			list.add(product);
			
//			if (!rs.next()) {
//				break;
//			}				
			System.out.println("product ���ÿϷ� : " + product);
		}
		
		map.put("totalCount", new Integer(totalCount));
		System.out.println("map�� totalCount �߰� : " + map);
		
		map.put("list", list);
		System.out.println("map�� list �߰� : " + map);
		
		System.out.println("list.size() : " + list.size()); 
		System.out.println("map.size() : " + map.size()); 
		
		rs.close();
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : getSaleList() ���� >>>>>");
		
		return map;
	}
	
	
	//�������� ������ ���� DBMS�� ����
	public void updatePurchase(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseDAO : updatePurchase() ���� >>>>>");
		System.out.println("���� purchase : " + purchase);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction "
					+ "SET payment_option=?, receiver_name=?, receiver_phone=?,"
					+ "demailaddr=?, dlvy_request=?, dlvy_date=? "
					+ "WHERE tran_no=? ";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchase.getPaymentOption());
		pStmt.setString(2, purchase.getReceiverName());
		pStmt.setString(3, purchase.getReceiverPhone());
		pStmt.setString(4, purchase.getDivyAddr());
		pStmt.setString(5, purchase.getDivyRequest());
		pStmt.setString(6, purchase.getDivyDate());
		pStmt.setInt(7, purchase.getTranNo());
		pStmt.executeUpdate();
		System.out.println("update �Ϸ� : " + sql);
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : updatePurchase() ���� >>>>>");
	}
	
	
	//���� �����ڵ� ������ ���� DBMS�� ����
	public void updateTranCode(Purchase purchase) throws Exception {
		System.out.println("<<<<< PurchaseDAO : updateTranCode() ���� >>>>>");
		System.out.println("���� purchase : " + purchase);
		
		Connection con = DBUtil.getConnection();
		
		String sql = "UPDATE transaction SET tran_status_code=? WHERE tran_no=?";
		
		PreparedStatement pStmt = con.prepareStatement(sql);
		pStmt.setString(1, purchase.getTranCode());
		pStmt.setInt(2, purchase.getTranNo());
		pStmt.executeUpdate();
		System.out.println("update �Ϸ� : " + sql);
		System.out.println("update�� tranCode : " + purchase.getTranCode());
		System.out.println("update�� tranNo : " + purchase.getTranNo());
		
		pStmt.close();
		con.close();
		
		System.out.println("<<<<< PurchaseDAO : updateTranCode() ���� >>>>>");
	}
	
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		System.out.println("<<<<< PurchaseDAO : getTotalCount() ���� >>>>>");
		
		sql = "SELECT COUNT(*) FROM ( " +sql+ " ) countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		System.out.println("<<<<< PurchaseDAO : getTotalCount() ���� >>>>>");
		
		return totalCount;
	}
	
	
	// �Խ��� currentPage Row ��  return
	private String makeCurrentPageSql(String sql, Search search){
		System.out.println("<<<<< PurchaseDAO : makeCurrentPageSql() ���� >>>>>");
		
		sql = 	"SELECT * "+ 
				"FROM ( SELECT inner_table. * ,  ROWNUM AS row_seq " +
				"FROM (	"+sql+" ) inner_table "+
				"WHERE ROWNUM <= "+search.getCurrentPage()*search.getPageSize()+" ) " +
				"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +
				" AND "+search.getCurrentPage()*search.getPageSize();
	
		System.out.println("make SQL��? "+ sql);//�����	
		
		System.out.println("<<<<< PurchaseDAO : makeCurrentPageSql() ���� >>>>>");
		
		return sql;
	}

}//end of class
