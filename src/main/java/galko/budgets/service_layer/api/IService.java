package galko.budgets.service_layer.api;

public interface IService<REQ, RES> {

    RES answer(REQ request);
}
