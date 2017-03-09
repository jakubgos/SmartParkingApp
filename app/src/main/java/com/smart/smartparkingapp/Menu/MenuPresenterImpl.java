package com.smart.smartparkingapp.Menu;

import android.view.View;

import com.smart.smartparkingapp.Menu.Interfaces.MenuPresenterOps;
import com.smart.smartparkingapp.Menu.Interfaces.MenuViewOps;
import com.smart.smartparkingapp.login.Data.LoginServiceImpl;
import com.smart.smartparkingapp.login.Interfaces.LoginViewOps;
import com.smart.smartparkingapp.login.LoginModel;

import java.lang.ref.WeakReference;

/**
 * Created by Bos on 2017-03-09.
 */
public class MenuPresenterImpl implements MenuPresenterOps
{


    private WeakReference<MenuViewOps> menuViewOps;

    public MenuPresenterImpl(MenuViewOps menuViewOps) {
        this.menuViewOps = new WeakReference<>(menuViewOps);
        //this.loginModelOps = new LoginModel(this, new LoginServiceImpl());
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
