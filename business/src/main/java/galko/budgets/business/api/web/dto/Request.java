package galko.budgets.business.api.web.dto;

import java.util.Optional;

public abstract class Request {

    public Optional<String> getUserId() {
        return Optional.ofNullable(userId);
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;


}
