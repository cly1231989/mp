package koanruler.auth;

import java.io.Serializable;

/**
 * Created by chengyuan on 2017/9/24.
 */
public class JwtAuthenticationResponse implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
