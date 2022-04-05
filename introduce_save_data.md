### =>Share Data
```Java
SharedPreferences sharedPreferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", "Lê Tuấn");
        editor.commit();
```
* Data saved in file xml and path = Device Explorer/data/data/[your-package]/shred_prefs

### => Write and Read data to file
```Java
     private void writeToFile(String data, Context context) {
        try{
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("data.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(Context context) {
        String res = "";
        try {
            InputStream inputStream = context.openFileInput("data.txt");

            if(inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String str = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((str = bufferedReader.readLine()) != null) {
                    stringBuilder.append("\n").append(str);
                }
                inputStream.close();
                res = stringBuilder.toString();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
```

* Data saved in file txt and path = Device Explorer/data/data/[your-package]/files