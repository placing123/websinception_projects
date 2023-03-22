package com.websinception.megastar.UI.winnerNumberSelection;

import android.content.Context;

import com.websinception.megastar.beanOutput.WinBreakupOutPut;

import java.util.List;



/**
 * Created by mobiweb on 6/12/16.
 */
public interface WinBreakupCallback {
    public void close(int position);

    public Context getContext();


    public List<WinBreakupOutPut.DataBean> getBean();

    public int getIndex();
}