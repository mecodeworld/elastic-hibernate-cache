package codeworld.app.ui.view.admin.hospital.dialog.department;

import codeworld.health.service.model.DepartmentDto;

public interface DepartmentDialogView {

    public interface Presenter {

        void save(DepartmentDto dto);
    }

}
