package commons.security;

import commons.model.UserWrapper;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author guorui1
 */
public class SecurityUtils {

    public static UserWrapper currentUser() {
        return (UserWrapper) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public static long currentUserId() {
        UserWrapper currentUser = currentUser();
        return currentUser == null ? -1 : currentUser.getId();
    }
}
