package codeworld.app.ui.view.admin.hospital.events;

import codeworld.app.ui.common.enums.AdminGridType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminHospitalGridRefreshEvent {

    private AdminGridType gridType;

}
