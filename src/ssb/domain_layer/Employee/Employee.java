package ssb.domain_layer.Employee;

import ssb.domain_layer.Person;

public abstract class Employee extends Person {

    private boolean canAccessCreateDocBtn;
    private boolean canPrintDoc;
    private boolean canEditDoc;
    private boolean canCreateNewProcessDoc;
    private boolean canCreateReportDocs;
    private boolean canCloseCase;
    private boolean canCreateNotification;
    private boolean canSeeNotifications;

    public Employee(String firstName, String lastName, String phoneNr, String cprNr) {
        super(firstName, lastName, phoneNr, cprNr);
    }

    public final boolean canAccessCreateDocBtn() {
        return canAccessCreateDocBtn;
    }

    protected final void setCanAccessCreateDocBtn(boolean canAccessCreateDocBtn) {
        this.canAccessCreateDocBtn = canAccessCreateDocBtn;
    }

    public final boolean canPrintDoc() {
        return canPrintDoc;
    }

    protected final void setCanPrintDoc(boolean canPrintDoc) {
        this.canPrintDoc = canPrintDoc;
    }

    public final boolean canEditDoc() {
        return canEditDoc;
    }

    protected final void setCanEditDoc(boolean canEditDoc) {
        this.canEditDoc = canEditDoc;
    }

    public final boolean canCreateNewProcessDoc() {
        return canCreateNewProcessDoc;
    }

    protected final void setCanCreateNewProcessDoc(boolean canCreateNewProcessDoc) {
        this.canCreateNewProcessDoc = canCreateNewProcessDoc;
    }

    public final boolean canCreateReportDocs() {
        return canCreateReportDocs;
    }

    protected final void setCanCreateReportDocs(boolean canCreateReportDocs) {
        this.canCreateReportDocs = canCreateReportDocs;
    }

    public final boolean canCloseCase() {
        return canCloseCase;
    }

    protected final void setCanCloseCase(boolean canCloseCase) {
        this.canCloseCase = canCloseCase;
    }

    public final boolean canCreateNotification() {
        return canCreateNotification;
    }

    protected final void setCanCreateNotification(boolean canCreateNotification) {
        this.canCreateNotification = canCreateNotification;
    }

    public final boolean canSeeNotifications() {
        return canSeeNotifications;
    }

    protected final void setCanSeeNotifications(boolean canSeeNotifications) {
        this.canSeeNotifications = canSeeNotifications;
    }
}
