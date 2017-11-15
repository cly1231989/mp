package koanruler.util;

import koanruler.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by chengyuan on 2017/9/16.
 */
public class UserUtil {
    public static User getCurUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
