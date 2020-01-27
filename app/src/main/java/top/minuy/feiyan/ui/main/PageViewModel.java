package top.minuy.feiyan.ui.main;

import android.text.TextUtils;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.Map;

import top.minuy.feiyan.url4web;

public class PageViewModel extends ViewModel {

    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            url4web u4 = url4web.getU4();
            if(TextUtils.isEmpty(u4.getUrl().get(String.valueOf(input)).getUrl())){
                return "http://minuy.top";
            }
            return u4.getUrl().get(String.valueOf(input)).getUrl();
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }
}