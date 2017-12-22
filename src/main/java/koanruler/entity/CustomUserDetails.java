package koanruler.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by hose on 2017/9/1.
 */
public class CustomUserDetails extends User implements UserDetails {

    public CustomUserDetails(User user) {
        super(user);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        int userType = super.getType();
        switch (userType){
            case 1: return Arrays.asList( new SimpleGrantedAuthority("ROLE_ADMIN") );
            case 2: return Arrays.asList( new SimpleGrantedAuthority("ROLE_AGENCY") );
            case 3: return Arrays.asList( new SimpleGrantedAuthority("ROLE_CENTER") );
            case 4: return Arrays.asList( new SimpleGrantedAuthority("ROLE_HOSPITAL") );
            case 5: return Arrays.asList( new SimpleGrantedAuthority("ROLE_DEPARTMENT") );
            case 6: return Arrays.asList( new SimpleGrantedAuthority("ROLE_CONSULTATION") );
            case 7: return Arrays.asList( new SimpleGrantedAuthority("ROLE_ANALYST") );
            default: return Arrays.asList( new SimpleGrantedAuthority("ROLE_USER") );
        }
    }

    @Override
    public String getPassword() {
        return super.getPwd();
    }

    @Override
    public String getUsername() {
        return super.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return super.getDelflag() == null || super.getDelflag() == 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
