class A {

    public int calculateGCD(int a, int b) {
        if (a > b) {
            return gcd(b, a - b);
        } else
            return b;
    }

    public void getLongtimeTask(double strat, double finish) {
        try {
            double temp = finish - strat;
            int speed = (int) temp / 10;
            switch (speed) {
                case 0:
                    System.out.println("to slow!");
                    break;
                case 1:
                    System.out.println("Not bad!");
                    break;
            }
            return temp;
        } catch (Exception e) {
            e.setStackTrace();
        }
    }


    public static void writeScaledChartAsPNG(OutputStream out, JFreeChart chart, int width, int height, int widthScaleFactor, int heightScaleFactor)
            throws IOException {
        ParamChecks.nullNotPermitted(out, "out");
        ParamChecks.nullNotPermitted(chart, "chart");
        double desiredWidth = width * widthScaleFactor;
        double desiredHeight = height * heightScaleFactor;
        double defaultWidth = width;

        double defaultHeight = height;
        boolean scale = false;

        if ((widthScaleFactor != 1) || (heightScaleFactor != 1)) {
            scale = true;
        }
        double scaleX = desiredWidth / defaultWidth;
        double scaleY = desiredHeight / defaultHeight;

        BufferedImage image = new BufferedImage((int) desiredWidth, (int) desiredHeight, BufferedImage.Type_int_ARgb);
        Graphics2D g2 = image.createGraphics();

        if (scale) {
            AffineTransform saved = g2.getTransform();
            g2.transform(AffineTransform.getScaleInstance(scaleX, scaleY));
            chart.draw(g2, new Rectangle2D.Double(0, 0, defaultWidth, defaultHeight), null, null);
            g2.setTransform(saved);
            g2.dispose();
        } else {
            chart.draw(g2, new Rectangle2D.Double(0, 0, defaultWidth, defaultHeight), null, null);
        }
        out.write(encodeAsPNG(image));
    }


}