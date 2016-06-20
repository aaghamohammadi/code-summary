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
    protected org.openide.nodes.Sheet createSheet() {

        Sheet sheet = super.createSheet();

        Sheet.Set propertySet = Sheet.createPropertiesSet();
        propertySet.setName("wmsmap");
        StandardWmsMapComponent component = (StandardWmsMapComponent) ((JRDesignComponentElement) getElement()).getComponent();
        propertySet.setDisplayName(I18n.getString("wmsmap"));

        JRDesignDataset dataset = ModelUtils.getElementDataset(getElement(), getJasperDesign());

        propertySet.put(new EvaluationTimeProperty(component, dataset));
        propertySet.put(new EvaluationGroupProperty(component, dataset));
        propertySet.put(new WmsServiceUrlProperty(component));
        propertySet.put(new WmsVersionListProperty(component));
        propertySet.put(new WmsSrsCrsProperty(component));
        propertySet.put(new WmsMapBBoxProperty(component, dataset));
        propertySet.put(new WmsMapLayersProperty(component, dataset));
        propertySet.put(new WmsMapStylesProperty(component, dataset));
        propertySet.put(new WmsMapImageTypeListProperty(component));
        propertySet.put(new WmsTransparentProperty(component));
        propertySet.put(new WmsUrlParametersProperty(component, dataset));

        sheet.put(propertySet);

        return sheet;
    }
    private static String encodeParameter(String key, Object value) {
        try {
            String encName = encode(key, UTF_8);
            String encValue = encode(value.toString(), UTF_8);
            String encodedParameter = String.format("%s=%s", encName, encValue);
            return encodedParameter;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding UTF-8 not supported on runtime");
        }
    }

    public ImageIcon getIcon(int width) {

        if (cachedImage != null && cachedImage.getIconWidth() == width) {
            return cachedImage;
        }


        cachedImage = getFasterScaledInstance(getIcon(), width, -1);
        return cachedImage;
    }

    public static ImageIcon getFasterScaledInstance(ImageIcon img, int targetWidth, int targetHeight) {
        if (img == null) {
            return null;
        }

        if (targetWidth == -1 && targetHeight > 0) {
            targetWidth = (int) (img.getIconWidth() * targetHeight * 1.0 / img.getIconHeight());
        } else if (targetHeight == -1 && targetWidth > 0) {
            targetHeight = (int) (img.getIconHeight() * targetWidth * 1.0 / img.getIconWidth());
        } else if (targetWidth <= 0 || targetHeight <= 0) {
            return img;
        }

        BufferedImage buf = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        buf.createGraphics().drawImage(img.getImage(), 0, 0, null);

        Image newImg = getFasterScaledInstance(buf, targetWidth, targetHeight, RenderingHints.VALUE_INTERPOLATION_BILINEAR, true);


        return new ImageIcon(newImg);
    }


}