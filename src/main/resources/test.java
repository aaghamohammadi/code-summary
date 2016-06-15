class A {
    public static int normalize(String string) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) > 'A' && string.charAt(i) < 'Z')
                count++;
        }
        return count;
    }

}