package com.leucht.eclipse.externaldiff.popup.actions;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

import com.leucht.eclipse.externaldiff.ExternalDiff;

public class LaunchExternalDiffTool implements IObjectActionDelegate {

	private ISelection selection = null;
	
	/**
	 * Constructor for Action1.
	 */
	public LaunchExternalDiffTool() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		Shell shell = new Shell();
		File workingDir = null;
		
        if (selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
			MessageDialog.openInformation(
					shell,
					"Failure",
					"No Selection or not Structured Selection.");
        }
//        else {
//    		MessageDialog.openInformation(
//			shell,
//			"External diff Tool Plug-in",
//			"Looks like it's a Structured Selection.");
//        }
        
        StructuredSelection sselection = (StructuredSelection)selection;
        List myList = sselection.toList();
//		MessageDialog.openInformation(
//		shell,
//		"External diff Tool Plug-in",
//		"List size: " + myList.size());
        
        IResource firstResource = (IResource)myList.get(0);
        IResource secondResource = (IResource)myList.get(1);
//		MessageDialog.openInformation(
//		shell,
//		"External diff Tool Plug-in",
//		"Resource 1: " + firstResource);
		
        IPath path1 = firstResource.getLocation();
        IPath path2 = secondResource.getLocation();
//		MessageDialog.openInformation(
//		shell,
//		"External diff Tool Plug-in",
//		"Path1: " + path1);

//        IPath path = ResourcesPlugin.getWorkspace().getRoot().getRawLocation();
//		MessageDialog.openInformation(
//		shell,
//		"External diff Tool Plug-in",
//		"Path: " + path);
//
//        path1 = path1.removeFirstSegments(1);
//        path2 = path2.removeFirstSegments(1);
        String firstArg = path1.toString();
        String secondArg = path2.toString();
//        String firstArg = path.toString() + path1.toString();
//        String secondArg = path.toString() + path2.toString();
        
//        MessageDialog.openInformation(
//				shell,
//				"No Executable",
//				"Hello first Arg="+firstArg+" srcondArg="+secondArg);
        
        String commandLineArgs = ExternalDiff.getDefault().getPreferenceStore().getString(ExternalDiff.PREF_CMD_LINE_ARGS);
        String modCmdLineArgs = commandLineArgs.replaceAll("%first", firstArg);
        String modCmdLineArgs2 = modCmdLineArgs.replaceAll("%second", secondArg);
        String[] cmdLineSplit = modCmdLineArgs2.split(" ");
        int size = cmdLineSplit.length;
        
        
        
		String[] cmdLine = new String[size + 1];
		
		cmdLine[0] = ExternalDiff.getDefault().getPreferenceStore().getString(ExternalDiff.PREF_EXE_FILE);
		
		// Check for empty String (user never configured)
		if(cmdLine[0].equals("")) {
			MessageDialog.openInformation(
					shell,
					"No Executable",
					"No External diff Tool has been configured.  Please configure an executable tool and command line options under Window | Preferences | General | Compare/External Tool.");
			return;
		}
		
		
		//cmdLine[1] = modCmdLineArgs2;
		for(int counter = 1; counter < size + 1; counter++) {
			cmdLine[counter] = cmdLineSplit[counter - 1];
		}
		
		
//		MessageDialog.openInformation(
//			shell,
//			"External diff Tool Plug-in",
//			"Should now start external program.");
		
		try {
			DebugPlugin.exec(cmdLine, workingDir);
//			MessageDialog.openInformation(
//					shell,
//					"External diff Tool Plug-in",
//					"After exec.");
		} catch (CoreException e) {
			MessageDialog.openInformation(
					shell,
					"Failure",
					"A problem occurred trying to start your External diff Toool.  Please check the settings under Window | Preferences | General | Compare/External Tool.");
		}
		
//		MessageDialog.openInformation(
//				shell,
//				"External diff Tool Plug-in",
//				"Exiting run.");
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		//System.out.println("Selection changed!");
		this.selection = selection;
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public ISelection getSelection() {
		//System.out.println("Selection changed!");
		return (this.selection);
	}

}
