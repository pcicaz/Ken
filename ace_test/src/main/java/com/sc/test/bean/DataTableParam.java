package com.sc.test.bean;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author steven
 * @date 5/21/15
 */
public class DataTableParam {
    private String sEcho;
    private int iDisplayStart;
    private int iDisplayLength;
    private int iSortCol_0;
    private String sSortDir_0;
    private String[] columns = new String[10];

    public String getsEcho() {
        return sEcho;
    }

    public void setsEcho(String sEcho) {
        this.sEcho = sEcho;
    }

    public int getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(int iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public int getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(int iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }

    public int getiSortCol_0() {
        return iSortCol_0;
    }

    public void setiSortCol_0(int iSortCol_0) {
        this.iSortCol_0 = iSortCol_0;
    }

    public String getSortDir() {
        return sSortDir_0;
    }

    public void setsSortDir_0(String sSortDir_0) {
        this.sSortDir_0 = sSortDir_0;
    }

    public void setmDataProp_0(String arg) {
        columns[0] = arg;
    }

    public void setmDataProp_1(String arg) {
        columns[1] = arg;
    }

    public void setmDataProp_2(String arg) {
        columns[2] = arg;
    }

    public void setmDataProp_3(String arg) {
        columns[3] = arg;
    }

    public void setmDataProp_4(String arg) {
        columns[4] = arg;
    }

    public void setmDataProp_5(String arg) {
        columns[5] = arg;
    }

    public void setmDataProp_6(String arg) {
        columns[6] = arg;
    }

    public void setmDataProp_7(String arg) {
        columns[7] = arg;
    }

    public void setmDataProp_8(String arg) {
        columns[8] = arg;
    }

    public void setmDataProp_9(String arg) {
        columns[9] = arg;
    }

    private String getProperty(int column) {
        return columns[column];
    }

    public String getSortProperty() {
        return getProperty(getiSortCol_0());
    }

    public Map<String, String> getSortMap() {
        Map<String, String> map = new LinkedHashMap<String, String>();
        map.put(getSortProperty(), getSortDir());
        return map;
    }

    public String getSortComboForMorphia() {
        String sortColumn = getSortProperty();
        String sortDir = getSortDir();
        if (StringUtils.isEmpty(sortColumn)) {
            for (String column : columns) {
                if (StringUtils.isNotEmpty(column)) {
                    sortColumn = column;
                    break;
                }
            }
        }

        if (StringUtils.isNotEmpty(sortColumn)) {
            if ("asc".equals(sortDir) || StringUtils.isEmpty(sortDir)) {
                return sortColumn;
            } else {
                return "-" + sortColumn;
            }
        }
        return null;
    }
}
