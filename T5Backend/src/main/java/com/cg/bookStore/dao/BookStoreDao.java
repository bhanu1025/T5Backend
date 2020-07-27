package com.cg.bookStore.dao;

import java.util.List;

import com.cg.bookStore.entity.BookInformation;
import com.cg.bookStore.entity.CartInformation;
import com.cg.bookStore.entity.CustomerInformation;
import com.cg.bookStore.entity.OrderInformation;

public interface BookStoreDao {
	
	public List<CartInformation> viewCartByCustomerId(int customerId);
	
	public List<BookInformation> viewBooks();
	
	public BookInformation getBook(int bookId);
	
	public String addBookToCart(CartInformation cart);

	public boolean removeCartItem(CartInformation cart);

	public CartInformation viewCartByCartId(int cartId);
	
	public CustomerInformation viewCustomerById(int customerId);

	public boolean updateCartQuantity(CartInformation cart);

	public List<OrderInformation> viewOrderByCustomerId(int customerId);

	boolean updateCartStatus(CartInformation cart);

	public List<CartInformation> viewOrderedCartByCustomerId(int customerId);

}
