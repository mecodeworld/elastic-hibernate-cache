package codeworld.app.ui.view.admin.location.dialog.city;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;

import codeworld.app.ui.common.enums.AdminGridType;
import codeworld.app.ui.view.admin.location.events.AdminLocationGridRefreshEvent;
import codeworld.health.service.api.CityService;
import codeworld.health.service.model.CityDto;

@Component
public class CityDialogPresenter implements CityView.Presenter {

    private CityDialogViewImpl view = null;

    @Resource
    private CityService cityService;

    @Autowired
    private EventBus.ApplicationEventBus applicationEventBus;

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public void setView(CityDialogViewImpl viewImpl) {
        view = viewImpl;
    }

    public Optional<CityDialogViewImpl> getView() {
        return Optional.ofNullable(this.view);
    }

    @Override
    public void save(CityDto dto) {
        cityService.create(dto);
        if (getView().isPresent()) {
            getView().get()
                    .close();
        }
        applicationEventBus.publish(this, new AdminLocationGridRefreshEvent(AdminGridType.CITY));
    }

}
