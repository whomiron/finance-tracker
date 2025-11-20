package com.example.finance_tracker.config;

import com.example.finance_tracker.models.UserAccount;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserDetailsImpl implements UserDetails {

    private final UserAccount userAccount;

    public UserDetailsImpl(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userAccount.getRoles() == null) return java.util.Collections.emptyList();
        return userAccount.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
    }

    @Override public String getPassword() { return userAccount.getPasswordHash(); }
    @Override public String getUsername() { return userAccount.getUsername(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
