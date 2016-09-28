package galko.budgets.business.api.web;

public interface IService<REQUEST, RESPONSE> {

    RESPONSE handle(REQUEST request);
}
