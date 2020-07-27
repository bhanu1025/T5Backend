package com.cg.bookStore.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cg.bookStore.entity.BookInformation;
import com.cg.bookStore.entity.CartInformation;
import com.cg.bookStore.exceptions.BookNotFoundException;
import com.cg.bookStore.exceptions.InvalidQuantityException;
import com.cg.bookStore.exceptions.RecordNotFoundException;
import com.cg.bookStore.service.BookStoreService;

@CrossOrigin("*")
@RestController
public class CartController {

	@Autowired
	public BookStoreService service;

	@CrossOrigin
	@GetMapping("/viewallbooks")
	public List<BookInformation> viewdDiagnosisCentre() throws RecordNotFoundException {
		return service.viewBooks();
	}

	@CrossOrigin
	@GetMapping("/viewcartbycustomerid/{customerid}")
	public List<CartInformation> viewCartByCustomerId(@PathVariable("customerid") int customerId,
			HttpServletRequest request) {
		return service.viewCartByCustomerId(customerId);
	}
	
	@CrossOrigin
	@GetMapping("/viewOrderedCartByCustomerId/{customerid}")
	public List<CartInformation> viewOrderedCartByCustomerId(@PathVariable("customerid") int customerId,
			HttpServletRequest request) {
		return service.viewOrderedCartByCustomerId(customerId);
	}

	@CrossOrigin
	@PostMapping("/addbooktocart/{bookid}/{customerid}/{status}")
	public String addBookToCart(@PathVariable("bookid") int bookId, @PathVariable("customerid") int customerId,
			@PathVariable("status") String status) throws BookNotFoundException, InvalidQuantityException {
		return service.addBookToCart(bookId, customerId, status);
	}

	@CrossOrigin
	@DeleteMapping("/removecartitem/{cartid}")
	public boolean removeCartItem(@PathVariable("cartid") int cartId, HttpServletRequest request) {
		return service.removeCartItem(cartId);
	}

	@CrossOrigin
	@DeleteMapping("/clearcartbycustomerid/{customerid}")
	public boolean clearCartByCustomerId(@PathVariable("customerid") int customerId, HttpServletRequest request) {
		return service.clearCartByCustomerId(customerId);
	}

	@CrossOrigin
	@PostMapping("/update/{cartId}/{newQuantity}")
	public String updateCart(@PathVariable("cartId") int cartId, @PathVariable("newQuantity") int newQuantity)
			throws InvalidQuantityException {
		return service.updateCart(cartId, newQuantity);
	}

}
