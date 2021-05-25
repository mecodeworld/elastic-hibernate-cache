package codeworld.app.ui.view.admin.location.dialog.state;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;

import codeworld.app.ui.common.enums.AdminGridType;
import codeworld.app.ui.view.admin.location.events.AdminLocationGridRefreshEvent;
import codeworld.health.service.api.StateService;
import codeworld.health.service.model.StateDto;

@Component
public class StateDialogPresenter implements StateView.Presenter {

    private StateDialogViewImpl view = null;

    @Resource
    private StateService stateService;

    @Autowired
    private EventBus.ApplicationEventBus applicationEventBus;

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public void setView(StateDialogViewImpl viewImpl) {
        view = viewImpl;
    }

    public Optional<StateDialogViewImpl> getView() {
        return Optional.ofNullable(this.view);
    }

    @Override
    public void save(StateDto bean) {
        stateService.create(bean);
        if (getView().isPresent()) {
            getView().get()
                    .close();
        }
        applicationEventBus.publish(this, new AdminLocationGridRefreshEvent(AdminGridType.STATE));
    }
}
