package com.markelmendizabal.dualactivity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class ActivityDos extends ActionBarActivity implements View.OnClickListener{
    private TextView txtMessage;
    private Button btnSend;
    private TextView textLabel;
    private Button btnClose;
   // private String KEY_ITEM="key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_dos);
        txtMessage= (TextView) findViewById(R.id.txtMessage);
        btnSend= (Button) findViewById(R.id.btnSend);
        textLabel= (TextView) findViewById(R.id.txtLabel);
        btnClose= (Button) findViewById(R.id.btnClose);
        btnClose.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        procesIntent();

    }

    private void procesIntent() {
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.KEY_TEXT);
        textLabel.setText(message);

    }

    @Override
    public void onClick(View v) {
    if(v.getId()==R.id.btnSend){
        String message=txtMessage.getText().toString();
        if(message.length()>0){
            Intent intent=new Intent();
            intent.putExtra(MainActivity.KEY_TEXT,txtMessage.getText().toString());
            setResult(RESULT_OK,intent);
            finish();

        }else{
            Toast toast= Toast.makeText(ActivityDos.this,"",Toast.LENGTH_SHORT);
            toast.show();
            //getresources()getString(R.string.);
        }

    }else if(v.getId()==R.id.btnClose){
        setResult(RESULT_CANCELED);
        finish();
    }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_dos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
