package codeworld.app.ui.view.admin.hospital.dialog.staff;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;

import codeworld.app.ui.common.enums.AdminGridType;
import codeworld.app.ui.view.admin.hospital.events.AdminHospitalGridRefreshEvent;
import codeworld.health.service.api.StaffService;
import codeworld.health.service.model.StaffDto;

@Component
public class StaffDialogPresenter implements StaffDialogView.Presenter {

    private StaffDialogViewImpl view = null;

    @Resource
    private StaffService staffService;

    @Autowired
    private EventBus.ApplicationEventBus applicationEventBus;

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public void setView(StaffDialogViewImpl viewImpl) {
        view = viewImpl;
    }

    public Optional<StaffDialogViewImpl> getView() {
        return Optional.ofNullable(this.view);
    }

    @Override
    public void save(StaffDto dto) {
        staffService.create(dto);
        if (getView().isPresent()) {
            getView().get()
                    .close();
        }
        applicationEventBus.publish(this, new AdminHospitalGridRefreshEvent(AdminGridType.STAFF));
    }

}
