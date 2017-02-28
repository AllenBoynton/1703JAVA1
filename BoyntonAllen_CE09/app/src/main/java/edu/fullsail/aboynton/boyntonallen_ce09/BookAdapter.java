// Allen Boynton

// JAV1 - 1703

// BookAdapter.java

package edu.fullsail.aboynton.boyntonallen_ce09;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import edu.fullsail.aboynton.boyntonallen_ce09.model.Book;

// Creating adapter for grid view to display titles and images
class BookAdapter extends BaseAdapter {

    private static final String TAG = "BookAdapter.TAG";

    private final Context context;
    private final ArrayList<Book> bookList;

    // Set Constructor
    BookAdapter(Context context, ArrayList<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    // Getters are inclusive with a custom adapter - Gets size of array list
    @Override
    public int getCount() {
        if (bookList.size() != 0) {
            return bookList.size();
        }
        Log.i(TAG, " <---- getCount(): " + bookList.size());
        return 0;
    }

    @Override
    public Object getItem(int pos) {
        return bookList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.i(TAG, " ----> getView()");

        // Helps the layout of the parsed data to display
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_book, parent, false);

        //Display book name in the TextView widget
        Book book = bookList.get(position);

        // Book title from google book
        ImageView image = (ImageView) convertView.findViewById(R.id.grid_item_image);
        TextView tv = (TextView) convertView.findViewById(R.id.grid_item_title);
        tv.setText(book.getTitle());

        // Boom image from google book
        String imageUrl = book.getThumbnail();

        // Imported Picasso Maven
        Picasso.with(context)
                .load(imageUrl)
                .into(image);

        Log.i(TAG, " <---- GetView() Title: " + book.getTitle() + "Book Cover: " + book.getThumbnail());
        return convertView;
    }
}
