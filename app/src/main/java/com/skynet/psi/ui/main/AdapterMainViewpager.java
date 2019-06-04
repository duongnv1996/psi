package com.skynet.psi.ui.main;

import android.util.SparseArray;
import android.view.ViewGroup;

import com.skynet.psi.ui.category.ListCategoryFragment;
import com.skynet.psi.ui.favourite.FavouriteFragment;
import com.skynet.psi.ui.home.HomeFragment;
import com.skynet.psi.ui.shop.ListShopFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class AdapterMainViewpager extends FragmentStatePagerAdapter {
    SparseArray<Fragment> registeredFragments = new SparseArray<>();

    public AdapterMainViewpager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:{
                return HomeFragment.newInstance();
            }
            case 1:{
                return ListCategoryFragment.newInstance();

            } case 2:{
                return ListCategoryFragment.newInstance();

            }case 3:{
                return FavouriteFragment.newInstance();

            }
            default:
                return HomeFragment.newInstance();
        }
    }
}
