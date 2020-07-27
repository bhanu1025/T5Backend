package com.cg.bookStore.test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.cg.bookStore.web.CartController;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CartController.class)
public class ViewReviewTest {
	
	@Autowired
	private MockMvc mockMvc;

}
