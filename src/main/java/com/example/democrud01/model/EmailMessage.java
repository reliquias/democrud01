package com.example.democrud01.model;

public class EmailMessage {
	
	public static String createTitle(UserSistem user) {
		return user.getName() + " seu cadastro como " + user.getNivel() + " foi realizado com sucesso!";
	}
	
	public static String messageToNewUser(UserSistem user, String password) {
		return 
				"Olá " + user.getName() 
		+ " bem vindo ao crm505 ! \n"
		+ "Os seus dados de acesso estão logo abaixo. \n\n"
		+ "================================================= \n\n"
		+ "Email: " + user.getEmail() + " \n"
		+ "Password: " + password + "\n\n"
		+ "================================================= \n\n";
	}
	
	
	public static String createTitleUpdatePassword(UserSistem user) {
		return user.getName() + " sua senha foi atualizada com sucesso !";
	}
	
	public static String messageToUpdatePassword(UserSistem user, String password) {
		return 
				"Olá " + user.getName() 
		+ " sua senha foi atualizada com sucesso ! \n"
		+ "Os seus dados de acesso estão logo abaixo. \n\n"
		+ "================================================= \n\n"
		+ "Email: " + user.getEmail() + " \n"
		+ "Password: " + password + "\n\n"
		+ "================================================= \n\n";
	}

}
