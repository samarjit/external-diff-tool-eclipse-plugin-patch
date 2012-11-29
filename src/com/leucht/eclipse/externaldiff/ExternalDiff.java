package com.leucht.eclipse.externaldiff;

import org.eclipse.ui.plugin.*;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.StringConverter;
import org.osgi.framework.BundleContext;

/**
 * The main plugin class to be used in the desktop.
 */
public class ExternalDiff extends AbstractUIPlugin {

	//The shared instance.
	private static ExternalDiff plugin;
	public static final String PREF_EXE_FILE = "exeFile";
	public static final String PREF_CMD_LINE_ARGS = "cmdLineArgs";
	public static final String PREF_ESC_CLOSE = "escClose";
	public static final String PREF_ADD_MRU = "addMru";
	
	/**
	 * The constructor.
	 */
	public ExternalDiff() {
		plugin = this;
	}

	/**
	 * This method is called upon plug-in activation
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
	}

	/**
	 * This method is called when the plug-in is stopped
	 */
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance.
	 */
	public static ExternalDiff getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given
	 * plug-in relative path.
	 *
	 * @param path the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return AbstractUIPlugin.imageDescriptorFromPlugin("com.leucht.eclipse.externaldiff", path);
	}
	
	protected void initializeDefaultPreferences( IPreferenceStore store) {
		
		super.initializeDefaultPreferences(store);
		
		store.setDefault(PREF_EXE_FILE, "");
		
		store.setDefault(PREF_CMD_LINE_ARGS, "%first %second");
		
		store.setDefault(PREF_ESC_CLOSE, StringConverter.asString(false));
		
		store.setDefault(PREF_ADD_MRU, StringConverter.asString(false));
		
	}
}


