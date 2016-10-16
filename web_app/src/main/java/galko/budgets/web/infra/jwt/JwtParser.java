package galko.budgets.web.infra.jwt;

import com.google.gson.Gson;

import java.util.Base64;

public class JwtParser {

    public static Jwt parse(String headerValue) {

        if (!headerValue.startsWith("Bearer ")) {
            throw new RuntimeException("header does not start with \"Bearer \". header: " + headerValue);
        }

        final String[] parts = headerValue.substring("Bearer ".length()).split("\\.");

        if (parts.length != 3) {
            throw new RuntimeException("header is not separated with 3 dots. header: " + headerValue);
        }

        final String secondPartAsJson = new String(Base64.getDecoder().decode(parts[1]));

        final Gson gson = new Gson();

        return gson.fromJson(secondPartAsJson, Jwt.class);
    }
}
