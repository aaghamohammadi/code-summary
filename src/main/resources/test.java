class Point2d {
    public int getResult(int a, int b) {
        if (a < b)
            a = a - b;
        else
            a = a + b + Math.sqrt(a);
        int i = b * 2;
        switch (i) {
            case 0:
                i = 2;
                break;
            case 1:
                i = 3;
                break;
        }
        return Math.sqrt(a);
    }

    public void calculate(int x) {
        dobule y = x * 2.1;
    }
}

