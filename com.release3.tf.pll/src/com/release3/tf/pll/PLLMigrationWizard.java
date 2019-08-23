package com.release3.tf.pll;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;

import org.eclipse.jface.wizard.Wizard;

import com.release3.tf.core.form.Settings;
import com.release3.tf.pll.model.MigrationPLL;
import com.release3.tf.pll.model.PLLMigrationModelProvider;

public class PLLMigrationWizard extends Wizard {

	public PLLMigrationWizard() {
		setWindowTitle("PLL Migration Wizard");
	}

	@Override
	public void addPages() {
		addPage(new PLLMigrationFileChooserWizardPage());
	}

	@Override
	public boolean performFinish() {
		List<MigrationPLL> pllList = PLLMigrationModelProvider.getInstance()
				.getSelectedPLLs();
		for (MigrationPLL migrationPLL : pllList) {
			File migrationPLLFile = migrationPLL.getPllFile();
			Runtime rt = Runtime.getRuntime();
			String batchCommand = Settings.getSettings()
					.getOracleDeveloperSuite()
					+ "\\BIN\\rwconverter batch=yes overwrite=yes stype=pllfile source="
					+ migrationPLLFile.getAbsolutePath() + " dtype=pldfile";
			try {
				System.out.println("batchCommand " + batchCommand);
				Process proc = rt.exec(batchCommand);
				BufferedReader in = new BufferedReader(new InputStreamReader(
						proc.getInputStream()));
				String line = in.readLine();
				while (line != null) {
					line = in.readLine();

				}

				proc.waitFor();

			} catch (Exception ex) {

			}
		}

		return true;
	}

}
