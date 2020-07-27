package com.cg.bookStore.service;

import java.util.List;

import com.cg.bookStore.entity.BookInformation;
import com.cg.bookStore.entity.CartInformation;
import com.cg.bookStore.entity.CustomerInformation;
import com.cg.bookStore.entity.OrderInformation;
import com.cg.bookStore.exceptions.BookNotFoundException;
import com.cg.bookStore.exceptions.InvalidCustomerIdException;
import com.cg.bookStore.exceptions.InvalidQuantityException;
import com.cg.bookStore.exceptions.RecordAlreadyPresentException;
import com.cg.bookStore.exceptions.RecordNotFoundException;

public interface BookStoreService {

	public List<BookInformation> viewBooks() throws RecordNotFoundException;
	
	public List<CartInformation> viewCartByCustomerId(int customerId);
	
	public String addBookToCart(int bookId, int customerId, String status)  throws BookNotFoundException, InvalidQuantityException;

	public boolean removeCartItem(int cartId);

	public boolean clearCartByCustomerId(int customerId);

	public String updateCart(int cartId,int quantity) throws InvalidQuantityException ;
	
	public OrderInformation addOrder(OrderInformation order) throws RecordAlreadyPresentException;
	
	public OrderInformation viewOrderById(int id);
	
	public CustomerInformation viewCustomerById(int customerId)  throws InvalidCustomerIdException;
	
	public Iterable<OrderInformation> listAllOrder();
	
	public List<OrderInformation> viewOrderByCustomerId(int customerId) throws InvalidCustomerIdException;
	
	public void updateStatus(OrderInformation order);

	public List<CartInformation> viewOrderedCartByCustomerId(int customerId);

}
