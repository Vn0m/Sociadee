package azzaoui.sociadee;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import azzaoui.sociadee.NetworkLogin.myImage;

/**
 * Created by Youssef Azzaoui on 29/01/2016.
 * youssef.azzaoui@epfl.ch
 */
public class NetworkUserInfo extends NetworkBase {


    private Bitmap mUserpicture = null;
    private String mFirstname = null;
    private String mAboutme = null;
    private boolean mAvailable = false;
    private String mCity = null;
    private LinkedList<myImage> imFbList;
    private Bitmap lastPicture;
    private int mAge;
    private LinkedList<myImage> mImageList;

    public NetworkUserInfo() {
        super();
    }

    /*
    public boolean UpdateLoc(double lat, double lon, String City) throws IOException, JSONException {
        String toSend = "/updateloc";
        String postData = "longitude=" + lon + "&latitude=" + lat + "&city=" + City;
        JSONObject response = sendPOSTRequest(toSend,postData,true);
        if (response == null) {
            return false;
        } else {
            JSONArray data = response.getJSONArray("data");
            fetchedUser = new ArrayList<>();
            for(int i = 0; i < data.length(); i++)
            {
                UserFetchedGPS newUser = new UserFetchedGPS();
                newUser.facebookId = data.getJSONObject(i).getLong("id");
                newUser.latitude = data.getJSONObject(i).getDouble("lat");
                newUser.longitude = data.getJSONObject(i).getDouble("lon");

                fetchedUser.add(newUser);
            }
            return true;
        }
    }*/

    public Boolean fetchUserInfo(long userId) throws IOException, JSONException {
        String toSend = "/getuserinfo";
        String postData = "id=" + userId;
        JSONObject response = sendPOSTRequest(toSend,postData,true);
        if (response == null) {
            return false;
        } else {
            String data = response.getString("pic");
            mUserpicture = decodeBase64(data);
            mFirstname =  response.getString("firstname");
            mAvailable =  response.getBoolean("available");
            mCity = response.getString("city");
            mAge = response.getInt("age");
            mAboutme = response.getString("aboutMe");

            JSONArray imageList = response.getJSONArray("images");
            mImageList = new LinkedList<>();
            for(int i = 0; i < imageList.length(); i++)
            {
                JSONObject imJ = imageList.getJSONObject(i);
                long imId =  imJ.getLong("id");
                Bitmap imBmp = decodeBase64(imJ.getString("data"));
                myImage curImage = new myImage(imId,imBmp);
                mImageList.add(curImage);

            }
            return true;
        }
    }

    public Boolean setUserInfo(String aboutme, Boolean available) throws IOException, JSONException {
        String toSend = "/setuserinfo";
        String av = available? "True":"False";
        String postData = "aboutme=" + aboutme + "&available=" + av;
        JSONObject response = sendPOSTRequest(toSend,postData,true);
        if (response == null) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updatePictures(String pic)throws IOException, JSONException {
        String toSend = "/updateuserimage";
        String postData = "images=" + pic;
        JSONObject response = sendPOSTRequest(toSend,postData,true);
        if (response == null) {
            return false;
        } else {
            JSONArray imageList = response.getJSONArray("images");
            imFbList = new LinkedList<>();
            for(int i = 0; i < imageList.length(); i++)
            {
                JSONObject imJ = imageList.getJSONObject(i);
                long imId =  imJ.getLong("id");
                Bitmap imBmp = decodeBase64(imJ.getString("data"));
                myImage curImage = new myImage(imId,imBmp);
                imFbList.add(curImage);

            }
            return true;
        }
    }

    public Boolean fetchPicture(long id) throws IOException, JSONException {
        String toSend = "/gethdpicture";
        String postData = "id=" + id;
        JSONObject response = sendPOSTRequest(toSend,postData,true);
        if (response == null) {
            return false;
        } else {
            String data = response.getString("data");
            lastPicture = decodeBase64(data);
            return true;
        }
    }

    public Boolean createEvent(String title, String Desc, int hour, int min, int day,int month,int year,
                               double lat, double lon, Drawable im) throws IOException, JSONException
    {
        String toSend = "/createevent";
        String postData = "lat=" + lat + "&long=" + lon;
        postData += "&title=" + title;
        postData += "&desc=" + Desc;

        postData += "&jour=" + day;
        postData += "&mois=" + month;
        postData += "&an=" + year;
        postData += "&heure=" + hour;
        postData += "&min=" + min;

        if(im != null)
        {

        }
        JSONObject response = sendPOSTRequest(toSend,postData,true);

        if (response == null) {
            return false;
        } else {
            return true;
        }
    }


    public LinkedList<myImage> getMyImages()
    {
        return imFbList;
    }

    public Bitmap getmUserpicture() {
        return mUserpicture;
    }

    public String getmFirstname() {
        return mFirstname;
    }

    public String getmAboutme() {
        return mAboutme;
    }

    public boolean ismAvailable() {
        return mAvailable;
    }

    public String getmCity() {
        return mCity;
    }

    public LinkedList<myImage> getUserImages(){ return mImageList;}

    public Bitmap getLastPicture() {
        return lastPicture;
    }

    public int getmAge() {
        return mAge;
    }
}
