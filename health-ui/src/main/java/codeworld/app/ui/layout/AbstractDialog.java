package codeworld.app.ui.layout;

import javax.annotation.PostConstruct;

import com.vaadin.flow.component.dialog.Dialog;

public abstract class AbstractDialog extends Dialog {

    private static final long serialVersionUID = -1601028584216617019L;

    @PostConstruct
    private void init() {
        initialize();
    }

    protected abstract void initialize();

}
