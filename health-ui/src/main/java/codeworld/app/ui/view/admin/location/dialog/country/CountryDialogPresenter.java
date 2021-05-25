package codeworld.app.ui.view.admin.location.dialog.country;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;

import codeworld.app.ui.common.enums.AdminGridType;
import codeworld.app.ui.view.admin.location.events.AdminLocationGridRefreshEvent;
import codeworld.health.service.api.CountryService;
import codeworld.health.service.model.CountryDto;

@Component
public class CountryDialogPresenter implements CountryView.Presenter {

    private CountryDialogViewImpl view = null;

    @Autowired
    private CountryService countryService;

    @Autowired
    private EventBus.ApplicationEventBus applicationEventBus;

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public void setView(CountryDialogViewImpl viewImpl) {
        view = viewImpl;
    }

    public Optional<CountryDialogViewImpl> getView() {
        return Optional.ofNullable(this.view);
    }

    @Override
    public void save(CountryDto countryDto) {
        countryService.create(countryDto);
        if (getView().isPresent()) {
            getView().get()
                    .close();
        }
        applicationEventBus.publish(this, new AdminLocationGridRefreshEvent(AdminGridType.COUNTRY));
    }

}
