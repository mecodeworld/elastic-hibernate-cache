package codeworld.app.ui.view.admin.hospital.dialog.staff;

import codeworld.health.service.model.StaffDto;

public interface StaffDialogView {

    public interface Presenter {

        void save(StaffDto dto);
    }

}
