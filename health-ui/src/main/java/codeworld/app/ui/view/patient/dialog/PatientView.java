package codeworld.app.ui.view.patient.dialog;

import codeworld.health.service.model.PatientDto;

public interface PatientView {

    public interface Presenter {

        void save(PatientDto dto);
    }

}
