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
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class CreateRoomActivity extends AppCompatActivity {

    private Document htmlDoc;
    private String htmlURL = "http://puzzledragonx.com/en/multiplayer-dungeons.asp";
    private String htmlContentString;
    private ArrayList<Category> dungeonList;
    private ArrayList<String> categoryList;
    private ArrayAdapter<String> adapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        categoryList = new ArrayList<String>();
        listView = (ListView)findViewById(R.id.listView);
        JsoupAsyncTask task = new JsoupAsyncTask();
        task.execute();
        adapter = new ArrayAdapter<String>(this, R.layout.list_layout, categoryList);
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("preexecute", "test");
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                htmlDoc = Jsoup.connect(htmlURL).get();
                htmlContentString = htmlDoc.title();

                Elements test = htmlDoc.select("table#tabledrop");
                dungeonList = new ArrayList<Category>();
                int counter = -1;
                for(Element row : test.select("tr")) {
                    Log.d("test", "1");
                    String temp = row.text();
                    int len = temp.length();
                    //special case, skip this.
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
                        //Log.d("parsetest--", temp);
                        dungeonList.get(counter).addDungeon(temp);
                    }
                    // digits at end indicates dungeon
                    else if(Character.isDigit(temp.charAt(len-1)) && Character.isDigit(temp.charAt(len-2))) {
                        //Log.d("parsetestdigit", temp);
                        dungeonList.get(counter).addDungeon(temp);
                    }
                    // empty indicates category
                    else {
                        categoryList.add(temp);
                        dungeonList.add(new Category(temp));
                        counter++;
                    }
                }

                for(int i = 0; i < dungeonList.size(); i++) {
                    Category temp = dungeonList.get(i);
                    Log.d("dungeoncategories", temp.getCat());
                    temp.print();

                }


                Log.d("test", "HTML CONTENT STRING " + htmlContentString);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //parsedHTMLNode.setText(htmlContentString);
            Log.d("test", "htmlContentString \n" + htmlContentString);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    // Start your Activity according to the item just clicked.
                    Log.d("test", "yes!");
                    Intent intent = new Intent(CreateRoomActivity.this, SelectDungeon.class);
                    //intent.putExtra("dungeonCatID", position);
                    intent.putExtra("dungeonCatID", "sending string");
                    startActivity(intent);
                }
            });
        }
    }
}
