/**
 * 
 */
package com.leucht.eclipse.externaldiff;

import org.eclipse.ui.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.preference.FileFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import org.eclipse.jface.preference.*;



public class ExtDiffPreferencePage 
	extends FieldEditorPreferencePage 
	implements IWorkbenchPreferencePage {
	
	private Text fFilters;
	private StringFieldEditor cmdLineArgs;
	private FileFieldEditor exeFile;
	private BooleanFieldEditor escClose;
	private BooleanFieldEditor addMru;

	public ExtDiffPreferencePage() {
		
		super(FieldEditorPreferencePage.FLAT);
		
		setPreferenceStore(ExternalDiff.getDefault().getPreferenceStore());
		
	}
	
	/*
	 * @see IWorkbenchPreferencePage#init()
	 */
	public void init(IWorkbench workbench) {
		// empty
	}

	protected void createFieldEditors() {

		Composite parent = getFieldEditorParent();
		GridLayout layout= new GridLayout(1, false);
		layout.marginWidth= 0;
		parent.setLayout(layout);

		Composite c2= new Composite(parent, SWT.NONE);
		GridLayout layout2= new GridLayout(3, false);
		layout2.marginWidth= 0;
		c2.setLayout(layout2);

		exeFile = new FileFieldEditor( ExternalDiff.PREF_EXE_FILE, "External diff executable:", c2);
		addField(exeFile);
		
		// a spacer
		new Label(parent, SWT.NONE);

		Label l2= new Label(parent, SWT.WRAP);
		l2.setText("Command line argument helper:"); //$NON-NLS-1$
		
		TabFolder folder= new TabFolder(parent, SWT.NONE);
		//folder.setLayout(new TabFolderLayout());	
		folder.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		TabItem item= new TabItem(folder, SWT.NONE);
		item.setText("WinMerge");	//$NON-NLS-1$
		//item.setImage(JavaPluginImages.get(JavaPluginImages.IMG_OBJS_CFILE));
		item.setControl(createWinMergeTab(folder));
		
		item= new TabItem(folder, SWT.NONE);
		item.setText("Beyond Compare");	//$NON-NLS-1$
		//item.setImage(JavaPluginImages.get(JavaPluginImages.IMG_OBJS_CFILE));
		item.setControl(createBeyondCompareTab(folder));
		
		//initializeFields();
		Dialog.applyDialogFont(folder);
		
		// a spacer
		new Label(parent, SWT.NONE);

		Label l3= new Label(parent, SWT.WRAP);
		l3.setText("Command line arguments:"); //$NON-NLS-1$
		
		Label l4= new Label(parent, SWT.WRAP);
		l4.setText("     (%first = 1st file to be compared)"); //$NON-NLS-1$
		
		Label l5= new Label(parent, SWT.WRAP);
		l5.setText("     (%second = 2nd file to be compared)"); //$NON-NLS-1$
		
		Composite c3= new Composite(parent, SWT.NONE);
		GridLayout layout3= new GridLayout(2, false);
		layout3.marginWidth= 0;
		c3.setLayout(layout2);

		cmdLineArgs = new StringFieldEditor( ExternalDiff.PREF_CMD_LINE_ARGS, "", 70, c3);
		cmdLineArgs.setTextLimit(StringFieldEditor.UNLIMITED);
		cmdLineArgs.setEmptyStringAllowed(true);
		addField(cmdLineArgs);
	
		// a spacer
		new Label(parent, SWT.NONE);

		//return folder;
		
	}
	
	
	private Control createWinMergeTab(Composite parent) {
		Composite composite= new Composite(parent, SWT.NULL);
		GridLayout layout= new GridLayout();
		layout.numColumns= 1;
		composite.setLayout(layout);
		
		escClose = new BooleanFieldEditor( ExternalDiff.PREF_ESC_CLOSE, "Close WinMerge with single 'ESC' keypress", composite);
		escClose.setPropertyChangeListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				if(event.getProperty().equals(FieldEditor.VALUE)) {
					Object valueObj = event.getNewValue();
					Boolean value = new Boolean(valueObj.toString());
//					System.out.println("propertyChange occurred");
//					cmdLineArgs.setStringValue("propertyChange occurred");
					if(value.booleanValue()) {
						String tempString = cmdLineArgs.getStringValue();
						// Check to see if it's already there
						if((tempString.contains("/e")) || (tempString.contains("-e"))) {
							// Do nothing
						}
						else {
							// Add it
							tempString = "/e " + tempString;
							cmdLineArgs.setStringValue(tempString);
						}
					}
					else {
						// Remove /e & -e from command line if it exists
						String tempString = cmdLineArgs.getStringValue();
//						System.out.println("tempString1: " + tempString);
						String tempString2 = tempString.replaceAll("/e", "");
//						System.out.println("tempString2: " + tempString2);
						String tempString3 = tempString2.replaceAll("-e", "");
//						System.out.println("tempString3: " + tempString3);
						cmdLineArgs.setStringValue(tempString3.trim());
					}
					escClose.setPreferenceStore(ExternalDiff.getDefault().getPreferenceStore());
					//escClose.setPreferencePage(this);
					escClose.store();
				}
				//System.out.println("propertyChange occurred: " + event.getProperty());
			}
		});

		//addField(escClose);
		escClose.setPreferenceStore(getPreferenceStore());
		escClose.setPreferencePage(this);
		escClose.load();

		
		addMru = new BooleanFieldEditor( ExternalDiff.PREF_ADD_MRU, "Remove both paths from MRU (most recently used list)", composite);
		addMru.setPropertyChangeListener(new IPropertyChangeListener() {

			public void propertyChange(PropertyChangeEvent event) {
				if(event.getProperty().equals(FieldEditor.VALUE)) {
					Object valueObj = event.getNewValue();
					Boolean value = new Boolean(valueObj.toString());
//					System.out.println("propertyChange occurred");
//					cmdLineArgs.setStringValue("propertyChange occurred");
					if(value.booleanValue()) {
						String tempString = cmdLineArgs.getStringValue();
						// Check to see if it's already there
						if((tempString.contains("/ub")) || (tempString.contains("-ub"))) {
							// Do nothing
						}
						else {
							// Add it
							tempString = "/ub " + tempString;
							cmdLineArgs.setStringValue(tempString);
						}
					}
					else {
						// Remove /ub & -ub from command line if it exists
						String tempString = cmdLineArgs.getStringValue();
//						System.out.println("tempString1: " + tempString);
						String tempString2 = tempString.replaceAll("/ub", "");
//						System.out.println("tempString2: " + tempString2);
						String tempString3 = tempString2.replaceAll("-ub", "");
//						System.out.println("tempString3: " + tempString3);
						cmdLineArgs.setStringValue(tempString3.trim());
					}
					addMru.setPreferenceStore(ExternalDiff.getDefault().getPreferenceStore());
					//addMru.setPreferencePage(getPreferenceStore());
					addMru.store();
				}
				//System.out.println("propertyChange occurred: " + event.getProperty());
			}
		});

		//addField(addMru);
		addMru.setPreferenceStore(getPreferenceStore());
		addMru.setPreferencePage(this);
		addMru.load();

		
		return composite;
	}
	
	
	private Control createBeyondCompareTab(Composite parent) {
		Composite composite= new Composite(parent, SWT.NULL);
		GridLayout layout= new GridLayout();
		layout.numColumns= 1;
		composite.setLayout(layout);
				
		// a spacer
		new Label(composite, SWT.NONE);

		//addCheckBox(composite, "ComparePreferencePage.saveBeforePatching.label", PREF_SAVE_ALL_EDITORS, 0);	//$NON-NLS-1$

		// a spacer
		new Label(composite, SWT.NONE);
		
		Label l= new Label(composite, SWT.WRAP);
		l.setText("Floating label"); //$NON-NLS-1$
		
		Composite c2= new Composite(composite, SWT.NONE);
		c2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		layout= new GridLayout(2, false);
		layout.marginWidth= 0;
		c2.setLayout(layout);
		
		l= new Label(c2, SWT.NONE);
		l.setText("Label 2"); //$NON-NLS-1$
		
		fFilters= new Text(c2, SWT.BORDER);
		fFilters.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		fFilters.setText("Default text field value");
		
		return composite;
	}


}
