package galko.budgets.web.infra.jwt;

public class Jwt {

    public String getIss() {
        return iss;
    }

    public String getSub() {
        return sub;
    }

    public String getAud() {
        return aud;
    }

    public String getExp() {
        return exp;
    }

    public String getIat() {
        return iat;
    }

    private String iss;
    private String sub;
    private String aud;
    private String exp;
    private String iat;
}
