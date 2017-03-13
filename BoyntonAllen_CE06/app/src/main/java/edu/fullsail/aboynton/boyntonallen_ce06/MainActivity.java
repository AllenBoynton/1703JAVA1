// Allen Boynton

// JAV1 - 1703

// MainActivity.java

package edu.fullsail.aboynton.boyntonallen_ce06;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.fullsail.aboynton.boyntonallen_ce06.Object.Person;

import static edu.fullsail.aboynton.boyntonallen_ce06.R.id.gridview;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ArrayList<Person> personList;

    //Display person classes for the widgets
    private PersonsAdapter personsAdapter;

    // OnClickListener for spinners and views clickable
    private PersonsOnItemSelectedListener personsOnItemSelectedListener =
            new PersonsOnItemSelectedListener();

    // 2 available views. 1 is Visible while other is Gone
    private ListView listView;
    private GridView gridView;

    private String[] fullNames;
    private String[] birthdays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "----> onCreate(): ON");

        // Names of persons
        fullNames = new String[] {
                "George Washington", "John Adams", "Thomas Jefferson", "James Madison", "James Monroe",
                "John Quincy Adams", "Andrew Jackson", "Martin Van Buren", "William Harrison", "Donald Trump"
        };

        // Birthdays of persons
        birthdays = new String[] {
                "2/22/1732", "10/30,1735", "4/14/1743", "3/16/1751", "4/28/1758",
                "7/11/1767", "3/15/1767", "12/5/1782", "2/9/1773", "6/14/1946"
        };

        //Initialize with empty data
        personList = new ArrayList<>();
        personsAdapter = new PersonsAdapter(this, personList);

        /** These are the screens for each view that would be Visible or Gone
         *  depending on the selection of the spinners.
         */
        // Views resource position
        listView = (ListView)findViewById(R.id.listView);
        gridView = (GridView)findViewById(gridview);

        // Adding all elements from each array to the final array list
        List<String> names = Arrays.asList(fullNames);

        // Displaying elements of the joined ArrayList
        for (String temp: names){
            Log.e(TAG, temp);
        }

        // Set array adapter for list view
        ArrayAdapter<String> nameListAdapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
        listView.setAdapter(nameListAdapter);

        // Set array adapter for grid view
//        ArrayAdapter<String> nameGridAdapter =
//                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, names);
//        gridView.setAdapter(nameGridAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Item at " + position + " tapped");
                personList.get(position);
                showDetailsAlert();
            }
        });

//        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "Item at " + position + " tapped");
//                personList.get(position);
//                showDetailsAlert();
//            }
//        });

        // Call methods to name spinners
        addListenerOnSpinnerItemSelection();

        // Call methods to show views
//        showPersonsListView(personList);
//        showPersonsGridView(personList);
    }

    private void addListenerOnSpinnerItemSelection() {
        Spinner viewSpinner = (Spinner) findViewById(R.id.viewSpinner);
        Spinner adapterSpinner = (Spinner) findViewById(R.id.adapterSpinner);
        viewSpinner.setOnItemSelectedListener(personsOnItemSelectedListener);
        adapterSpinner.setOnItemSelectedListener(personsOnItemSelectedListener);

        /** Attempted to get get values of each spinner view to set the view and adapter
        // View spinner view value
        String list_view = viewSpinner.getSelectedItem().toString().trim();
        String grid_view = viewSpinner.getSelectedItem().toString().trim();

        // Adapter spinner view value
        String array_view = viewSpinner.getSelectedItem().toString().trim();
        String simple_view = viewSpinner.getSelectedItem().toString().trim();
        String base_view = viewSpinner.getSelectedItem().toString().trim(); */
    }

//    public void showPersonsListView(ArrayList<Person> personList) {
//        gridView.setVisibility(View.GONE);
//        listView.setVisibility(View.VISIBLE);
//
//        listView.setAdapter(new PersonsAdapter(this, personList));
//    }

//    public void showPersonsGridView(ArrayList<Person> personList) {
//        listView.setVisibility(View.GONE);
//        gridView.setVisibility(View.VISIBLE);
//
//        gridView.setAdapter(new PersonsAdapter(this, personList));
//    }

    private void showDetailsAlert() {
        Log.d(TAG, "----> showDetailsAlert(): ON");

        // Sets AlertDialog class locally to be less dependant on the class
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        // Gets name detail of tapped object
        for (String fullName : fullNames) {
            builder.setTitle(fullName);
        }
        // Gets birthday detail of tapped object
        for (String birthday : birthdays) {
            builder.setMessage(birthday);
        }
        // Gets picture detail of tapped object
        for (int pictureId : personsAdapter.pictureIds) {
            builder.setIcon(pictureId);
        }

        // Setup of Okay button to dismiss dialog
        builder.setNegativeButton(R.string.okayDialogButton, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
            }
        });
        builder.create();
        builder.show();
        Log.d(TAG, "<---- showDetailsAlert(): OFF");
    }
}
