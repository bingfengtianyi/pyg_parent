package com.itheima.pyg.service;

import com.itheima.pyg.pojo.seller.Seller;
import com.itheima.pyg.service.seller.SellerService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class UserDetailServiceImpl  implements UserDetailsService {

    private SellerService sellerService;
    public void setSellerService(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Seller seller = sellerService.findOne(username);
        if (seller!=null&&"1".equals(seller.getStatus())){
            Set<GrantedAuthority> authorities = new HashSet<>();
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("ROLE_SELLER");
            authorities.add(simpleGrantedAuthority);
            User user= new User(username,seller.getPassword(),authorities);
            return user;
        }
        return null;
    }
}
