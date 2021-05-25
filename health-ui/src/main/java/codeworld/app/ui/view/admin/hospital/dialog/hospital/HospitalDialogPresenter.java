package codeworld.app.ui.view.admin.hospital.dialog.hospital;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;

import codeworld.app.ui.common.enums.AdminGridType;
import codeworld.app.ui.view.admin.hospital.events.AdminHospitalGridRefreshEvent;
import codeworld.health.service.api.HospitalService;
import codeworld.health.service.model.HospitalDto;

@Component
public class HospitalDialogPresenter implements HospitalView.Presenter {

    @Resource
    private HospitalService hospitalService;

    private HospitalDialogViewImpl view = null;

    @Autowired
    private EventBus.ApplicationEventBus applicationEventBus;

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public void setView(HospitalDialogViewImpl viewImpl) {
        view = viewImpl;
    }

    public Optional<HospitalDialogViewImpl> getView() {
        return Optional.ofNullable(this.view);
    }

    @Override
    public void save(HospitalDto dto) {
        hospitalService.create(dto);
        if (getView().isPresent()) {
            getView().get()
                    .close();
        }
        applicationEventBus.publish(this, new AdminHospitalGridRefreshEvent(AdminGridType.HOSPITAL));
    }

}
