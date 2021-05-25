package codeworld.app.ui.view.admin.location.dialog.state;

import codeworld.health.service.model.StateDto;

public interface StateView {

    public interface Presenter {

        void save(StateDto bean);

    }

}
