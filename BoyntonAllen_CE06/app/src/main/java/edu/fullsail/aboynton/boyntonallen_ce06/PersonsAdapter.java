// Allen Boynton

// JAV1 - 1703

// PersonsAdapter.java

package edu.fullsail.aboynton.boyntonallen_ce06;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.fullsail.aboynton.boyntonallen_ce06.Object.Person;

class PersonsAdapter extends BaseAdapter {

    private final Context context;
    private ArrayList<Person> personList = new ArrayList<>();

    PersonsAdapter(Context context, ArrayList<Person> personList) {
        this.context = context;
        this.personList = personList;
    }

    @Override
    public int getCount() {
        if (personList.size() > 0) {
            return this.personList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int pos) {
        return personList.get(pos);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_base, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        //Display photo, name, and birth date in TextView widget
        Person person = personList.get(position);

        holder.nameText.setText(person.getName());
        holder.birthdayText.setText(person.getBirthday());

        holder.picture.setImageResource(person.getPictureIds());

        return convertView;
    }

    // Keep all Images in array
    final Integer[] pictureIds = {R.drawable.gw, R.drawable.ja,
            R.drawable.tj, R.drawable.jm, R.drawable.jm2, R.drawable.jqa,
            R.drawable.aj, R.drawable.mvb, R.drawable.wh, R.drawable.dt
    };

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int pos) {
        return false;
    }

    private static class ViewHolder {

        // Views in the item layout
        private TextView nameText;
        private TextView birthdayText;
        private ImageView picture;

        /** Unable to get name and birthday elements into viewHolder. I followed your
         *  FS MDV JAV1 Custom Adapter video but it would not work for me in this case. */

        // Parameters from Person class
        ViewHolder(View view) {
            nameText = (TextView) view.findViewById(R.id.grid_item_name);
            birthdayText = (TextView) view.findViewById(R.id.grid_item_birthday);
            picture = (ImageView) view.findViewById(R.id.grid_item_picture);
        }
    }
}