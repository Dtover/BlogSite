package com.dtover.blog.util;

public class Getletter {
    public static char[] transfer(int[] nums, int offset) {
        char[] chars = new char[nums.length];
        for (int i = 0; i < nums.length; i++) {
            chars[i] = (char) (nums[i] + 96 + offset);
        }
        return chars;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4,16,15,8,19,2,21,22,13,2,21,10,16,15};
        char[] chars = transfer(nums, -1);
        for (int i = 0; i < chars.length; i++) {
            System.out.print(chars[i]);
        }
    }
}