// Allen Boynton

// JAV1 - 1703

// BookJSONParser.java

package edu.fullsail.aboynton.boyntonallen_ce09.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import edu.fullsail.aboynton.boyntonallen_ce09.model.Book;

public class BookJSONParser {

    // Parsing JSON data downloaded asynchronously
    public static ArrayList<Book> parseFeed(String data) {

        try {
            JSONObject outerMostObject = new JSONObject(data);
            JSONArray booksJson = outerMostObject.getJSONArray("items");

            ArrayList<Book> bookList = new ArrayList<>();

            for (int i = 0; i < booksJson.length(); ++i) {
                JSONObject items = booksJson.getJSONObject(i);
                JSONObject volumeInfo = items.getJSONObject("volumeInfo");
                JSONObject imageLinks = volumeInfo.getJSONObject("imageLinks");

                Book book = new Book();

                // Using setter from Book to set title and image to gridView
                book.setTitle(volumeInfo.getString("title"));
                book.setThumbnail(imageLinks.getString("thumbnail"));

                bookList.add(book);
            }
            return bookList;
        }
        catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
