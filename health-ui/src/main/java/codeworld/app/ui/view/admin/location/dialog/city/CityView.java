package codeworld.app.ui.view.admin.location.dialog.city;

import codeworld.health.service.model.CityDto;

public interface CityView {

    public interface Presenter {

        void save(CityDto dto);
    }

}
