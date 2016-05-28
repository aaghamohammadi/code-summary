class Point2d {
    public int getResult(int a, int b) {
        if (a < b)
            a = a - b;
        else
            a = a + b;
        return Math.sqrt(a);
    }
}

