package com.markelmendizabal.dualactivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private TextView txtmessage;
    private Button btnSend;
    private TextView lblMessage;
    private ImageView imageCamara;
    private Button btnPhoto;
    private final  int KEY_ITEM=1;
    private final  int KEY_IMAGE=2;
    public static final String KEY_TEXT="key";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtmessage= (TextView) findViewById(R.id.textMessage);
        btnSend= (Button) findViewById(R.id.btnSend);
        lblMessage= (TextView) findViewById(R.id.lblMessage);
        imageCamara= (ImageView) findViewById(R.id.imgCamara);
        btnPhoto= (Button) findViewById(R.id.btnPhoto);
        btnPhoto.setOnClickListener(this);
        btnSend.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
      if (v.getId()==R.id.btnSend){
          String msg= txtmessage.getText().toString();
          if(KEY_TEXT.length()>0){
              Intent intent=new Intent(MainActivity.this, ActivityDos.class);
              intent.putExtra(KEY_TEXT,msg);
              startActivityForResult(intent,KEY_ITEM);

          }else{
              Toast toast= Toast.makeText(MainActivity.this,"",Toast.LENGTH_SHORT);
              toast.show();
              //getresources()getString(R.string.);
          }



      }else if (v.getId()==R.id.btnPhoto){
          Intent takePictureIntent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          if(takePictureIntent.resolveActivity(getPackageManager())!=null){
              startActivityForResult(takePictureIntent,KEY_IMAGE);
          }

      }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_CANCELED){
            switch (requestCode){
                case KEY_ITEM:
                    String message=data.getStringExtra(KEY_TEXT);
                    lblMessage.setText(message);
                    break;
                case KEY_IMAGE:
                    Bundle extras =data.getExtras();
                    Bitmap imageBitmap= (Bitmap) extras.get("data");
                    imageCamara.setImageBitmap(imageBitmap);
                    break;
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
