package codeworld.app.ui.view.patient.dialog;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.events.EventBus;

import codeworld.app.ui.view.patient.events.PatientGridRefreshEvent;
import codeworld.health.service.api.PatientService;
import codeworld.health.service.model.PatientDto;

@Component
public class PatientDialogPresenter implements PatientView.Presenter {

    private PatientDialogViewImpl view = null;

    @Resource
    private PatientService patientService;

    @Autowired
    private EventBus.ApplicationEventBus applicationEventBus;

    @PostConstruct
    public void postConstruct() {
        applicationEventBus.subscribe(this, false);
    }

    public void setView(PatientDialogViewImpl viewImpl) {
        view = viewImpl;
    }

    public Optional<PatientDialogViewImpl> getView() {
        return Optional.ofNullable(this.view);
    }

    @Override
    public void save(PatientDto dto) {
        Long id = patientService.create(dto);
        if (getView().isPresent()) {
            getView().get()
                    .close();
        }
        applicationEventBus.publish(this, new PatientGridRefreshEvent(id));
    }

}
