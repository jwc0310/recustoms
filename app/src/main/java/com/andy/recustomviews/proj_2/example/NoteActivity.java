/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.andy.recustomviews.proj_2.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewAfterTextChangeEvent;
import com.andy.recustomviews.R;
import com.andy.recustomviews.activity.Base2Activity;
import com.andy.recustomviews.fragment.BaseFragment;
import com.andy.recustomviews.proj_2.GreenDaoManager;
import com.andy.recustomviews.proj_2.eg2.DaoSession;
import com.andy.recustomviews.proj_2.eg2.NoteDao;

import org.greenrobot.greendao.rx.RxDao;
import org.greenrobot.greendao.rx.RxQuery;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class NoteActivity extends Base2Activity {

    private EditText editText;
    private View addNoteButton;

    private RxDao<Note, Long> rxNoteDao;
    private RxQuery<Note> rxNotesQuery;
    private NotesAdapter notesAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.rx_green_dao;
    }

    @Override
    public void init(Bundle bundle) {
        setUpViews();

        // get the note DAO
        DaoSession daoSession = GreenDaoManager.getInstance().getmDaoSession();
//        noteDao = daoSession.getNoteDao();
        rxNoteDao = daoSession.getNoteDao().rx();

        // query all notes, sorted a-z by their text
        rxNotesQuery = daoSession.getNoteDao().queryBuilder().orderAsc(NoteDao.Properties.Text).rx();
        updateNotes();
    }

    private void updateNotes() {
        rxNotesQuery.list()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<Note>>() {
                    @Override
                    public void call(List<Note> notes) {
                        notesAdapter.setNotes(notes);
                    }
                });
    }

    protected void setUpViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewNotes);
        //noinspection ConstantConditions
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        notesAdapter = new NotesAdapter(noteClickListener);
        recyclerView.setAdapter(notesAdapter);

        addNoteButton = findViewById(R.id.buttonAdd);
        //noinspection ConstantConditions
        addNoteButton.setEnabled(false);

        editText = (EditText) findViewById(R.id.editTextNote);
        RxTextView.editorActions(editText).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        if (integer == EditorInfo.IME_ACTION_DONE){
                            addNote();
                        }
                    }
                });
        RxTextView.afterTextChangeEvents(editText).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<TextViewAfterTextChangeEvent>() {
                    @Override
                    public void call(TextViewAfterTextChangeEvent textViewAfterTextChangeEvent) {
                        boolean enable = textViewAfterTextChangeEvent.editable().length() > 0;
                        addNoteButton.setEnabled(enable);
                    }
                });
    }

    public void onAddButtonClick(View view) {
        addNote();
    }

    private void addNote() {
        String noteText = editText.getText().toString();
        editText.setText("");

        final DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        String comment = "Added on " + df.format(new Date());

        Note note = new Note();
        note.setText(noteText);
        note.setComment(comment);
        note.setDate(new Date());
        note.setType(NoteType.TEXT);

        rxNoteDao.insert(note)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Action1<Note>() {
            @Override
            public void call(Note note) {
                Log.d("DaoExample", "Inserted new note, ID: " + note.getId());
                updateNotes();
            }
        });
    }

    NotesAdapter.NoteClickListener noteClickListener = new NotesAdapter.NoteClickListener() {
        @Override
        public void onNoteClick(int position) {
            Note note = notesAdapter.getNote(position);
            final Long noteId = note.getId();

            rxNoteDao.deleteByKey(noteId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<Void>() {
                @Override
                public void call(Void aVoid) {
                    Log.d("DaoExample", "Deleted note, ID: " + noteId);
                    updateNotes();
                }
            });
        }
    };
}