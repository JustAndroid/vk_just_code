package com.nik.smartnote.vk.util;

import android.content.Context;

import com.nik.smartnote.vk.Model.User;
import com.nik.smartnote.vk.R;

/**
 * Created by Николай on 07.01.2016.
 */
public class PrissonManager {


    public static final String[] MODS = {"simple", "cool", "epic"};

    public static final int[][] BOSSES = {
            {1, R.string.kirpich, R.drawable.kirpich, R.string.xp_kirpich},
            {2, R.string.sizyi, R.drawable.sizyi, R.string.xp_sizyi},
            {3, R.string.mahno, R.drawable.mahno, R.string.xp_mahno},
            {4, R.string.lutyi, R.drawable.lutyi, R.string.xp_lutyi},
            {5, R.string.shaiba, R.drawable.shaiba, R.string.xp_shaiba},
            {42, R.string.mezen, R.drawable.mezen, R.string.xp_mezen},
            {14, R.string.burat, R.drawable.burat, R.string.xp_burat},
            {16, R.string.dada_masha, R.drawable.dada_masha, R.string.xp_dada_masha},
            {27, R.string.bandak, R.drawable.bandak, R.string.xp_bandak},
            {45, R.string.abu, R.drawable.abu, R.string.xp_abu},
            {34, R.string.grizli, R.drawable.grizli, R.string.xp_grizli},
            {30, R.string.vorkuta, R.drawable.vorkuta, R.string.xp_vorkuta},
            {11, R.string.hirurg, R.drawable.hirurg, R.string.xp_hirurg},
            {6, R.string.palych, R.drawable.palych, R.string.xp_palych},
            {7, R.string.cyklop, R.drawable.cyklop, R.string.xp_cyklop},
            {12, R.string.raisa, R.drawable.raisa, R.string.xp_raisa},
            {8, R.string.bes, R.drawable.bes, R.string.xp_bes},
            {9, R.string.palenyi, R.drawable.palenyi, R.string.xp_palenyi},
            {13, R.string.bliznecy, R.drawable.bliznecy, R.string.xp_bliznecy},
            {10, R.string.borzov, R.drawable.borzov, R.string.xp_borzov},
            {43, R.string.ehan, R.drawable.ehan, R.string.xp_ehan},
            {26, R.string.konvoi, R.drawable.konvoi, R.string.xp_konvoi},
            {15, R.string.dubel, R.drawable.dada_masha, R.string.xp_konvoi},

//52 старшой
//19 бугор
            //50 змей

    };

    public int getBoss(int id){

        for (int i = 0;i < BOSSES.length; i++) {
            System.out.println(i);
          if(BOSSES[i][0] == id)   {
              return i;


          }

    }
        return -1;
    }

   public void kickBoss(Context context, int id){

        HTTPHelper.getInstance().requestGet(
                "http://109.234.156.252/prison/universal.php?method=hitBoss&spell_id=7&key=" + User.getInstance().getAuth_key(context) + "&amount=1&boss_id="
                        + id + "&user=" + User.getInstance().getUser_id(context),
                null
        );
    }

    public int attackBoss(int id, String mode, Context context) {
        String rezult = HTTPHelper.getInstance().requestGet(
                "http://109.234.156.250/prison/universal.php?key=" + User.getInstance().getAuth_key(context)
                        + "&buff=0&method=startBattle&user=" + User.getInstance().getUser_id(context) + "&boss%5Fid="
                        + id + "&type=boss&guild%5Fmode=0&choice=p&mode=" + mode
                , null
        );


        String xml = new XMLParser().parsXMLTeg(rezult, "code");
        if (xml == null) {
            return 10;
        }

        return Integer.parseInt(xml);
    }

    public String fightInfo(int code) {
        switch (code) {

            case 4:
                return "Бой уже начат";

            case 0:
                return "Напали на босса";

            case 6:
                return "Лимит нападений на этого босса исчерпан";

            case 2:
                return "Нет доступа к боссу, ключей ноль";

        }
        return "Ошибка при нападении!!!";

    }


public String getUSerInfo(int id, Context context){

    return HTTPHelper.getInstance().requestGet("http://109.234.156.250/prison/universal.php?key="
            + User.getInstance().getAuth_key(context) + "&with_guild=1&user="
            + User.getInstance().getUser_id(context) + "&method=getFriendModels&friend_uid=" + Integer.toString(id), null);
}

}
