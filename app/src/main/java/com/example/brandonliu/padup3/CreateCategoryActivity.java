package com.example.brandonliu.padup3;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class CreateCategoryActivity extends AppCompatActivity {

    private Document htmlDoc;
    private String htmlURL = "http://puzzledragonx.com/en/multiplayer-dungeons.asp";
    private ArrayList<Category> dungeonList;    //used to hold all of our categories and respective dungeons
    private ArrayList<String> categoryList;     //used for our listview
    private ArrayAdapter<String> adapter;       //adapter for listview
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);

        categoryList = new ArrayList<String>();             //category list is the list of categories that we use in our listview
        listView = (ListView)findViewById(R.id.listView);
        //new task
        JsoupAsyncTask task = new JsoupAsyncTask();
        //execute task
        task.execute();
        //create adapter with these parameters; will be set on postExecution
        adapter = new ArrayAdapter<String>(this, R.layout.list_layout, categoryList);
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                //grab htmldoc
                htmlDoc = Jsoup.connect(htmlURL).get();

                //grab all elements in the table. Includes category, dungeon, and a few misc lines.
                Elements test = htmlDoc.select("table#tabledrop");
                //initialize dungeon list
                dungeonList = new ArrayList<Category>();
                //start at negative 1. Note, we always start at a new category.
                int counter = -1;
                //iterates through elements and parses according to category or dungeon.
                for(Element row : test.select("tr")) {
                    String temp = row.text();
                    int len = temp.length();
                    //special case, we want to skip this line.
                    if(temp.equals("STA BTL Coin Exp G/S E/S")) {
                        continue;
                    }
                    //hit the end. break
                    if(temp.equals("Remarks")) {
                        Log.d("end", temp);
                        break;
                    }
                    //special case for challenge dungeon since it ends with numbers.
                    if(temp.length() >= 17 && temp.substring(0, 17).equals("Challenge Dungeon")) {
                        //Log.d("parsechallenge", temp);
                        categoryList.add(temp);
                        dungeonList.add(new Category(temp));
                        counter++;
                    }
                    // -- at end indicates dungeon
                    else if(temp.charAt(len-1) == '-' && temp.charAt(len-2) == '-') {
                        temp = stripString(temp);
                        dungeonList.get(counter).addDungeon(temp);
                    }
                    // digits at end indicates dungeon
                    else if(Character.isDigit(temp.charAt(len-1)) && Character.isDigit(temp.charAt(len-2))) {
                        temp = stripString(temp);
                        dungeonList.get(counter).addDungeon(temp);
                    }
                    // in this case, there is no digits or "--" so we know it's a category.
                    else {
                        categoryList.add(temp);
                        dungeonList.add(new Category(temp));
                        counter++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    // Start your Activity according to the item just clicked.
                    Intent intent = new Intent(CreateCategoryActivity.this, CreateDungeonActivity.class);
                    //intent.putExtra("dungeonCatID", position);
                    intent.putExtra("cat", dungeonList.get(position));
                    startActivity(intent);
                }
            });
        }

        /**
         * This function strips the string of all (6) trailing dashes and numbers which correspond
         * to the stamina, floors, coins, etc.
         */
        private String stripString(String str) {
            String temp = "";
            int spaceCounter = 0;
            int i;
            for(i = str.length()-1; i >= 0; i--) {
                if(str.charAt(i) == ' ') {
                    spaceCounter++;
                }
                if(spaceCounter == 6) {
                    break;
                }
            }
            temp = str.substring(0, i);
            return temp;
        }
    }
}
