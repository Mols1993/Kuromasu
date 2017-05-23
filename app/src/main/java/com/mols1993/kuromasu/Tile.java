package com.mols1993.kuromasu;

import android.util.Log;

/**
 * Created by mols1 on 5/22/2017.
 */

public class Tile {
    private int state = 0, number = 0;
    private Tile top, bot, left, right;
    public Tile(int n){
        number = n;
    }

    /**
     *
     * @param a Top link
     * @param b Right link
     * @param c Bot link
     * @param d Left link
     */
    public void setLinks(Tile a, Tile b, Tile c, Tile d){
        top = a;
        right = b;
        bot = c;
        left = d;
    }

    public void showLinks(){
        Log.i("TopLink", Integer.toString(top.getNumber()));
        Log.i("RightLink", Integer.toString(right.getNumber()));
        Log.i("BotLink", Integer.toString(bot.getNumber()));
        Log.i("LeftLink", Integer.toString(left.getNumber()));
    }

    public int getNumber(){
        return number;
    }
}
