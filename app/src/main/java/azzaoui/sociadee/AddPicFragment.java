package azzaoui.sociadee;


import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddPicFragment extends Fragment implements SociadeeFragment {

    private gridAddPicAdapter mGridAddPicAdapter;
    private LinkedList<Long> SelectedPic ;
    private final int PicNumbers = 200 ; // 4 * 7
    private int mCurrentNumber = 0;
    private int mDownloadedNumber = 0;
    private JSONArray imageArrayId;
    private boolean mSaveButton = false;
    private MainActivity.CallBackTopButton mTopButtonCallback;

    public AddPicFragment() {
        // Required empty public constructor
        SelectedPic = new LinkedList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

       View v = inflater.inflate(R.layout.fragment_add_pic, container, false);

        SelectedPic.clear();
        Iterator<FacebookImage> iter = Parameters.getFacebookImages().listIterator();
        while (iter.hasNext() ) {
           SelectedPic.add(iter.next().getId());
        }


        GridView gridView = (GridView)v.findViewById(R.id.GridLayoutAddPic);
        mGridAddPicAdapter = new gridAddPicAdapter(getContext());
        gridView.setAdapter(mGridAddPicAdapter);
        return v;
    }


    // TODO: A tester
    public void validatePic(View v)
    {
        ImageView isAdded = (ImageView) v.getTag(R.id.validatedPic);
        long imId = (long) v.getTag(R.id.FACEBOOK_ID);
        if(isAdded.getVisibility() == View.VISIBLE)
        {
            isAdded.setVisibility(View.INVISIBLE);
            SelectedPic.remove(imId);
        }
        else
        {
            SelectedPic.add(imId);
            isAdded.setVisibility(View.VISIBLE );
        }
    }

    private boolean isSelected(long id)
    {
        boolean res = false;

        Iterator<Long> iter = SelectedPic.listIterator();
        while (iter.hasNext() && !res) {
            res = iter.next() == id ;
        }
        return res;
    }

    public void retrievePictures()
    {
        mGridAddPicAdapter.clearItem();
        mCurrentNumber = 0;
        mDownloadedNumber = 0;
        Bundle parameters = new Bundle();
        parameters.putString("limit", Integer.toString(PicNumbers));
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/photos",
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONObject firstResponse = response.getJSONObject();
                        try {
                            imageArrayId = firstResponse.getJSONArray("data");
                            mCurrentNumber = imageArrayId.length();
                            for (int i = 0; i < 4 && i < mCurrentNumber; i++)
                            {
                                fetchImageUrl( imageArrayId.getJSONObject(i).getLong("id"));
                            }

                        } catch (JSONException e) {


                        }

                    }
                }
        ).executeAsync();
    }
    private void showSaveButton()
    {
        mTopButtonCallback.fadeIn(R.drawable.saveicon);
        mSaveButton = true;
    }
    private void fetchImageUrl(final long id)
    {
        Bundle parameters = new Bundle();
        parameters.putString("fields", "images");
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+id,
                parameters,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
                        JSONObject firstResponse = response.getJSONObject();
                        try {
                            JSONArray imagesIds = firstResponse.getJSONArray("images");
                            String imageUrl = imagesIds.getJSONObject(imagesIds.length()-1).getString("source");
                            URL image_value = new URL(imageUrl);
                            Drawable newPic = Drawable.createFromStream(image_value.openConnection().getInputStream(), "blah");

                            mGridAddPicAdapter.addItem(new gridAddPicAdapter.Item(id, newPic,isSelected(id)));
                            mGridAddPicAdapter.notifyDataSetChanged();
                            addedPic();

                        } catch (JSONException e) {
                            e.printStackTrace();

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
        ).executeAsync();

    }

    private void addedPic()
    {
        mDownloadedNumber++;
        if(mDownloadedNumber%4 == 0)
        {
            int i=0;
            int mOldDownloadedNumber = mDownloadedNumber;
            while(i < 4 && mOldDownloadedNumber + i < mCurrentNumber)
            {
                try {
                    fetchImageUrl(imageArrayId.getJSONObject(mOldDownloadedNumber+i).getLong("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                i++;
            }
        }
    }

    @Override
    public void setButtonCallback(MainActivity.CallBackTopButton myCallback) {
        mTopButtonCallback = myCallback;
    }

    @Override
    public void onFragmentEnter() {

    }

    @Override
    public void onFragmentLeave() {

    }

    @Override
    public void onTopMenuMenuButtonClick() {

    }
}
