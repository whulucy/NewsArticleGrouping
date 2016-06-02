o List any special studio settings, project settings or demo/testing instructions if necessary.
I use the following settings:
        applicationId "edu.scu.xli2.photonotes"
        minSdkVersion 21
        targetSdkVersion 23
o Where are image files stored? Do you use thumbnails?
    I store image under "/storage/emulated/0/“. I use thumbnails for the RecyclerView list of photos and use big display of photo on the DisplayPhoto Activity
o Does “Add” button to Camera picture capture directly or go to “Add Photo” activity first?
	I use the “+” sign for jumping to the AddPhoto Activity. My Add button go to AddPhoto activity first. User can choose take photo button and save button from the AddPhoto Activity.
o Do you implement 6.0 run-time permission request or set targetSDK to 22 in build.gradle file?

      I use 6.0 run-time permission request and implemented the following two functions:
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };o Do you support both viewing modes or have fixed viewing modes?
	I use fixed portrait modes in my app.o Do you implement the bonus features?
	Yes, I implemented the RecyclerView with swiping and dragging touch functions. I also have a customized launch icon and my app displays well on both 4.0 inch and 7.0 inch screens 
