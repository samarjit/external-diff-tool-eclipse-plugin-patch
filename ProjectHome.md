This project is just a patched version of the eclipse plugin already existing [externaldiff](https://sourceforge.net/projects/externaldiff/) by [kleucht](https://sourceforge.net/users/kleucht/) for a long time since 2006. It enables selection of any two files of any project currently opened in eclipse and compare using an external tool like WinMerge.

There was a small bug that if files were selected from two different projects which were in a different workspace than the current one, then it showed wrong path. The root path always points to the same workspace.

A detail discussion https://sourceforge.net/projects/externaldiff/forums/forum/567724/topic/1527426 and patchhttps://sourceforge.net/tracker/?func=detail&aid=2165559&group_id=166350&atid=838676 has been provided in 2008. However it didn't seem to work for me.

So I tried implementing solution given by [adrianissott](https://sourceforge.net/users/adrianissott/)
I am going to follow his solution verbatim

```patch

74,75c74,75

<         IPath path1 = firstResource.getFullPath();

<         IPath path2 = secondResource.getFullPath();

---

>         IPath path1 = firstResource.getLocation();

>         IPath path2 = secondResource.getLocation();

81,88c80,82

<         IPath path = ResourcesPlugin.getWorkspace().getRoot().getRawLocation();

< //		MessageDialog.openInformation(

< //		shell,

< //		"External diff Tool Plug-in",

< //		"Path: " + path);

<

<         String firstArg = path.toString() + path1.toString();

<         String secondArg = path.toString() + path2.toString();

---

>         String firstArg = path1.toString();

>         String secondArg = path2.toString();

96c90
```

I would like to publish under the same license the the original author which is [Common Public License 1.0](https://sourceforge.net/directory/license:ibmcpl/) at the time of writing this. Since google code does not provide this I am chooing eclipse public license.