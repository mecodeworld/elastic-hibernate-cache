package codeworld.app.ui.view.admin.hospital.dialog.hospital;

import codeworld.health.service.model.HospitalDto;

public interface HospitalView {

    public interface Presenter {

        void save(HospitalDto dto);
    }

}
