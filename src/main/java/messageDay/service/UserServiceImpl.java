package messageDay.service;

import messageDay.entities.RoleEntity ;
import messageDay.entities.UserEntity;
import messageDay.repository.RoleRepository ;
import messageDay.repository.UserRepository ;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("userService")
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public UserEntity findUserByLogin(String login) {
		return userRepository.findByLogin(login);
	}

	@Override
	public List<UserEntity> findAllUserByFIOEquals(String name, String surname) {
		return userRepository.findAllByNameAndSurnameEquals(name, surname);
	}


	@Override
	public void saveSingUpUser(UserEntity user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(1);
		RoleEntity userRole = roleRepository.findByRole("admin");
//        RoleEntity userRole = roleRepository.findById(user.getRoleId()).orElse(roleRepository.findByRole("user"));
		user.setRole(userRole);
		userRepository.save(user);
	}

	@Override
	public Optional<UserEntity> findByActiveAndRoleId(int i, int i1) {
		return userRepository.findByActiveAndRoleId(i,i1);
	}

	@Override
	public void saveUser(UserEntity user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        user.setActive(1);
		RoleEntity userRole = roleRepository.findById(user.getRoleId()).orElse(roleRepository.findByRole("user"));
		user.setRole(userRole);
		userRepository.save(user);
	}


	@Override
	public UserEntity getAuthenticationUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return findUserByLogin(auth.getName());
	}

	@Override
	public Page<UserEntity> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@Override
	public Page<UserEntity> findAllByActive(Pageable pageable, int active) {
		return userRepository.findAllByActive(pageable, active);
	}

	@Override
	public Page<UserEntity> findAllByFioIgnoreCase(Pageable pageable, String name) {
		return userRepository.findAllByFirstnameContainingIgnoreCaseOrLastnameContainingIgnoreCaseOrderByRoleId(pageable, name, name);
	}

	@Override
	public void deleteById(int id) {
		userRepository.deleteById(id);
	}

	@Override
	public UserEntity findById(int id) {
		return userRepository.findById(id);

	}


}
