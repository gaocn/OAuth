package govind.controller;

import com.fasterxml.jackson.annotation.JsonView;
import govind.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: gvind
 */
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

	@GetMapping("/user/{id:\\d+}")
	@JsonView(User.UserSimpleView.class)
	public User getInfo(@PathVariable(name = "id")String userid) {
		System.out.println("userid: " + userid);

		User user = new User();
		user.setUsername("Tom");
		return user;
	}

	@PostMapping("/user")
	@JsonView(User.UserDetailView.class)
	public User create(@RequestBody User user) {
		System.out.println("user: " +user);
		user.setUserid(1);
		return user;
	}

	@PutMapping("/user/{userid:\\d+}")
	public User update(@Valid @RequestBody(required = true) User user, BindingResult errors) {
		errors.getAllErrors().stream().forEach(err -> {
			FieldError error =  (FieldError) err;

			System.out.println("Filed["+ error.getField() +"]-->Error["+error.getDefaultMessage() +"]");
		});

		user.setUserid(2);
		return user;
	}

	@DeleteMapping("/user/{userid:\\d+}")
	public void delete(@PathVariable(name = "userid") String id) {
		System.out.println("deleted userid["+ id +"]");
	}
}
