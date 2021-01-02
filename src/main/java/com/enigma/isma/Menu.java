package com.enigma.isma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;

import com.enigma.isma.dao.*;
import com.enigma.isma.entity.Role;
import com.enigma.isma.entity.User;

public class Menu {
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	String regexEmail = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

	BooksDao booksDao = new BooksDaoImpl();
	TransactionDao transactionDao = new TransactionDaoImpl();
	ShelfDao shelfDao= new ShelfDaoImpl();
	CategoryDao categoryDao= new CategoryDaoImpl();

	UserDao userDao = new UserDaoImpl();


	public void start() throws NumberFormatException, IOException {


		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		System.out.println("+============================================+");
		System.out.println("|   Selamat Datang Di Penyewaan Buku Kenji   |");
		System.out.println("+============================================+");
		System.out.print("Sudah memiliki akun?[(y)es/(n)o] ");
		String answer = br.readLine();

		if (answer.contains("n")){
			System.out.print("Apakah kamu ingin mendaftar menjadi member? [(y)es/(n)o] ");
			answer=br.readLine();

			if (answer.contains("y")){
				signUp();
				signIn();
			}else {
				System.out.println("Kamu tidak bisa mengakses menu");
			}
		}else{
		signIn();
		}

	}

	public void signIn() throws IOException {
		User user = null;

		while (user==null){
			System.out.println("==============Log in=============");
			System.out.print("Email : ");
			String email = br.readLine();
			System.out.print("Password : ");
			String password = br.readLine();
			user= userDao.login(email,password);
			if (user==null){
				System.out.println("email atau password salah!Silahkan ulangi!");
			}else {
				System.out.println("login berhasil!");
				MemberMenu memberMenu = new MemberMenu();
				memberMenu.printMenu(br,booksDao,transactionDao,shelfDao,categoryDao,user);
			}
		}


	}
	public void signUp() throws IOException {
		System.out.println("==============Registration Member=============");
		System.out.print("Nama : ");
		String name = br.readLine();
		String email="";
		while (!email.matches(regexEmail)){
			System.out.print("Email : ");
			email = br.readLine();
			if (!email.matches(regexEmail)){
				System.out.println("email yang dimasukkan salah!");
			}
		}
		System.out.print("Phone : ");
		String phone = br.readLine();
		System.out.print("Address :");
		String address = br.readLine();
		String password="";
		while (password.length()<8){
			System.out.print("Password [8 minimum]:");
			password = br.readLine();
			if (password.length()<8){
				System.out.println("password kurang dari 8");
			}
		}
		String role = "";
		System.out.print("Apakah kamu admin?[(y)es/(n)o] ");
		String answer=br.readLine();
		if (answer.contains("n")){
			role="Member";
		}else{
			role="Admin";
		}

		User newUser = new User(name,email,password,phone,address, Role.valueOf(role));
		String status=userDao.saveMember(newUser);
		if (status.equals("Success")){
		System.out.println("Anda berhasil menjadi member kami!, silahkan login untuk melihat menu!");
		}else{
			System.out.println("Gagal mendaftar!");
		}
	}
}
