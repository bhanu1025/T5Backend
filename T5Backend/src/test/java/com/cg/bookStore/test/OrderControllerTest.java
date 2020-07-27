package com.cg.bookStore.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.cg.bookStore.entity.CartInformation;
import com.cg.bookStore.entity.CustomerInformation;
import com.cg.bookStore.entity.OrderInformation;
import com.cg.bookStore.service.BookStoreService;
import com.cg.bookStore.web.OrderController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)			
@WebMvcTest(value = OrderController.class)
public class OrderControllerTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	BookStoreService service;
	
	@Test
	public void testAddOrder() throws Exception {
		 OrderInformation mockOrder= new OrderInformation();
		 List<CartInformation> list = new ArrayList<CartInformation>();
		 list.add(new CartInformation());
		 list.add(new CartInformation());
		 CustomerInformation custInfo= new CustomerInformation();
		 custInfo.setCustomerId(1);
		 mockOrder.setCustomer(custInfo);
		 mockOrder.setPaymentMethod("COD");
		 mockOrder.setCartId(list);
		 mockOrder.setQuantity(2);
		 mockOrder.setTotalPrice(300);
		 mockOrder.setOrderStatus("Shipped");
	     mockOrder.setShippingAddress("M-266");
	     String inputInJson = this.mapToJson(mockOrder);
	     String uri= "/addOrder";
	     
	     Mockito.when(service.addOrder(Mockito.any(OrderInformation.class)))	//return mockOrder when addOrder() is called
	     .thenReturn(mockOrder);
	   
	     RequestBuilder reqBuilder= MockMvcRequestBuilders
	    		 .post(uri)
	    		 .accept(MediaType.APPLICATION_JSON).content(inputInJson)			//accepts JSON
	    		 .contentType(MediaType.APPLICATION_JSON);							//returns JSON
	     
	     MvcResult result = mockMvc.perform(reqBuilder).andReturn();
	     MockHttpServletResponse response = result.getResponse();
	     
	     String outputInJson = response.getContentAsString();
	     assertThat(outputInJson).isEqualTo(inputInJson);
	     assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	@Test
	public void testSearchOrderById() throws Exception {
		OrderInformation mockOrder= new OrderInformation();
		 int id=1;
		 CustomerInformation custInfo= new CustomerInformation();
		 custInfo.setCustomerId(1);
		 mockOrder.setCustomer(custInfo);
		 mockOrder.setPaymentMethod("COD");
		 mockOrder.setQuantity(2);
		 mockOrder.setTotalPrice(300);
		 mockOrder.setOrderStatus("Shipped");
	     mockOrder.setShippingAddress("M-266");
		String inputInJson = this.mapToJson(mockOrder);
		String uri= "/searchOrder/"+id;
	     
	     Mockito.when(service.viewOrderById(Mockito.anyInt())).thenReturn(mockOrder);
	     
	     RequestBuilder reqBuilder= MockMvcRequestBuilders
	    		 .get(uri)
	    		 .accept(MediaType.APPLICATION_JSON).content(inputInJson)
	    		 .contentType(MediaType.APPLICATION_JSON);
	     
	     MvcResult result = mockMvc.perform(reqBuilder).andReturn();
	     MockHttpServletResponse response = result.getResponse();
	     
	     String outputInJson = response.getContentAsString();
	     assertThat(outputInJson).isEqualTo(inputInJson);
	     assertEquals(HttpStatus.OK.value(), response.getStatus());
	}
	
	

	@Test
	public void neagtiveTestSearchOrderById() throws Exception {
		String uri= "/searchOrder/1";
	     
	     Mockito.when(service.viewOrderById(Mockito.anyInt())).thenReturn(null);
	     
	     RequestBuilder reqBuilder= MockMvcRequestBuilders
	    		 .get(uri)
	    		 .contentType(MediaType.APPLICATION_JSON);
	     
	     MvcResult result = mockMvc.perform(reqBuilder).andReturn();
	     MockHttpServletResponse response = result.getResponse();
	    
	     assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}
	
	private String mapToJson(Object obj) throws JsonProcessingException {
	      ObjectMapper objectMapper = new ObjectMapper();
	      return objectMapper.writeValueAsString(obj);
	}
	
	
}