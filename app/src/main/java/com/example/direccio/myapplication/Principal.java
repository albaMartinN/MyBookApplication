package com.example.direccio.myapplication;



import java.util.Comparator;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;


public class Principal extends MainActivity {
    private BookData bookData;
    private ListView listView;
    public static final int THIS_ACTIVITY = 1;
    private static int pos;
    private List<Book> values;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ll_titol);
        setTitle("Books sort by title");
        checkMenuItem(1);
        bookData = new BookData(this);
        //ens permet llegir a la database
        bookData.open();

       /* bookData.createBook("Miguel Strogoff", "Jules Verne");
        bookData.createBook("Ulysses", "James Joyce");
        bookData.createBook("Don Quijote", "Miguel de Cervantes");
        bookData.createBook("Metamorphosis", "Kafka");*/

        values = bookData.getAllBooks_title();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        listView = (ListView) findViewById(android.R.id.list);
        ArrayAdapter<Book> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,values);
        //adapter.addAll(values);

        listView.setAdapter(adapter);
        //adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                showPopupMenu(view);
            }
        });

    }

    public void showPopupMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popup.getMenu());
        popup.show();
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
            public boolean onMenuItemClick(MenuItem item){
                switch(item.getItemId()) {
                    case R.id.btn_canviar:
                        Intent intent = new Intent(getApplicationContext(), PersonalEvaluation.class);
                        intent.putExtra("titulo_libro",values.get(pos).getTitle());
                        intent.putExtra("id_libro",values.get(pos).getId());
                        startActivity(intent);
                        return true;
                    case R.id.btn_eliminar:
                        Toast.makeText(getBaseContext(), "eliminas "+  values.get(pos).getTitle(), Toast.LENGTH_SHORT).show();
                        return true;
                    default: return false;
                }
            }
        });
    }




    // Basic method to add pseudo-random list of books so that
    // you have an example of insertion and deletion

    // Will be called via the onClick attribute
    // of the buttons in main.xml
   /* public void onClick(View view) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<Book> adapter = (ArrayAdapter<Book>) listView.getAdapter();
        Book book;
        switch (view.getId()) {
            case R.id.add:
                String[] newBook = new String[] { "Miguel Strogoff", "Jules Verne", "Ulysses", "James Joyce", "Don Quijote", "Miguel de Cervantes", "Metamorphosis", "Kafka" };
                int nextInt = new Random().nextInt(4);
                // save the new book to the database
                book = bookData.createBook(newBook[nextInt*2], newBook[nextInt*2 + 1]);

                // After I get the book data, I add it to the adapter
                adapter.add(book);
                break;
            case R.id.delete:
                if (listView.getAdapter().getCount() > 0) {
                    book = (Book) listView.getAdapter().getItem(0);
                    bookData.deleteBook(book);
                    adapter.remove(book);
                }
                break;
        }
        adapter.notifyDataSetChanged();
    }*/
    // Life cycle methods. Check whether it is necessary to reimplement them


    @Override
    protected void onResume() {
        bookData.open();
        super.onResume();
        checkMenuItem(THIS_ACTIVITY);
    }

    // Life cycle methods. Check whether it is necessary to reimplement them

    @Override
    protected void onPause() {
        bookData.close();
        super.onPause();
    }

}