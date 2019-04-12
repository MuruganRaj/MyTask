package mytaskcom.user.nearrestaurants.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mytaskcom.user.nearrestaurants.R;
import mytaskcom.user.nearrestaurants.model.Result;

public class RestaurantAdapter extends BaseAdapter {

    private Activity activity;
    private List<Result> data;
    private static LayoutInflater inflater=null;

//    List<Result> list_res = new ArrayList<>();

    public RestaurantAdapter(Activity a, List<Result> d) {
        activity = a;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView artist = (TextView)vi.findViewById(R.id.artist); // artist name
        RatingBar rating = (RatingBar) vi.findViewById(R.id.rating); // duration
        ImageView thumb_image=(ImageView)vi.findViewById(R.id.list_image); // thumb image

        Result   list_res = data.get(position);

        Picasso.get().load(list_res.getIcon()).into(thumb_image);

        // Setting all values in listview
        title.setText(list_res.getName());
        artist.setText(list_res.getFormattedAddress());
        rating.setRating(list_res.getRating());

        return vi;
    }
}
