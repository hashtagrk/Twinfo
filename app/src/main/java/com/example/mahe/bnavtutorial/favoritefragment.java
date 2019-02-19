package com.example.mahe.bnavtutorial;


//onlongclick listener for deleting.
//clear all button.

        import android.content.Context;
        import android.database.Cursor;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.annotation.Nullable;
        import android.support.v4.app.Fragment;
        import android.view.ActionMode;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AbsListView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListAdapter;
        import android.widget.ListView;

        import java.util.ArrayList;


public class favoritefragment extends Fragment {
    DatabaseHelper mDatabaseHelper;
    private ListView mListView;
    Button clearall;
    Context mcontext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vw = inflater.inflate(R.layout.fragment_favorites, container, false);
        mListView = vw.findViewById(R.id.listvw);
        mDatabaseHelper = new DatabaseHelper(getActivity());
        clearall=vw.findViewById(R.id.clearall);
        clearall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.clearall();
                populateListView();
            }
        });
       populateListView();
        return vw;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mcontext=context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void populateListView() {
        //Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        //listData.add("hello");
        try{
        while (data.moveToNext()) {
            listData.add(data.getString(1));
        }
     }
        finally {
            if (data != null && !data.isClosed())
                data.close();
        }
        mDatabaseHelper.close();
        ListAdapter adapter = new ArrayAdapter<>(mcontext, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);

        mListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        mListView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }
}
