package com.cg.bookStore.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cg.bookStore.dao.BookStoreDao;
import com.cg.bookStore.dao.OrderDao;
import com.cg.bookStore.entity.BookInformation;
import com.cg.bookStore.entity.CartInformation;
import com.cg.bookStore.entity.CustomerInformation;
import com.cg.bookStore.entity.OrderInformation;
import com.cg.bookStore.exceptions.BookNotFoundException;
import com.cg.bookStore.exceptions.InvalidCustomerIdException;
import com.cg.bookStore.exceptions.InvalidQuantityException;
import com.cg.bookStore.exceptions.RecordAlreadyPresentException;
import com.cg.bookStore.exceptions.RecordNotFoundException;
import com.cg.bookStore.util.BookStoreConstants;

@Service("bookstore")
@Transactional
public class BookStoreServiceImpl implements BookStoreService {

	@Autowired
	private BookStoreDao dao;

	@Autowired
	private OrderDao orderDao;

	@Override
	public String addBookToCart(int bookId, int customerId, String status)
			throws BookNotFoundException, InvalidQuantityException {
		BookInformation book = dao.getBook(bookId);
		List<CartInformation> cartList = dao.viewCartByCustomerId(customerId);
		int quantity = 1;
		CartInformation cartInfo = new CartInformation();

		if (book == null) {
			throw new BookNotFoundException(BookStoreConstants.BOOK_ID_EXCEPTION);
		}

		if (cartList.isEmpty()) {
			float subTotal = book.getPrice();
			cartInfo.setBook(book);
			cartInfo.setQuantity(quantity);
			cartInfo.setSubTotal(subTotal);
			cartInfo.setCustomerId(customerId);
			cartInfo.setStatus(status);
			return dao.addBookToCart(cartInfo);
		} else {

			for (CartInformation cart1 : cartList) {
				if (cart1.getBook().getBookId() == bookId) {
					int cartId = cart1.getCartId();
					int quan = cart1.getQuantity();
					int updatedQuantity = quan + 1;
					updateCart(cartId, updatedQuantity);
					return dao.addBookToCart(cart1);
				}

			}

			float subTotal = book.getPrice();
			cartInfo.setBook(book);
			cartInfo.setQuantity(quantity);
			cartInfo.setSubTotal(subTotal);
			cartInfo.setCustomerId(customerId);
			cartInfo.setStatus(status);
			return dao.addBookToCart(cartInfo);
		}
	}

	@Override
	public List<BookInformation> viewBooks() throws RecordNotFoundException {

		List<BookInformation> blist = dao.viewBooks();
		if (blist.isEmpty())
			throw new RecordNotFoundException();
		return blist;
	}

	@Override
	public List<CartInformation> viewCartByCustomerId(int customerId) {
		List<CartInformation> reviewList = dao.viewCartByCustomerId(customerId);
		return reviewList;
	}

	@Override
	public boolean removeCartItem(int cartId) {
		CartInformation cart = dao.viewCartByCartId(cartId);
		return dao.removeCartItem(cart);
	}

	@Override
	public boolean clearCartByCustomerId(int customerId) {
		List<CartInformation> carts = dao.viewCartByCustomerId(customerId);
		int i = 0;
		while (i < carts.size()) {
			dao.removeCartItem(carts.get(i));
			i++;
		}
		return true;
	}

	public String updateCart(int cartId, int quantity) throws InvalidQuantityException {
		CartInformation cart = dao.viewCartByCartId(cartId);
		if (quantity < 0) {
			throw new InvalidQuantityException();
		} else {
			float updatedSubtotal = quantity * cart.getBook().getPrice();
			cart.setQuantity(quantity);
			cart.setSubTotal(updatedSubtotal);
			dao.updateCartQuantity(cart);
		}
		return "Cart Updated";

	}

	@Override
	public OrderInformation addOrder(OrderInformation order) throws RecordAlreadyPresentException {
		Optional<OrderInformation> newOrder = orderDao.findById(order.getOrderId());
		if (newOrder.isPresent()) {
			throw new RecordAlreadyPresentException();
		} else {
			orderDao.save(order);
			updateStatus(order);
			return order;
		}
	}

	@Override
	public void updateStatus(OrderInformation order) {
		List<CartInformation> cartList=order.getCart();
		for(CartInformation c: cartList) {
			c.setStatus("order");
			dao.updateCartStatus(c);
		}
	}
	
	@Override
	public OrderInformation viewOrderById(int id) {
		Optional<OrderInformation> order = orderDao.findById(id);
		if (!order.isPresent()) {
			throw new RecordNotFoundException();
		} else
			return order.get();
	}

	@Override
	public Iterable<OrderInformation> listAllOrder() {
		Iterable<OrderInformation> list = orderDao.findAll();
		if (list == null) {
			throw new RecordNotFoundException();
		} else
			return list;
	}

	@Override
	public List<OrderInformation> viewOrderByCustomerId(int customerId) throws InvalidCustomerIdException {
		List<OrderInformation> list = dao.viewOrderByCustomerId(customerId);
		if (list.isEmpty()) {
			throw new InvalidCustomerIdException(BookStoreConstants.CUSTOMER_ID_EXCEPTION);
		} else
			return list;
	}

	@Override
	public CustomerInformation viewCustomerById(int customerId)  throws InvalidCustomerIdException{
		return dao.viewCustomerById(customerId);
	}

	@Override
	public List<CartInformation> viewOrderedCartByCustomerId(int customerId) {
		List<CartInformation> reviewList = dao.viewOrderedCartByCustomerId(customerId);
		return reviewList;
	}

}
