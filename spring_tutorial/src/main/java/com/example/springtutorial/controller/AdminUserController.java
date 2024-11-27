package com.example.springtutorial.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.springtutorial.entity.User;
import com.example.springtutorial.service.UserService;

@Controller
public class AdminUserController {
	private final UserService userService;
	
	//ユーザーリスト表示
	
	//コンストラクタインジェクション(ControllerとServiceを紐づける)
	public AdminUserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/adminuser")
	public String adminUser(Model model) {
		
		// 最新のユーザーリストを取得
		List<User> users = userService.getAllUsers();
		
		// ビューにユーザーリストを渡す
		model.addAttribute("users",users);
		
		//ビューの呼び出し
		return "adminUserView";
		
	}
	
	//ユーザー登録処理
	@PostMapping("/register")
	public String registerUser(RedirectAttributes redirectAttributes,
			@RequestParam("user_name") String userName,
			@RequestParam("password") String password,
			@RequestParam("role_id") int roleId) {
		
		try {
		// リクエストパラメータからのデータを用いてユーザー登録
		userService.createUser(userName, password, roleId);
		// 登録成功時は完了メッセージをビューに受け渡す
		redirectAttributes.addFlashAttribute("successMessage","ユーザー登録が完了しました");
		
		}catch(IllegalArgumentException e) {
			// 登録失敗時はエラーメッセージをビューに受け渡す
			redirectAttributes.addFlashAttribute("failuerMessage",e.getMessage());
			
			 // 再表示用の入力データをビューに受け渡す（パスワードは除く）
			redirectAttributes.addFlashAttribute("userName",userName);
			redirectAttributes.addFlashAttribute("roleId",roleId);
		}
		
		// adminuserにリダイレクトしてリストを再表示
		return "redirect:/adminuser";
		
		
	}
	
	
	
	

}
