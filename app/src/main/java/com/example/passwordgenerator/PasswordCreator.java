package com.example.passwordgenerator;

public class PasswordCreator {
    //длина пароля
    private int passwordLength = 25;
    //Если MAX_CHAR = 125, тогда не получаем char с номером 125
    private final static int MAX_CHAR = 126;
    private final static int MIN_CHAR = 33;
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

    private int[] process(int[] one, int[] two) {
        int[] resultArr = new int[passwordLength];
        for (int i = 0; i < passwordLength; i++) {
            int a = one[i];
            int b = two[i];
            int result = subtractingChars(a,b);


            if (!strongPassword){

                if (result == 39 ) result =  subtractingChars(a + passwordLength / (i+1) , b - passwordLength/ (i+1));

            }

            resultArr[i] = result;
        }
        return resultArr;
    }

    private int subtractingChars(int a , int b){
        int result = a - b;
        while(result > MAX_CHAR){
            result = result - b;
        }

        while(result < MIN_CHAR){
            result = MAX_CHAR - (MIN_CHAR - result) ;
        }
        return result;
    }

    private String createStringFromIntegerArray(int[] intArray) {
        StringBuilder stringFromArray = new StringBuilder();
        for (int integer : intArray) stringFromArray.append((char) integer);
        return stringFromArray.toString();
    }

/*
    //show result
    private  void showPass(Integer[] arr ){
        // System.out.println( );
        // System.out.println( );
        System.out.println("Password :");
        //   System.out.println( );
        System.out.print("                  ");
        for (Integer ch : arr) System.out.print( (char) (int) ch);
        //  System.out.println( );
    }

    private int passwordLength = 8;

    public int getPasswordLength() {
        return passwordLength;
    }

    public String createPassword(String resourceName, String key, int passwordLength) {
        this.passwordLength = passwordLength;
        return generatePassword(correctString(resourceName.trim(), 2),correctString(key.trim(),1));
    }


    private String correctString(String stringForCorrection, int variation) {
        StringBuilder correctedResourceName = new StringBuilder();
        while(stringForCorrection.length() < passwordLength){
            stringForCorrection += stringForCorrection;
        }
        int charHash = passwordLength;
        for(int i = 0 ; i < stringForCorrection.length() ; i++){
            charHash += stringForCorrection.charAt(i) * variation;
        }
        for (int b = 0 ; b < passwordLength ; b++){
            char temp = (char) (charHash - stringForCorrection.charAt(b));
            charHash = temp;
            correctedResourceName.append(temp);
        }
        return correctedResourceName.toString();
    }


    private char charPlus(char firstChar , char secondChar){
        int result = (int) firstChar + (int) secondChar;
        while (result > 125){
            result = 33 + (result - 125);
        }

        return (char) result;
    }

    private char charMinus(char firstChar , char secondChar){
        int result = (int) firstChar - (int) secondChar;
       while (result < 33){
           result = 125 - Math.abs(result);
       }
       while (result > 125){
           result = result - (int) secondChar;
       }
        while (result < 33){
            result = 125 - Math.abs(result);
        }

        return (char) result;
    }

    private String generatePassword(String resourceName , String key){
        StringBuilder password = new StringBuilder();
        for (int i = 0 ; i < passwordLength ; i++){
            char firstChar = resourceName.charAt(i);
            char secondChar = key.charAt(i);
            if(i%2 == 0){
                password.append(charMinus(firstChar , secondChar));
                if(charMinus(firstChar , secondChar) > 125 || charMinus(firstChar , secondChar) < 33){
                    return "Error minus";
                }
            }else {
                password.append(charPlus(firstChar , secondChar));
                if(charPlus(firstChar , secondChar) > 125 || charPlus(firstChar , secondChar) < 33){
                    return "Error plus";
                }
            }
        }
        return password.toString();
    }*/
}
