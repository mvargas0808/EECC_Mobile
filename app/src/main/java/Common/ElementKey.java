package Common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bryan on 1/8/2017.
 */

public class ElementKey {

    private String key;
    private String value;
    List<ElementKey> provincesArray;

    private ElementKey(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public ElementKey() {
        provincesArray = new ArrayList<ElementKey>();
    }

    public void addElement(String key, String value){
        provincesArray.add(new ElementKey(key,value));
    }

    public String getKey(String pValue){
        for(int x=0;x<provincesArray.size();x++) {
            if (provincesArray.get(x).value.equals(pValue)){
                return provincesArray.get(x).key;
            }
        }
        return "";
    }
}
