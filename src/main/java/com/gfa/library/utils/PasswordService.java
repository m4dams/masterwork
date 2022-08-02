package com.gfa.library.utils;

public interface PasswordService {

  String hashPassword(String password);

  Boolean checkPassword(String passwordString, String passwordHashed);
}
