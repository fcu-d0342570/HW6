package com.example.i1239.hw6;

/**
 * Created by i1239 on 2017/6/4.
 */


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class NoteEditor extends ActionBarActivity {

    EditText et_title, et_body;
    ArrayList<String> titlelist;
    SQLiteDatabase db;
    int notepos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);
        et_title = (EditText)findViewById(R.id.editText);
        et_body = (EditText)findViewById(R.id.editText2);

        Intent intent = getIntent();
        notepos = intent.getIntExtra("NOTEPOS", -1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DBOpenHelper openhelper = new DBOpenHelper(this);
        db = openhelper.getWritableDatabase();

        titlelist = NoteDB.getTitleList(db);

        if (notepos != -1) {
            String title = titlelist.get(notepos);
            et_title.setText(title);
            et_body.setText(NoteDB.getBody(db, title));
        } else {
            et_title.setText("");
            et_body.setText("");
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        String title = et_title.getText().toString();
        if (title.length() == 0) {
            Toast.makeText(this, "標題不能空白",
                    Toast.LENGTH_LONG).show();
        } else {
            NoteDB.addNote(db, et_title.getText().toString(),
                    et_body.getText().toString());
        }
    }

    boolean isTitleExist(String title) {
        for (int i = 0; i < titlelist.size(); i++)
            if (title.equalsIgnoreCase(titlelist.get(i)))
                return true;
        return false;
    }
}
