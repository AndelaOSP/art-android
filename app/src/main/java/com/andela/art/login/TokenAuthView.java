package com.andela.art.login;

import com.andela.art.root.View;

/**
 * Created by zack on 4/18/18.
 */

public interface TokenAuthView extends View {

    /**
     * Report error retrieving token.
     * @param exception - exception
     */
    void reportError(Exception exception);
}
