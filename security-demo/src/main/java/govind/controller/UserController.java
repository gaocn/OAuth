package govind.controller;

import com.fasterxml.jackson.annotation.JsonView;
import govind.entity.User;
import govind.exception.UserNotExistException;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
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
@Slf4j
public class UserController {

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	@ResponseBody
	public List<User> query(User user, @PageableDefault(size=5, page = 0, sort="username,asc")Pageable pageable) {
		List<User>  users = new ArrayList<>();
		System.out.println("user: " +  user);
		System.out.println("pageable: " +  pageable);
		return users;
	}

	@ApiOperation(value = "获取单个用户详细信息")
	@GetMapping("/user/{id:\\d+}")
	@JsonView(User.UserSimpleView.class)
	public User getInfo(@ApiParam(value = "用户ID") @PathVariable(name = "id")String userid) {
		System.out.println("userid: " + userid);

//		throw new UserNotExistException(userid);
		User user = new User();
		user.setUsername("Tom");
		log.info("服务处理中......");
		return user;
	}

	@PostMapping("/user")
	@JsonView(User.UserDetailView.class)
	public User create(@Valid @RequestBody User user) {
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

	@GetMapping("/user/me")
	public Object getCredential() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
