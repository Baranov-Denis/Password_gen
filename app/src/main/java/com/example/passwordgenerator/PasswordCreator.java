package com.example.passwordgenerator;

public class PasswordCreator {

    public static boolean oldGeneration = true;



    public void setOldGeneration(boolean oldGeneration) {
        this.oldGeneration = oldGeneration;
    }

    /**
     * Длинна пароля по умолчанию через геттер передаётся на главный экран
     * При вызове метода createPassword(String resourceName, String key, int passwordLength, boolean strongPassword)
     * устанавливается переданная длинна
     */
    public static int passwordLength = 25;

    /**
     * Переменная которая определяет какой массив использовать
     * если определена true  то  ->  fullCharArray
     * если определена false то  ->  cutCharArray
     */
    public static boolean strongPassword = true;

    /**
     * Массив из всех символов a-z A-Z и символы
     * Каждое число обозначает числовое представление символа
     * <p>
     * char x = (char) 33;
     * Равносильно следующей записи:
     * char x = (char) fullCharArray[0];
     */
    private final static int[] fullCharArray = {33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
            51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77,
            78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 91, 92, 93, 94, 95, 96, 97, 98, 99, 100, 101, 102, 103,
            104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 123, 124, 125};

    /**
     * Сокращённый массив с меньшим количеством кол-вом символов если пароль не проходит
     */
    private final static int[] cutCharArray = {33, 40, 41, 42, 43, 44, 45, 46, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57,
            60, 61, 62, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88,
            89, 90, 95, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116,
            117, 118, 119, 120, 121, 122};

    /**
     * Массив для того, чтобы один раз в начале выполнения программы определить какой из массивов будет использован полный или урезанный
     */
    private int[] currentArray;

    /**
     * Не менять VAR_1 и VAR_2 - оптимально равномерное распределение
     * Переменные для того, чтобы использовать один метод для преобразования пользовательских слов
     * для разных слов задаём разные числа и тогда даже при двух одинаковых словах получаем разные пароли
     */
    private final static int VAR_1 = 37;
    private final static int VAR_2 = 69;


    public String createPassword(String resourceName, String key) {
        return startCreatingString(resourceName, key);
    }

    private String startCreatingString(String resourceName, String key) {
        if (strongPassword) currentArray = fullCharArray;
        else currentArray = cutCharArray;
        int[] intArrayFromResource = correctString(resourceName.trim(), VAR_1);
        int[] intArrayFromKey = correctString(key.trim(), VAR_2);
        int[] intArrayForPassword = process(intArrayFromResource, intArrayFromKey);
        return createStringFromIntegerArray(intArrayForPassword);
    }


    /**
     * @param stringForCorrection
     * @param variation
     * @return
     */
    private int[] correctString(String stringForCorrection, int variation) {
        int[] hashArray = new int[passwordLength];
        stringForCorrection = stringForCorrection.trim();
        StringBuilder tempBuilder = new StringBuilder(stringForCorrection);

        while (stringForCorrection.length() < passwordLength) {
            for (int i = 0; i < stringForCorrection.length(); i++) {
                if (oldGeneration) {
                    tempBuilder.append((char) (stringForCorrection.charAt(i) + i + 1));
                } else {
                    tempBuilder.append((char) (stringForCorrection.charAt(i) * createVariableNumber(tempBuilder.toString()) - stringForCorrection.charAt(stringForCorrection.length() - (i + 1))));
                }
            }
            stringForCorrection = tempBuilder.toString();

        }

        int charHash = 0;

        for (int i = 0; i < stringForCorrection.length(); i++) {
            charHash += (stringForCorrection.charAt(i) * (passwordLength + variation));
        }

        for (int b = 0; b < passwordLength; b++) {
            int tempChar;
            if (oldGeneration) {
                tempChar = (charHash - (stringForCorrection.charAt(b) * variation));
            } else {
                tempChar = (charHash - (createVariableNumber(stringForCorrection) * variation));
            }
            hashArray[b] = tempChar;
            charHash = tempChar;
        }

        return hashArray;
    }

    /**
     * New method
     * @param a
     * @return
     */
    public int createVariableNumber(String a) {
        int res = 1;

        for (int i = 0; i < a.length(); i++) {

            if (i < (a.length() - 1)) {

                if (i % 2 == 0) {
                    res = (a.charAt(i) * a.charAt(i + 1)) - res;
                } else {
                    res += (a.charAt(i) * a.charAt(i + 1)) + res;
                }

            } else {

                res += res * a.charAt(a.length() / 2);

            }
        }

        //System.out.println(res);
        return res;
    }


    /**
     * @return Метод возвращает массив чисел, которые
     * будут использованы в качестве индексов для извлечения номеров символов из
     * полного или урезанного массива.
     */
    private int[] process(int[] one, int[] two) {
        int[] resultArr = new int[passwordLength];
        for (int i = 0; i < passwordLength; i++) {
            int a = one[i];
            int b = two[i];
            int result = subtractingChars(a, b);
            resultArr[i] = result;
        }
        return resultArr;
    }


    /**
     * Вычитание символов.
     * Метод подсчитывает так, чтобы попасть в диапазон массива
     * и возвращает число, которое в дальнейшем будет использовано
     * в качестве индекса для извлечения номера символа из
     * полного или урезанного массива.
     **/
    private int subtractingChars(int a, int b) {
        int result = a - b;
        while (result > currentArray.length) {
            result = result - b;
        }
        while (result < 0) {
            result = currentArray.length - (-result);
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
