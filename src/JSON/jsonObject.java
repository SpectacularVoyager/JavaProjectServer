package JSON;

import java.util.List;
import java.util.ListIterator;

public class jsonObject {

    String s = "";


    public void addList(String name, List<?> list) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(",\"").append(name).append("\":[");
        ListIterator<?> iterator = list.listIterator();
        while (true) {
            Object o = iterator.next();
            stringBuilder.append("\"").append(o).append("\"");
            if (iterator.hasNext()) {
                stringBuilder.append(",");
            } else {
                break;
            }
        }
        stringBuilder.append("]");
        s += stringBuilder.toString();
    }

    public void addList(String name, List<?> list, boolean putQuotes) {
        if (putQuotes) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(",\"").append(name).append("\":[");
            ListIterator<?> iterator = list.listIterator();
            while (true) {
                Object o = iterator.next();
                stringBuilder.append("\"").append(o).append("\"");
                if (iterator.hasNext()) {
                    stringBuilder.append(",");
                } else {
                    break;
                }
            }
            stringBuilder.append("]");
            s += stringBuilder.toString();
        }else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(",\"").append(name).append("\":[");
            ListIterator<?> iterator = list.listIterator();
            while (iterator.hasNext()) {
                Object o = iterator.next();
                stringBuilder.append("").append(o).append("");
                if (iterator.hasNext()) {
                    stringBuilder.append(",");
                } else {
                    break;
                }
            }
            stringBuilder.append("]");
            s += stringBuilder.toString();
        }
    }


    public void addString(String name, String value) {
        s += String.format(",\"%s\":\"%s\"", name, value);
    }

    @Override
    public String toString() {
        if (s.isEmpty()) {
            return "";
        }
        return s.substring(1);//escape comma
    }
}
