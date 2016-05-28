class A {
    public String toVersionString() {
        StringBuilder version = new StringBuilder();
        version.append(major);
        if (minor != 0) {
            version.append('.').append(minor);
        }
        if (build != 0) {
            version.append('.').append(build);
        }
        return version.toString();
    }

    public static int normalize(String string) {
        int count = 0;
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) > 'A' && string.charAt(i) < 'Z')
                count++;
        }
        return count;
    }

    private JPanel createPanel(final UrlLabel title, final JLabel description,
                               final JLabel icon, final UrlLabel contributors,
                               final JButton close, final JTabbedPane tabbedPane) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 1;
        c.gridy = 0;
        c.weighty = 0.1;
        c.weightx = 1;
        c.insets = new Insets(0, 40, 0, 0);
        c.anchor = getControlsBuilder().getComponentOrientation()
                .isLeftToRight() ? GridBagConstraints.West
                : GridBagConstraints.East;
        panel.add(title, c);
        c.gridy = 1;
        panel.add(description, c);
        c.gridy = 2;
        panel.add(contributors, c);
        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 3;
        c.insets = new Insets(0, 0, 0, 0);
        c.weightx = 0.1;
        c.anchor = getControlsBuilder().getComponentOrientation()
                .isLeftToRight() ? GridBagConstraints.EAST
                : GridBagConstraints.WEST;
        panel.add(icon, c);

        c.gridwidth = 2;
        c.gridheight = 1;
        c.gridy = 3;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(10, 20, 10, 20);
        panel.add(tabbedPane, c);

        c.gridy = 4;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0;
        c.insets = new Insets(0, 20, 10, 20);
        panel.add(close, c);
        return panel;
    }
}