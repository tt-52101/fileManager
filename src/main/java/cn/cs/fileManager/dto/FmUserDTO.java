/**
 * 
 */
package cn.cs.fileManager.dto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import cn.cs.fileManager.dao.model.FmUser;
import lombok.Data;

/**
 * @author al89
 *
 */
@Data
public class FmUserDTO  extends FmUser implements UserDetails {
	/**
     * 是否记住密码
     */
    private Boolean remember;
 
    
    /**
     * 账户是否未过期
     *
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     *
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 账户凭证是否未过期
     *
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

			
	/**获取权限信息，rewrite userDetails getAuthorities
     * @return
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities =
                getRoles().stream().map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName)).collect(Collectors.toList());
        return grantedAuthorities;
    }

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return super.getLoginName();
	}

}
