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
//    private String htmlURL = "http://google.com";
    private TextView parsedHTMLNode;
    private String htmlContentString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        JsoupAsyncTask task = new JsoupAsyncTask();
        task.execute();
        ListView listView = (ListView)findViewById(R.id.listView);
        ArrayList<String> test = new ArrayList<String>();

        test.add("numero1");
        test.add("number 2");
        test.add("number 3");
        test.add("number 4");
        test.add("number 5");
        test.add("number 6");
        test.add("number 7");
        test.add("number 8");
        test.add("number 9");
        test.add("number 10");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_layout, test);
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

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                htmlDoc = Jsoup.connect(htmlURL).get();
                htmlContentString = htmlDoc.title();

                /*
                //This section of code is able to grab all of the dungeons we need (plus Home, which we can remove)
                //However, we cannot efficiently divide it.
                Elements links = htmlDoc.select("a[href]");

                Log.d("test", "\nLinks: " + String.valueOf(links.size()));
                for (Element link : links) {
                    Log.d("test", link.text());
                    if(link.text().equals("Home")) {
                        Log.d("test", "equals");
                        break;
                    }
                }*/
                Elements test = htmlDoc.select("table#tabledrop");
                for(Element row:test.select("tr")) {
                    Log.d("test", row.text());
                }

//                StringBuilder table = new StringBuilder();
//                table.append(test.text());
//                //Category
//                //Dungeon
//                for(int i = 0; i < table.length(); i++) {
//                    String temp = "";
//
//                }
/* cases to skip:
*   After category, can skip X amount of spaces. Category ends when we hit STA.
*   After each dungeon we can skip X amount of spaces too.
* */


//                for (Element link : test) {
//                    Log.d("test", link.text());
//                    if (link.text().equals("Home")) {
//                        Log.d("test", "equals");
//                        break;
//                    }
//                }




//                Log.d("test", dungeonNames.text());
//                for(Element link:dungeonNames)
//                    Log.d("test", dungeonNames.text());
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
        }
    }
}
