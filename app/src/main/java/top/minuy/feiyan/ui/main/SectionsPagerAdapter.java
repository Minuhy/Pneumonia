package top.minuy.feiyan.ui.main;

import android.content.Context;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.Map;

import top.minuy.feiyan.R;
import top.minuy.feiyan.WebBearn;
import top.minuy.feiyan.url4web;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    final static String TAG = "适配器";

    private url4web u4;

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        position = position + 1;
        u4 = url4web.getU4();
        WebBearn wb = u4.getUrl().get(String.valueOf(position));
        if(wb == null) {
            return "Null";
        }
        if (wb.getTitle() == null) {
            return "Null";
        }
        Log.i(TAG,u4.getUrl().get(String.valueOf(position)).getTitle());
        return u4.getUrl().get(String.valueOf(position)).getTitle();
    }

    @Override
    public int getCount() {
        u4 = url4web.getU4();
        return u4.getUrl().size();
    }
}