package br.com.infoglobo.androidmvp.view.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import br.com.infoglobo.androidmvp.R;
import br.com.infoglobo.androidmvp.base.ServerAPI;
import br.com.infoglobo.androidmvp.presenter.MainPresenter;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusActivity<MainPresenter> {

    CheckedTextView check1;
    CheckedTextView check2;
    ArrayAdapter<ServerAPI.Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check1 = (CheckedTextView)findViewById(R.id.check1);
        check2 = (CheckedTextView)findViewById(R.id.check2);

        check1.setText(MainPresenter.NAME_1);
        check2.setText(MainPresenter.NAME_2);

        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().request(MainPresenter.NAME_1);
            }
        });
        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPresenter().request(MainPresenter.NAME_2);
            }
        });

        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(adapter = new ArrayAdapter<>(this, R.layout.item));

        if (savedInstanceState == null)
            getPresenter().request(MainPresenter.DEFAULT_NAME);

    }

    public void onItens(ServerAPI.Item[] itens, String user) {
        check1.setChecked(user.equals(MainPresenter.NAME_1));
        check2.setChecked(user.equals(MainPresenter.NAME_2));

        adapter.clear();
        adapter.addAll(itens);
    }

    public void onNetworkError(Throwable throwable) {
        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_LONG).show();
    }
}
