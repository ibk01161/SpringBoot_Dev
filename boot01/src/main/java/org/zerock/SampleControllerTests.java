package org.zerock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@RunWith(SpringRunner.class)
@WebMvcTest(SampleControllerTests.class)
public class SampleControllerTests {
	
	@Autowired
	MockMvc mock;
	
	/*@Test
	public void testHello() throws Exception {
		mock.perform(get("/hello")).andExpect(content().string("Hello world"));
	}*/
	
	// 정상 응답 상태이고, 응답으로 전송되는결과를 보고 싶을 때
	@Test
	public void testHello() throws Exception {
		MvcResult result = mock.perform(get("/hello")).andExpect(status().isOk())
				.andExpect(content().string("Hello World")).andReturn();
		
		System.out.println(result.getResponse().getContentAsString());
	}

}
