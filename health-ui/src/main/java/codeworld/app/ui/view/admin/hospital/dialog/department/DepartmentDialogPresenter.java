package codeworld.app.ui.view.admin.hospital.dialog.department;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;

import codeworld.app.ui.common.enums.AdminGridType;
import codeworld.app.ui.view.admin.hospital.events.AdminHospitalGridRefreshEvent;
import codeworld.health.service.api.DepartmentService;
import codeworld.health.service.model.DepartmentDto;

@Component
public class DepartmentDialogPresenter implements DepartmentDialogView.Presenter {

    private DepartmentDialogViewImpl view = null;

    @Resource
    private DepartmentService departmentService;

    @Autowired
    private EventBus.ApplicationEventBus applicationEventBus;

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public void setView(DepartmentDialogViewImpl viewImpl) {
        view = viewImpl;
    }

    public Optional<DepartmentDialogViewImpl> getView() {
        return Optional.ofNullable(this.view);
    }

    @Override
    public void save(DepartmentDto dto) {
        departmentService.create(dto);
        if (getView().isPresent()) {
            getView().get()
                    .close();
        }
        applicationEventBus.publish(this, new AdminHospitalGridRefreshEvent(AdminGridType.DEPARTMENT));
    }

}
