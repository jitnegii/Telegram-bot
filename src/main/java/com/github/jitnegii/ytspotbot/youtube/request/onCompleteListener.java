package com.github.jitnegii.ytspotbot.youtube.request;

import com.github.jitnegii.ytspotbot.youtube.models.Format;

import java.util.ArrayList;

public interface onCompleteListener {

    void onFailure(Error error);
    void onComplete(ArrayList<Format> formats);
}
