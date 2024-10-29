package kz.edu.astanait.onlineshop.service;

import kz.edu.astanait.onlineshop.domain.GeneratedToken;

public interface TokenFactory {

    GeneratedToken create(String email);
}
