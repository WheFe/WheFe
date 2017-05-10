package com.example.chun.whefe.fragments;

import java.io.Serializable;

/**
 * Created by Chun on 2017-05-08.
 */

public interface FragmentReplaceable extends Serializable{

    public void onFragmentChanged(int fragmentId);
}
