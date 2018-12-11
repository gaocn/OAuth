package govind;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	@Autowired
	private WebApplicationContext wac;
	private MockMvc mockMvc;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}

	@Test
	public void whenWuerySuccess() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.param("page","2")
				.param("sort","passwd,desc")
				.param("username","Govind")
				.param("passwd", "1234QA@#ED"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
	}

	@Test
	public void testGetInfo() throws Exception {
		String result  = mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
		).andExpect(MockMvcResultMatchers.status().isOk())
		.andReturn().getResponse().getContentAsString();

		System.out.println(result);
	}

	@Test
	public void testGetInfo2() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
		.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}


	@Test
	public void testCreateUser() throws Exception {

		Date date = new Date();
		String content = "{\"username\":\"Govind\",\"passwd\":\"12WD#@Cds^%\",\"birthday\":"+ new Date().getTime() +"}";

		String result = mockMvc.perform(MockMvcRequestBuilders.post("/user")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.userid").value(1))
		.andReturn().getResponse().getContentAsString();

		System.out.println(result);
	}

	@Test
	public void testUpdate() throws Exception {
		String content = "{\"username\":\"\",\"passwd\":\"12WDasdv\",\"birthday\":"+ new Date().getTime() +"}";
		String result = mockMvc.perform(
				MockMvcRequestBuilders.put("/user/1")
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(content)
		)
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.userid").value(2))
		.andReturn().getResponse().getContentAsString();
		System.out.println("response:  "  + result);
	}

	@Test
	public void  testDeleteUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/user/2")
		.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

























}
