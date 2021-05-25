package codeworld.app.ui.view.admin.location.events;

import codeworld.app.ui.common.enums.AdminGridType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminLocationGridRefreshEvent {
    private AdminGridType gridType;
}
