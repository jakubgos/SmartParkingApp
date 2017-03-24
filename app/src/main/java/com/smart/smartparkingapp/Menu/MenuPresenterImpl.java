package com.smart.smartparkingapp.menu;

import com.smart.smartparkingapp.menu.interfaces.MenuPresenterOps;
import com.smart.smartparkingapp.menu.interfaces.MenuViewOps;

import java.lang.ref.WeakReference;

/**
 * Created by Bos on 2017-03-09.
 */
public class MenuPresenterImpl implements MenuPresenterOps
{


    private WeakReference<MenuViewOps> menuViewOps;

    public MenuPresenterImpl(MenuViewOps menuViewOps) {
        this.menuViewOps = new WeakReference<>(menuViewOps);
        //this.loginModelOps = new LoginModel(this, new NetworkServiceImpl());
    }



    private MenuViewOps getView() throws NullPointerException {
        if ( menuViewOps != null )
            return menuViewOps.get();
        else
            throw new NullPointerException("View is unavailable");
    }


    @Override
    public void onMapSelect() {
        getView().loadMap();

    }
}
