package in.premad.maisha_task.NearbyPlacesList.adapters;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import in.premad.maisha_task.NearbyPlacesList.entities.Result;
import in.premad.maisha_task.R;

/*
public class PlacesListAdapter extends ArrayAdapter<Result> {

    // View lookup cache



    private Context context;
    private List<Result> results;

    private static class ViewHolder {

        public TextView textViewName;
        public TextView textViewAddress;
        public ImageView imageViewPhoto;

    }




    public PlacesListAdapter(Context context, List<Result> results) {
        super(context, R.layout.place_row_layout, results);

        this.context = context;
        this.results = results;

    }



    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        // Get the data item for this position


        // Check if an existing view is being reused, otherwise inflate the view

        ViewHolder viewHolder; // view lookup cache stored in tag

        if (convertView == null) {

            // If there's no view to re-use, inflate a brand new view for row

            viewHolder = new ViewHolder();

            LayoutInflater inflater = LayoutInflater.from(getContext());

            convertView = inflater.inflate(R.layout.place_row_layout, parent, false);

            viewHolder.textViewName = (TextView) convertView.findViewById(R.id.textViewName);

            viewHolder.textViewAddress = (TextView) convertView.findViewById(R.id.textViewAddress);
            viewHolder.imageViewPhoto =  convertView.findViewById(R.id.imageViewPhoto);

            // Cache the viewHolder object inside the fresh view

            convertView.setTag(viewHolder);



        } else {

            // View is being recycled, retrieve the viewHolder object from tag

            viewHolder = (ViewHolder) convertView.getTag();

        }

        // Populate the data from the data object via the viewHolder object

        // into the template view.

        Result result = results.get(position);
        viewHolder.textViewName.setText(result.getName());
        viewHolder.textViewAddress.setText(result.getVicinity());
        Bitmap photo = null;
        try {
            photo = new ImageRequestAsk().execute(result.getIcon()).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        viewHolder.imageViewPhoto.setImageBitmap(photo);
        // Return the completed view to render on screen

        return convertView;





    }
    private class ImageRequestAsk extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                InputStream inputStream = new java.net.URL(params[0]).openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                return null;
            }
        }

    }


}
*/




public class PlacesListAdapter extends ArrayAdapter<Result> {

    private Context context;
    private List<Result> results;

    public PlacesListAdapter(Context context, List<Result> results) {
        super(context, R.layout.place_row_layout, results);
        this.context = context;
        this.results = results;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        try {
            ViewHolder viewHolder;
            if(view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(context).inflate(R.layout.place_row_layout,null);
                viewHolder.textViewName = view.findViewById(R.id.textViewName);
                viewHolder.textViewAddress = view.findViewById(R.id.textViewAddress);
                viewHolder.imageViewPhoto = view.findViewById(R.id.imageViewPhoto);
                view.setTag(viewHolder);
            } else  {
                viewHolder = (ViewHolder) view.getTag();
            }
            Result result = results.get(position);
            viewHolder.textViewName.setText(result.getName());
            viewHolder.textViewAddress.setText(result.getVicinity());
            Bitmap photo = new ImageRequestAsk().execute(result.getIcon()).get();
            viewHolder.imageViewPhoto.setImageBitmap(photo);
            return view;
        } catch (Exception e) {
            return view;
        }
    }

    public static class ViewHolder {
        public TextView textViewName;
        public TextView textViewAddress;
        public ImageView imageViewPhoto;
    }

    private class ImageRequestAsk extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                InputStream inputStream = new java.net.URL(params[0]).openStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (Exception e) {
                return null;
            }
        }

    }


}


