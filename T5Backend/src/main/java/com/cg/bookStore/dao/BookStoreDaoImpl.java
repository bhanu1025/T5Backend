package com.cg.bookStore.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.cg.bookStore.entity.BookInformation;
import com.cg.bookStore.entity.CartInformation;
import com.cg.bookStore.entity.CustomerInformation;
import com.cg.bookStore.entity.OrderInformation;

@Repository
public class BookStoreDaoImpl implements BookStoreDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	public List<BookInformation> viewBooks() {
		TypedQuery<BookInformation> query = em.createQuery("from BookInformation", BookInformation.class);
		return query.getResultList();
	}

	@Override
	public BookInformation getBook(int bookId) {
		return em.find(BookInformation.class, bookId);
	}

	@Override
	public List<CartInformation> viewCartByCustomerId(int customerId) {
		String jpql = "from CartInformation where customerId=:custId and status='cart'";
		TypedQuery<CartInformation> query = em.createQuery(jpql, CartInformation.class);
		query.setParameter("custId", customerId);
		return query.getResultList();
	}

	@Override
	public String addBookToCart(CartInformation cart) {
		em.persist(cart);
		return "Book Added To Cart Successfully";
	}

	@Override
	public boolean removeCartItem(CartInformation cart) {
		em.remove(cart);
		return true;
	}

	@Override
	public CartInformation viewCartByCartId(int cartId) {
		return em.find(CartInformation.class, cartId);
	}

	@Override
	public boolean updateCartQuantity(CartInformation cart) {
		em.merge(cart);
		return true;
	}
	
	@Override
	public boolean updateCartStatus(CartInformation cart) {
		em.merge(cart);
		return true;
	}

	@Override
	public List<OrderInformation> viewOrderByCustomerId(int customerId) {
		String jpql = "from OrderInformation oi inner join fetch oi.customer c where c.customerId=:custid";
		TypedQuery<OrderInformation> query = em.createQuery(jpql, OrderInformation.class);
		query.setParameter("custid", customerId);
		return query.getResultList();
	}

	@Override
	public CustomerInformation viewCustomerById(int customerId) {
		return em.find(CustomerInformation.class, customerId);
	}

	@Override
	public List<CartInformation> viewOrderedCartByCustomerId(int customerId) {
		String jpql = "from CartInformation where customerId=:custId and status='order'";
		TypedQuery<CartInformation> query = em.createQuery(jpql, CartInformation.class);
		query.setParameter("custId", customerId);
		return query.getResultList();
	}

}
