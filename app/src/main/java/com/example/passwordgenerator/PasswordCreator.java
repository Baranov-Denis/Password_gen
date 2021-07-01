package com.example.passwordgenerator;

public class PasswordCreator {
    //длина пароля
    private int passwordLength = 25;
    //Если MAX_CHAR = 125, тогда не получаем char с номером 125
    private final static int MAX_CHAR = 126;
    private final static int MIN_CHAR = 33;

    private final static int[] fullCharArray = {33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,
    51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,
    84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,109,110,111,112,
    113,114,115,116,117,118,119,120,121,122,123,124,125};


    private final static int[] cutCharArray = {33,40,41,43,45,48,49,50,51,52,53,54,55,56,57,61,62,65,66,67,68,
    69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,95,97,98,99,100,101,102,103,104,105,106,
    107,108,109,110,111,112,113,114,115,116,117,118,119,120,121,122};

    private int[] currentArray;

    //Не менять VAR_1 и VAR_2 - оптимально равномерное распределение
    private final static int VAR_1 = 37;
    private final static int VAR_2 = 69;
    private boolean strongPassword;

    public int getPasswordLength() {
        return passwordLength;
    }

    public String createPassword(String resourceName, String key, int passwordLength, boolean strongPassword) {
        this.passwordLength = passwordLength;
        this.strongPassword = strongPassword;
        return startCreatingString(resourceName, key);
    }

    private String startCreatingString(String resourceName, String key) {
        if(strongPassword) currentArray = fullCharArray;
        else currentArray = cutCharArray;
        int[] intArrayFromResource = correctString(resourceName.trim(), VAR_1);
        int[] intArrayFromKey = correctString(key.trim(), VAR_2);
        int[] intArrayForPassword = process(intArrayFromResource, intArrayFromKey);
        return createStringFromIntegerArray(intArrayForPassword);
    }


    //Преобразуем входящую строку
    private int[] correctString(String stringForCorrection, int variation) {
        int[] hashArray = new int[passwordLength];
        stringForCorrection = stringForCorrection.trim();
        StringBuilder tempBuilder = new StringBuilder(stringForCorrection);

        while (stringForCorrection.length() < passwordLength) {
            for (int i = 0; i < stringForCorrection.length(); i++) {
                tempBuilder.append((char) (stringForCorrection.charAt(i) + i + 1));
            }
            stringForCorrection = tempBuilder.toString();

        }

        int charHash = 0;

        for (int i = 0; i < stringForCorrection.length(); i++) {
            charHash += (stringForCorrection.charAt(i) * (passwordLength + variation));
        }

        for (int b = 0; b < passwordLength; b++) {
            int tempChar = (charHash - (stringForCorrection.charAt(b) * variation));
            hashArray[b] = tempChar;
            charHash = tempChar;
        }

        return hashArray;
    }


    //Получаем разность массивов

    private  int[] process(int[] one, int[] two ){
        int[] resultArr = new int[passwordLength];
        for(int i = 0 ; i < passwordLength ; i++){
            int a = one[i];
            int b = two[i];
            int result ;
           // if(strongPassword) {
                result =   subtractingChars(a, b);
         //   }else {
           //     result =   subtractingChars(a, b, cutCharArray);
        //    }


            resultArr[i] = result;
        }
        return resultArr ;
    }

   /* private int subtractingChars(int a , int b){
        int result = a - b;
        while(result > MAX_CHAR){
            result = result - b;
        }

        while(result < MIN_CHAR){
            result = MAX_CHAR - (MIN_CHAR - result) ;
        }
        return result;
    }*/

    private int subtractingChars(int a , int b){
        int result = a - b;
        while(result > currentArray.length){
            result = result - b;
        }

        while(result < 0){
            result = currentArray.length - (0 - result) ;
        }

        return result;

    }

    /*
    private String createStringFromIntegerArray(int[] intArray) {
        StringBuilder stringFromArray = new StringBuilder();
        for (int integer : intArray) stringFromArray.append((char) integer);
        return stringFromArray.toString();
    }*/
    private String createStringFromIntegerArray(int[] intArray) {
        StringBuilder stringFromArray = new StringBuilder();

            for (int integer : intArray) stringFromArray.append((char) currentArray[integer]);

        return stringFromArray.toString();
    }


}
