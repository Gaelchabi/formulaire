package chabi.gael.formulaire;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Person;
import android.content.DialogInterface;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.app.ProgressDialog.show;
import static java.nio.file.Files.delete;

public class ListeActivity extends AppCompatActivity {

    ArrayList < String > Prenom = new ArrayList < String > ();
    ListView mListView;
    ArrayAdapter<String> adapter;
    DataBaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste);
        Prenom.add("Gael");
        Prenom.add("Mariam");
        Prenom.add("Socole");
        Prenom.add("Idiot");
        Prenom.add("Amour");
        mListView = (ListView) findViewById(R.id.listView);
        adapter = new
                ArrayAdapter<String>(ListeActivity.this,
                android.R.layout.simple_list_item_1, Prenom);
        mListView.setAdapter(adapter);

        db= new DataBaseHelper(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        //.setAction("Action", null).show();
                ajouter(view);
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView <?> adapterView, View view , int i,long l){
                final int position = i;

//Toast.makeText(ListActivity.this,i+"",Toast.LENGTH_LONG).show();
                final String p = Prenom.get(i);
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(ListeActivity.this);
                builder.setTitle("Modification d'une prenom");
                final View form = getLayoutInflater().inflate(R.layout.form,null);
                builder.setView(form);
                //final EditText nomed = form.findViewById(R.id.nom);
                final EditText prenomed = form.findViewById(R.id.prenom);
                //nomed.setText(p.getNom());
                prenomed.setText(p);
                builder.setPositiveButton("Valider", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Prenom.set(position,prenomed.getText().toString());
                                adapter.notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("Annuler", new
                        DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                builder.show();
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int choix = i;
                final String p = Prenom.get(i);
                AlertDialog.Builder builder = new
                        AlertDialog.Builder(ListeActivity.this);
                builder.setTitle("Suppression d'un prenom");
                builder.setMessage("Voulez-vous vraiment supprimer cet prenom ?");
                builder.setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            Prenom.remove(choix);
                            adapter.notifyDataSetChanged();
                            }
                        });
                builder.setNegativeButton("Annuler",null);
                builder.show();
                return true;
            }
        });
    }

    public void ajouter(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ListeActivity.this);
        builder.setTitle("Ajout d'un prénom");
        final View form = getLayoutInflater().inflate(R.layout.form,null);
        builder.setView(form);
        builder.setPositiveButton("Valider", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText prenomed = form.findViewById(R.id.prenom);
                        Prenom.add(prenomed.getText().toString());
                        adapter.notifyDataSetChanged();
                    }
                });
        builder.setNegativeButton("Annuler", new
                DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int
                            i) {
                        Toast.makeText(ListeActivity.this,"Annuler",Toast.LENGTH_LONG).
                        show();
                    }
                });
        builder.show();
    }

}
