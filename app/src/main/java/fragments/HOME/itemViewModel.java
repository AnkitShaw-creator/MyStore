package fragments.HOME;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import Items.items;

public class itemViewModel extends ViewModel {
    private final MutableLiveData<items> item = new MutableLiveData<>();

    public MutableLiveData<items> getItem() {
        return item;
    }

    public void setItem(items i) {
        item.setValue(i);
    }
}
