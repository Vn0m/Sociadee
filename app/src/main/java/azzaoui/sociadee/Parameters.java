package azzaoui.sociadee;

import android.graphics.drawable.Drawable;

import java.util.LinkedList;

/**
 * Created by Youssef Azzaoui on 10/11/15.
 *
 *  Singleton class for storing various parameters
 */
public class Parameters {


    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    public static final String PUBLIC_MESSAGE_RECEIVED = "publicMessage";
    public static final String PRIVATE_MESSAGE_RECEIVED = "privateMessage";

    private static String FBToken;
    private static String SociadeeToken;
    private static long facebookId;
    private static String firstname;
    private static Drawable profilePicture;
    private static Drawable HDprofilePicture;
    private static int age;
    private static LinkedList<FacebookImage> facebookImages = new LinkedList<>();
    private static boolean available;
    private static String aboutme;


    public static void addImage(long id , Drawable im)
    {
        FacebookImage image = new FacebookImage(id,im);
        facebookImages.add(image);

    }
    public static void removeImage(FacebookImage image)
    {
        facebookImages.remove(image);
    }

    public static LinkedList<FacebookImage> getFacebookImages()
    {
        return facebookImages;
    }

    public static String getFBToken() {
        return FBToken;
    }

    public static String getSociadeeToken() {
        return SociadeeToken;
    }

    public static void setSociadeeToken(String sociadeeToken) {
        SociadeeToken = sociadeeToken;
    }
    public static void setFBToken(String token) {
        FBToken = token;
    }

    public static String getFirstname() {
        return firstname;
    }

    public static void setFirstname(String fname) {
        Parameters.firstname = fname;
    }

    public static long getFacebookId() {
        return facebookId;
    }

    public static void setFacebookId(long facebookId) {
        Parameters.facebookId = facebookId;
    }

    public static Drawable getProfilePicture() {
        return profilePicture;
    }

    public static void setProfilePicture(Drawable profilePicture) {
        Parameters.profilePicture = profilePicture;
    }

    public static boolean isAvailable() {
        return available;
    }

    public static void setAvailable(boolean available) {
        Parameters.available = available;
    }

    public static String getAboutme() {
        return aboutme;
    }

    public static void setAboutme(String aboutme) {
        Parameters.aboutme = aboutme;
    }

    public static Drawable getHDprofilePicture() {
        return HDprofilePicture;
    }

    public static void setHDprofilePicture(Drawable HDprofilePicture) {
        Parameters.HDprofilePicture = HDprofilePicture;
    }

    public static int getAge() {
        return age;
    }

    public static void setAge(int age) {
        Parameters.age = age;
    }
}
