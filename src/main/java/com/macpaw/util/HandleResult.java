package com.macpaw.util;


public class HandleResult {

    /**
     *
     * @param className Name of css class to parse it
     * @return current score and coordinates of cell
     */
    public static int[] parseClassName(String className) {
        String[] name = className.split(" ");
        int score = Integer.parseInt(name[1].substring(5));
        int col = Integer.parseInt(name[2].split("-")[2]);
        int row = Integer.parseInt(name[2].split("-")[3]);

        return new int[]{score, col, row};
    }

}
