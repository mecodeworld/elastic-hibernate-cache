package codeworld.app.ui.view.admin.location.dialog.country;

import codeworld.health.service.model.CountryDto;

public interface CountryView {

    public interface Presenter {

        void save(CountryDto countryDto);
    }

}
