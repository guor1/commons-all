package commons.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * 扩展内置UserDetails，添加id属性
 *
 * @author guor
 */
@Getter
@Setter
public class UserWrapper extends User {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public static final int SUPER_ADMIN_ID = 1;

    private Long id;

    public UserWrapper(Long id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public boolean isSuperAdmin() {
        return getId() == SUPER_ADMIN_ID;
    }
}
