package com.example.springtutorial.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.springtutorial.entity.User;
import com.example.springtutorial.repository.UserRepository;

@Service
public class UserService {
	//インジェクションのための変数宣言
	private final UserRepository userRepository;
	
	// 依存性の注入（DI）を行う（コンストラクタインジェクション）
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	//新規ユーザの登録メソッド
	public void createUser(String userName, String password, int roleId) {
		//ユーザ名の未入力チェック
		if (userName == null || userName.isEmpty()) {
			throw new IllegalArgumentException("ユーザー名を入力してください。");
		}
		
		//ユーザー名の重複チェック
		if (!userRepository.findByUserName(userName).isEmpty()) {
			throw new IllegalArgumentException("そのユーザー名は既に使用されています。");
		}
		
		//ユーザ登録用のエンティティを作成
		User user = new User();
		user.setUserName(userName);
		user.setPassword(password);
		user.setRoleId(roleId);
		
		//ユーザの登録
		userRepository.save(user);
		
	}
	
	//ユーザの一括取得メソッド
	public List<User> getAllUsers(){
		return userRepository.findAll();
	}

}
