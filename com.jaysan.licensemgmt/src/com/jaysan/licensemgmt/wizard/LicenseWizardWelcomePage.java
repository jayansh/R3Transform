package com.jaysan.licensemgmt.wizard;

import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import de.schlichtherle.license.LicenseContent;
import de.schlichtherle.license.LicenseManager;

public class LicenseWizardWelcomePage extends WizardPage implements IWizardPage {

	private LicenseWizardInstalltionPage page2;
	private LicenseWizardInstallSuccessPage page3;
	private LicenseManager manager;

	@Override
	public boolean canFlipToNextPage() {
		// TODO Auto-generated method stub
		return true;
	}

	private String subject=Messages.getString("LicenseWizardWelcomePage.product_title"); //$NON-NLS-1$
	WizardPage nextPage;
	private Button btninstall;
	private Button btnVerify;
	private boolean selectBtnVerify;

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	

	public boolean isSelectBtnVerify() {
		return selectBtnVerify;
	}



	public void setSelectBtnVerify(boolean selectBtnVerify) {
		this.selectBtnVerify = selectBtnVerify;
	}



	/**
	 * Create the wizard.
	 */
	public LicenseWizardWelcomePage(LicenseManager manager) {
		super(Messages.getString("LicenseWizardWelcomePage.wizard_page")); //$NON-NLS-1$
		setPageComplete(false);
		setTitle(Messages.getString("LicenseWizardWelcomePage.wizard_title")); //$NON-NLS-1$
		setDescription(Messages.getString("LicenseWizardWelcomePage.wizard_description") //$NON-NLS-1$
				+ subject);
		this.manager = manager;

	}

	/**
	 * Create contents of the wizard.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		setControl(container);
		{
			Label lblWhatYouLike = new Label(container, SWT.NONE);
			lblWhatYouLike.setBounds(10, 25, 295, 15);
			lblWhatYouLike.setText(Messages.getString("LicenseWizardWelcomePage.label_what_you_like_text")); //$NON-NLS-1$
		}
		{
			Composite installOrVerifyComposite = new Composite(container,
					SWT.NONE);
			installOrVerifyComposite.setBounds(91, 66, 392, 146);
			final LicenseWizard wizard = (LicenseWizard) getWizard();

			{
				btninstall = new Button(installOrVerifyComposite, SWT.RADIO);
				btninstall.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						selectBtnVerify = false;
					}
				});
				btninstall.setBounds(29, 37, 223, 16);
				btninstall.setText(Messages.getString("LicenseWizardWelcomePage.button_install_cert_text")); //$NON-NLS-1$

			}
			{
				btnVerify = new Button(installOrVerifyComposite, SWT.RADIO);
				btnVerify.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						selectBtnVerify = true;
						
						try {
							LicenseContent content = manager.verify();
							((LicenseWizard) getWizard()).setLicenseContent(content);
							
							((LicenseWizardInstallSuccessPage) ((LicenseWizard) getWizard())
									.getPage3()).fillContent(content);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					}
				});
				btnVerify.setBounds(29, 72, 223, 16);
				btnVerify.setText(Messages.getString("LicenseWizardWelcomePage.button_verify_lic_text")); //$NON-NLS-1$
				
			}
		}
	}

	public Button getBtnVerify() {
		return btnVerify;
	}

	@Override
	public IWizardPage getNextPage() {
		if(selectBtnVerify){
			return ((LicenseWizard)getWizard()).getPage3();
		}else{
			return super.getNextPage();
		}
	}

	
}
