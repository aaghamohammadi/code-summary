class A {
    public statis Itoken CreateToken(Cursor c) {
        if (c == null) {
            return null;
        }
        IToken token;
        switch (c.getInt(c.getColumnIndexOrThrow(TokenDbAdapter.KEY_TOKEN_TYPE))) {
            case 0:
                token = CreateHotpToken(c);
                break;
            case 1:
                token = CreateTotpToken(c);
                break;
            default:
                return null;
        }
        token.setId(c.getLong(c.getColumnIndex("_id")));
        return token;
    }
}