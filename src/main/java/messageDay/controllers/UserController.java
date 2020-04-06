package messageDay.controllers;

import messageDay.entities.UserEntity;
import messageDay.service.UserService ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("")
	public String userList(Model model,
                           @RequestParam(value = "page", defaultValue = "1") int page,
                           @RequestParam(value = "size", defaultValue = "10") int size,
                           @RequestParam(name = "value", required = false) String value) {
		Page<UserEntity> all;
		PageRequest pageable = PageRequest.of(page - 1, size);

		if (value != null && !value.isEmpty()) {
			all = userService.findAllByFioIgnoreCase(pageable, value);
		} else {
			//all = userService.findAll(pageable);
			all = userService.findAll(pageable);
		}

		Integer roleId = userService.getAuthenticationUser().getRoleId();

		model.addAttribute("key", value);
		model.addAttribute("userPage", all);
		model.addAttribute("pageNumbers", getPageNumbers(all));
		model.addAttribute("roleId", roleId);


		return "users/list";
	}

	private List<Integer> getPageNumbers(Page<UserEntity> page) {
		List<Integer> pageNumbers = new ArrayList<>();

		if (page != null) {
			int totalPages = page.getTotalPages();
			if (totalPages > 0) {
				pageNumbers = IntStream.rangeClosed(1, totalPages)
						.boxed()
						.collect(Collectors.toList());

			}
		}

		return pageNumbers;
	}

	@GetMapping("/form")
	public String showUser(Model model, @RequestParam(value = "id", required = false) UserEntity user) {
		Integer roleId = userService.getAuthenticationUser().getRoleId();

		if (user == null) {
			user = new UserEntity();
		}
		model.addAttribute("user", user);
		model.addAttribute("roleId", roleId);


		return "users/form";
	}

	@PostMapping("/form")
	public String addNewUser(@ModelAttribute @Valid UserEntity user, BindingResult errors, SessionStatus status) {
		Optional<UserEntity> r = userService.findByActiveAndRoleId(1, 1);


		if (errors.hasErrors() && user.getRoleId() == 1 ) {
			return "users/form";
		}
		user.setActive(1);

		userService.saveUser(user);
		status.setComplete();


		return "redirect:";
	}

	@RequestMapping("/delete")
	public String deleteUser(@RequestParam("id") int id) {
//        userService.deleteById(id);
		UserEntity user = new UserEntity();
		user = userService.findById(id);
		user.setActive(0);
		userService.saveUser(user);
		return "redirect:";
	}
	@RequestMapping("/active")
	public String activeUser(@RequestParam("id") int id) {
//        userService.deleteById(id);
		UserEntity user = new UserEntity();
		user = userService.findById(id);
		user.setActive(1);
		userService.saveUser(user);
		return "redirect:";
	}
}
