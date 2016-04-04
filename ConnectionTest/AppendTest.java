class AppendTest  {
	public static String  getAppendedString(String message) {
        for (int i = WIDTH - 2; i < message.length(); i += WIDTH - 2) {
            message = message.substring(0, i) + "\n" + message.substring(i, message.length());
        }
        outputArea.append(message + " " + "\n");
        String currentText = outputArea.getText();
        int numlines = currentText.length() - currentText.replace("\n", "").length();
        while (numlines > outputArea.getHeight() - 1) {
            outputArea.setText(currentText.substring(currentText.indexOf("\n") + 1, currentText.length()));
            currentText = outputArea.getText();
            numlines = currentText.length() - currentText.replace("\n", "").length();
        }
	} 
}