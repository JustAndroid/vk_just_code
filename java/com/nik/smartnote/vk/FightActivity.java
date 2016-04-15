package com.nik.smartnote.vk;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nik.smartnote.vk.util.HTTPHelper;
import com.nik.smartnote.vk.util.PrissonManager;
import com.nik.smartnote.vk.util.XMLParser;

/**
 * Created by Николай on 21.02.2016.
 */
public class FightActivity extends Activity {

    ImageView imageViewBoss;
    ImageButton imageButtonJad;
    ImageButton imageButtonFinka;
    ImageButton imageButtonUdarVPah;
    ImageButton imageButtonPalcemVGlaz;
    ImageButton imageButtonSamopal;
    ImageButton imageButtonJPyromVSolnyshko;
    ProgressBar progressBarLifeBoss;
    TextView textViewMayUron;
    TextView textViewLifeBoss;
    TextView textViewNameBoss;
    int idBoss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fight_layout);

        ActionBar actionBar = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            actionBar = getActionBar();

            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        progressBarLifeBoss = (ProgressBar) findViewById(R.id.progressBar);
        imageViewBoss = (ImageView) findViewById(R.id.image_view_boss);
textViewLifeBoss = (TextView)findViewById(R.id.life_boss);
textViewNameBoss = (TextView)findViewById(R.id.text_name_boss);
        imageButtonUdarVPah = (ImageButton)findViewById(R.id.image_button_v_pah);

        Intent intent = getIntent();
        String helFull = intent.getStringExtra("helFull");
        if(helFull != null) {
            String nowHel = intent.getStringExtra("nowHel");

             idBoss = Integer.parseInt(intent.getStringExtra("idBoss"));

            imageViewBoss.setImageResource(PrissonManager.BOSSES[idBoss][2]);
            textViewNameBoss.setText(getResources().getString(PrissonManager.BOSSES[idBoss][1]));
            progressBarLifeBoss.setMax(Integer.parseInt(helFull));
            progressBarLifeBoss.setProgress(Integer.parseInt(nowHel));
            textViewLifeBoss.setText("Всего: " + helFull+ "\nОсталось: " + nowHel);

        }else{
            refreshData();
        }
    }
public void refresh(View v) {
   refreshData();

}
    public void refreshData(){
        String xmlRezult = new PrissonManager().getBossInfo(FightActivity.this);
        XMLParser xmlParser = new XMLParser();
        String status = xmlParser.parsXMLTeg(xmlRezult, "status");
        if (status != null) {
            switch (status) {
                case "win":
                    AlertDialog.Builder alertDialogB = new AlertDialog.Builder(FightActivity.this).setTitle("Победа");

                    alertDialogB.setMessage("Помянем " + xmlParser.parsXMLTeg(xmlRezult, "name"));
                    alertDialogB.setPositiveButton("К списку", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    AlertDialog alertDialog = alertDialogB.create();
                    alertDialog.show();

                    break;
                default:


                    //  statusTextView.setText("Проигрыш, либо бой не начат!!!");
                    break;
            }
        } else {
            String helFull = xmlParser.parsXMLTeg(xmlRezult, "h_full");

            if (helFull != null) {
                String nowHel = xmlParser.parsXMLTeg(xmlRezult, "h_now");

                idBoss = Integer.parseInt(xmlParser.parsXMLTeg(xmlRezult, "id"));
             int idxBoss = new PrissonManager().getBoss(idBoss);
                System.out.println(idBoss);

                progressBarLifeBoss.setMax(Integer.parseInt(helFull));
                progressBarLifeBoss.setProgress(Integer.parseInt(nowHel));
                imageViewBoss.setImageResource(PrissonManager.BOSSES[idxBoss][2]);
                textViewLifeBoss.setText("Всего: " + helFull+ "\nОсталось: " + nowHel);

            } else {
                finish();

            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.about:
                startActivity(new Intent(FightActivity.this, DeveloperActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


   public void onVPah(View v){

        new PrissonManager().kickBoss(FightActivity.this, idBoss,7);


     }
   public void onPyrom(View v){
       // new PrissonManager().kickBoss(FightActivity.this, idBoss,);

     }
}
