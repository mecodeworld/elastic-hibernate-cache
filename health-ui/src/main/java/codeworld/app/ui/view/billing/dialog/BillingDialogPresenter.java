package codeworld.app.ui.view.billing.dialog;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class BillingDialogPresenter implements BillingView.Presenter {

    private BillingDialogViewImpl view = null;

    public void setView(BillingDialogViewImpl viewImpl) {
        view = viewImpl;
    }

    public Optional<BillingDialogViewImpl> getView() {
        return Optional.ofNullable(this.view);
    }

}
