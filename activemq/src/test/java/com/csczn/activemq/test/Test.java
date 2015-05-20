package com.csczn.activemq.test;

/**
 * 04 26 2015 Steven
 */
public class Test {
    /**
     * 半角转全角
     *
     * @param input String.
     * @return 全角字符串.
     */
    public static String ToSBC(String input) {
        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == ' ') {
                c[i] = '\u3000';
            } else if (c[i] < '\177') {
                c[i] = (char) (c[i] + 65248);

            }
        }
        return new String(c);
    }

    /**
     * 全角转半角
     *
     * @param input String.
     * @return 半角字符串
     */
    public static String ToDBC(String input) {

        char c[] = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == '\u3000') {
                c[i] = ' ';
            } else if (c[i] > '\uFF00' && c[i] < '\uFF5F') {
                c[i] = (char) (c[i] - 65248);

            }
        }

        return new String(c);
    }

    public static void main(String[] args) {

        String QJstr = "wch";

        String QJstr1 = "ｈｅｌｌｏ@！~`你好啊 ？、-，；：、、|】【{}=+&……%￥#@！&**（（））。／";


        String result = ToSBC(QJstr);

        String result1 = ToDBC(QJstr1);


        System.out.println(QJstr + "\n" + result);

        System.out.println(QJstr1 + "\n" + result1);

        String name = "AA2BB12bbdfd";
        System.out.println(name);
        name = name.replaceAll("\\d*", "");
        System.out.println(name);

    }
}
