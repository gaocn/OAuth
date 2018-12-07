package govind.controller;

import govind.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public List<User> query(User user, @PageableDefault(size=5, page = 0, sort="username,asc")Pageable pageable) {
		List<User>  users = new ArrayList<>();
		System.out.println("user: " +  user);
		System.out.println("pageable: " +  pageable);
		return users;
	}
}
